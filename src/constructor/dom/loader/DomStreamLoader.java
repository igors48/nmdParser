package constructor.dom.loader;

import constructor.dom.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import util.Assert;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Обработчик DOM потока. Берет из потока корневой элемент.
 * По его имени находит обработчик и запускает его. А тот
 * уже по цепочке и до победы.
 *
 * @author Igor Usenko
 *         Date: 14.03.2009
 */
public class DomStreamLoader implements Loader {

    private final ComponentFactory componentFactory;

    public DomStreamLoader(final ComponentFactory _componentFactory) {
        Assert.notNull(_componentFactory, "Component factory is null.");
        this.componentFactory = _componentFactory;
    }

    public Blank load(final InputStream _stream, final ConstructorFactory _factory) throws LoaderException {
        Assert.notNull(_stream);

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document doc = factory.newDocumentBuilder().parse(_stream);
            Element root = doc.getDocumentElement();
            String rootName = root.getNodeName();
            Blank blank = this.componentFactory.getBlank(rootName);

            if (blank == null) {
                throw new LoaderException("Can`t create blank object for root id [ " + rootName + " ].");
            }

            ElementHandler handler = this.componentFactory.getHandler(rootName);

            if (handler != null) {
                handler.handle(doc.getDocumentElement(), blank, _factory);
            }

            return blank;
        } catch (SAXException e) {
            throw new LoaderException(e);
        } catch (IOException e) {
            throw new LoaderException(e);
        } catch (ParserConfigurationException e) {
            throw new LoaderException(e);
        } catch (ElementHandler.ElementHandlerException e) {
            throw new LoaderException(e);
        }

    }


}
