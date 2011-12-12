package constructor.objects.snippet.core;

import app.cli.blitz.BlitzRequestUtils;
import app.cli.blitz.request.BlitzRequest;
import app.cli.blitz.request.RequestSourceType;
import constructor.objects.output.configuration.Composition;
import constructor.objects.snippet.configuration.SnippetConfiguration;
import util.Assert;
import util.TextTools;
import util.sequense.PatternListSequencer;

import java.util.ArrayList;
import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * Конвертирует сниппет в блиц запрос
 *
 * @author Igor Usenko
 *         Date: 12.12.2009
 */
public class SnippetConverter {

    public static BlitzRequest convert(final SnippetConfiguration _configuration) throws SnippetConverterException {
        Assert.notNull(_configuration, "Snippet configuration is null");

        BlitzRequest result = new BlitzRequest(_configuration.getRequestSourceType(), createAdresses(_configuration));

        result.setCriterionType(_configuration.getCriterionType());

        if (!_configuration.getCriterionExpression().isEmpty()) {
            result.setCriterionExpression(_configuration.getCriterionExpression());
        }

        result.setStorage(_configuration.getStorage());
        result.setBranch(_configuration.getBranch());
        result.setComposition(_configuration.getComposition());

        if (_configuration.getComposition() == Composition.MANY_TO_ONE) {
            result.setOutName(_configuration.getOutName());
        }

        result.setForEachPath(_configuration.getPath());
        result.setForEachCommand(_configuration.getCommand());
        result.setForEachWait(TextTools.parseLong(_configuration.getWait()));

        result.setLinksAsFootnotes(_configuration.isLinksAsFootnotes());
        result.setRemoveExists(_configuration.isRemoveExists());
        result.setResolveImageLinks(_configuration.isResolveImageLinks());

        result.setGenres(_configuration.getGenres());

        result.setLang(_configuration.getLang());

        result.setForced(_configuration.isForced());

        result.setFromNewToOld(_configuration.getDocumentItemsSortMode());

        result.setCoverUrl(_configuration.getCoverUrl());

        return result;
    }

    private static List<String> createAdresses(final SnippetConfiguration _configuration) throws SnippetConverterException {
        List<String> result = newArrayList();

        checkUrls(_configuration);

        if (_configuration.getRequestSourceType() == RequestSourceType.RSS) {
            createForRss(_configuration, result);
        } else if (_configuration.getRequestSourceType() == RequestSourceType.URLS) {
            createForUrls(_configuration, result);
        } else {
            throw new SnippetConverterException("Request source type not set");
        }

        return result;
    }

    private static void createForUrls(final SnippetConfiguration _configuration, final List<String> _result) throws SnippetConverterException {

        if (_configuration.getBase().isEmpty()) {
            _result.addAll(getUrls(_configuration));
        } else {
            _result.addAll(BlitzRequestUtils.appendBase(_configuration.getBase(), getUrls(_configuration)));
        }
    }

    private static List<String> getUrls(final SnippetConfiguration _configuration) {
        return PatternListSequencer.getSequence(_configuration.getUrlGenerationPatterns());
    }

    private static void checkUrls(final SnippetConfiguration _configuration) throws SnippetConverterException {

        if (_configuration.getUrlGenerationPatterns().isEmpty()) {
            throw new SnippetConverterException("Urls list is empty");
        }
    }

    private static void createForRss(final SnippetConfiguration _configuration, final List<String> _result) throws SnippetConverterException {
        _result.addAll(PatternListSequencer.getSequence(_configuration.getUrlGenerationPatterns().subList(0, 1)));
    }

    public static class SnippetConverterException extends Exception {

        public SnippetConverterException(final String _s) {
            super(_s);
        }
    }
}
