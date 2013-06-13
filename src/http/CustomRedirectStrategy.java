package http;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.HttpRequest;
import org.apache.http.client.CircularRedirectException;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.RedirectLocations;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author : Igor Usenko ( igors48@gmail.com )
 * Date : 31.05.13
 */
public class CustomRedirectStrategy implements RedirectStrategy {

    public static final String RESPONSE_BODY = "response.body";
    public static final String RESPONSE_CHARSET = "response.charset";

    private static final Pattern META_PATTERN = Pattern.compile("<meta.*http-equiv=\"refresh\".*url=(.*)\"");

    private static final String LOCATION_PARSED = "location.parsed";

    private final Log log = LogFactory.getLog(getClass());

    public static final String REDIRECT_LOCATIONS = "http.protocol.redirect-locations";

    @Override
    public boolean isRedirected(HttpRequest request, HttpResponse response, HttpContext context) throws ProtocolException {
        if (response == null) {
            throw new IllegalArgumentException("HTTP response may not be null");
        }

        int statusCode = response.getStatusLine().getStatusCode();
        String method = request.getRequestLine().getMethod();
        Header locationHeader = response.getFirstHeader("location");

        switch (statusCode) {
            case HttpStatus.SC_MOVED_TEMPORARILY:
                return (method.equalsIgnoreCase(HttpGet.METHOD_NAME)
                        || method.equalsIgnoreCase(HttpHead.METHOD_NAME)) && locationHeader != null;
            case HttpStatus.SC_MOVED_PERMANENTLY:
            case HttpStatus.SC_TEMPORARY_REDIRECT:
                return method.equalsIgnoreCase(HttpGet.METHOD_NAME)
                        || method.equalsIgnoreCase(HttpHead.METHOD_NAME);
            case HttpStatus.SC_SEE_OTHER:
                return true;
            case HttpStatus.SC_OK:
                try {
                    byte[] responseBody = readResponseBody(response);
                    String responseCharset = getResponseCharset(response);

                    context.setAttribute(RESPONSE_BODY, responseBody);
                    context.setAttribute(RESPONSE_CHARSET, responseCharset);

                    String location = getLocationFromResponseBody(new String(responseBody, responseCharset));

                    final boolean isRedirected = !location.isEmpty();

                    if (isRedirected) {
                        context.setAttribute(LOCATION_PARSED, location);
                    }

                    return isRedirected;
                } catch (UnsupportedEncodingException e) {
                    throw new ProtocolException("Cant read response body", e);
                }
            default:
                return false;
        } //end of switch
    }

    public URI getLocationURI(
            final HttpRequest request,
            final HttpResponse response,
            final HttpContext context) throws ProtocolException {
        if (response == null) {
            throw new IllegalArgumentException("HTTP response may not be null");
        }

        String locationFromContext = (String) context.getAttribute(LOCATION_PARSED);

        String location = locationFromContext == null ? getLocation(response) : locationFromContext;

        if (location.isEmpty()) {
            throw new ProtocolException(
                    "Received redirect response " + response.getStatusLine()
                            + " but no location header neither location in response body");
        }

        if (this.log.isDebugEnabled()) {
            this.log.debug("Redirect requested to location '" + location + "'");
        }

        URI uri = createLocationURI(location);

        HttpParams params = response.getParams();
        // rfc2616 demands the location value be a complete URI
        // Location       = "Location" ":" absoluteURI
        if (!uri.isAbsolute()) {
            if (params.isParameterTrue(ClientPNames.REJECT_RELATIVE_REDIRECT)) {
                throw new ProtocolException("Relative redirect location '"
                        + uri + "' not allowed");
            }
            // Adjust location URI
            HttpHost target = (HttpHost) context.getAttribute(
                    ExecutionContext.HTTP_TARGET_HOST);
            if (target == null) {
                throw new IllegalStateException("Target host not available " +
                        "in the HTTP context");
            }
            try {
                URI requestURI = new URI(request.getRequestLine().getUri());
                URI absoluteRequestURI = URIUtils.rewriteURI(requestURI, target, true);
                uri = URIUtils.resolve(absoluteRequestURI, uri);
            } catch (URISyntaxException ex) {
                throw new ProtocolException(ex.getMessage(), ex);
            }
        }

        if (params.isParameterFalse(ClientPNames.ALLOW_CIRCULAR_REDIRECTS)) {

            RedirectLocations redirectLocations = (RedirectLocations) context.getAttribute(
                    REDIRECT_LOCATIONS);

            if (redirectLocations == null) {
                redirectLocations = new RedirectLocations();
                context.setAttribute(REDIRECT_LOCATIONS, redirectLocations);
            }

            URI redirectURI;
            if (uri.getFragment() != null) {
                try {
                    HttpHost target = new HttpHost(
                            uri.getHost(),
                            uri.getPort(),
                            uri.getScheme());
                    redirectURI = URIUtils.rewriteURI(uri, target, true);
                } catch (URISyntaxException ex) {
                    throw new ProtocolException(ex.getMessage(), ex);
                }
            } else {
                redirectURI = uri;
            }

            if (redirectLocations.contains(redirectURI)) {
                throw new CircularRedirectException("Circular redirect to '" +
                        redirectURI + "'");
            } else {
                redirectLocations.add(redirectURI);
            }
        }

        return uri;
    }

    private String getLocation(HttpResponse response) throws ProtocolException {

        try {
            Header locationHeader = response.getFirstHeader("location");

            if (locationHeader != null) {
                return locationHeader.getValue();
            }

            byte[] responseBody = readResponseBody(response);
            String responseCharset = getResponseCharset(response);

            return getLocationFromResponseBody(new String(responseBody, responseCharset));
        } catch (UnsupportedEncodingException e) {
            throw new ProtocolException("Cant read response body", e);
        }
    }

    private String getResponseCharset(HttpResponse response) {
        String responseCharset = EntityUtils.getContentCharSet(response.getEntity());

        return responseCharset == null ? Charset.defaultCharset().name() : responseCharset;
    }

    private byte[] readResponseBody(HttpResponse response) throws ProtocolException {

        try {
            return EntityUtils.toByteArray(response.getEntity());
        } catch (IOException e) {
            throw new ProtocolException("Error reading response body");
        }
    }

    private String getLocationFromResponseBody(String responseBody) throws ProtocolException {
        Matcher matcher = META_PATTERN.matcher(responseBody);

        return matcher.find() ? matcher.group(1) : "";
    }

    /**
     * @since 4.1
     */
    protected URI createLocationURI(final String location) throws ProtocolException {
        try {
            return new URI(location);
        } catch (URISyntaxException ex) {
            throw new ProtocolException("Invalid redirect URI: " + location, ex);
        }
    }

    public HttpUriRequest getRedirect(
            final HttpRequest request,
            final HttpResponse response,
            final HttpContext context) throws ProtocolException {
        URI uri = getLocationURI(request, response, context);
        String method = request.getRequestLine().getMethod();
        if (method.equalsIgnoreCase(HttpHead.METHOD_NAME)) {
            return new HttpHead(uri);
        } else {
            return new HttpGet(uri);
        }
    }

}
