package constructor.dom.locator;

import app.templater.Template;
import constructor.dom.Locator;
import constructor.dom.ObjectType;
import constructor.dom.StandardTypeNames;
import constructor.objects.simpler.configuration.SimplerConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import util.Assert;
import util.IOTools;
import util.PathTools;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static util.CollectionUtils.newArrayList;
import static util.CollectionUtils.newHashMap;

/**
 * Локатор объектов расположенных в произвольных подкаталогах рута
 *
 * @author Igor Usenko
 *         Date: 18.07.2009
 */
public class JavaStyleLocator implements Locator {

    private static final String ERROR_READING_OBJECT_FROM_FILE_TOKEN = "Error reading object from file [ ";
    private static final String CLOSING_BRACE = " ].";

    private final String root;

    private final XmlFileNameFilter xmlFileNameFilter;

    private Map<ObjectType, List<String>> objects;
    private String rootIndex;

    private final Log log;

    public JavaStyleLocator(final String _root) {
        Assert.isValidString(_root, "Locator directory name is not valid.");

        this.root = getAbsoluteRoot(_root);
        Assert.isTrue(new File(this.root).exists(), "Locator directory [ " + this.root + " ] not exists.");

        this.xmlFileNameFilter = new XmlFileNameFilter();

        this.log = LogFactory.getLog(getClass());

        scanObjects();
    }

    public String getSourceFile(final String _id, final ObjectType _type) throws LocatorException {
        Assert.isValidString(_id, "Object id is not valid.");
        Assert.notNull(_type, "Object type is null.");

        return getSourceFileName(_id, _type);
    }

    public InputStream locate(final String _id, final ObjectType _type) throws LocatorException {
        Assert.isValidString(_id, "Object id is not valid.");
        Assert.notNull(_type, "Object type is null.");

        try {
            String source = getSourceFileName(_id, _type);

            if (!source.isEmpty()) {
                return new FileInputStream(source);
            } else {
                throw new LocatorException("Can not find file for object id [ " + _id + " ] of type [ " + _type + CLOSING_BRACE);
            }
        } catch (FileNotFoundException e) {
            throw new LocatorException("Can not find file for object id [ " + _id + " ] of type [ " + _type + CLOSING_BRACE, e);
        }
    }

    public List<String> locateAll(final ObjectType _type, final Mask _mask) throws LocatorException {
        Assert.notNull(_type, "Object type is null");
        Assert.notNull(_mask, "Mask is null");

        List<String> result = newArrayList();

        List<String> ids = this.objects.get(_type);

        if (ids != null) {

            for (String current : ids) {
                String id = indexToId(current);

                if (id == null) {
                    this.log.error("Can not get object id from index [ " + id + " ].");
                } else {
                    result.add(id);
                }
            }
        }

        return JavaStyleLocatorUtils.select(result, _mask);
    }

    public void storeTemplates(final String _name, final List<Template> _templates) throws LocatorException {
        Assert.isValidString(_name, "Name is not valid");
        Assert.notNull(_templates, "Templates is null");

        storeTemplates(_templates, getDestinationDirectory(_name));
    }

    public void removeSimplerConfiguration(final SimplerConfiguration _configuration) throws LocatorException {
        Assert.notNull(_configuration, "Simpler configuration is null");

        File pathToSimpler = new File(PathTools.normalize(this.root), JavaStyleLocatorUtils.getPathToSimpler(_configuration.getId()));

        if (pathToSimpler.exists()) {
            File victim = new File(pathToSimpler, JavaStyleLocatorUtils.getSimplerFileName(_configuration.getId()));

            if (!victim.delete()) {
                throw new LocatorException(MessageFormat.format("Error deleting simpler with id [ {0} ]", _configuration.getId()));
            }
        } else {
            throw new LocatorException(MessageFormat.format("Can not find directory [ {0} ] for simpler with id [ {1} ]", pathToSimpler.getAbsolutePath(), _configuration.getId()));
        }
    }

    public void storeSimplerConfiguration(final SimplerConfiguration _configuration) throws LocatorException {
        Assert.notNull(_configuration, "Simpler configuration is null");

        File pathToSimpler = new File(PathTools.normalize(this.root), JavaStyleLocatorUtils.getPathToSimpler(_configuration.getId()));

        if (!pathToSimpler.exists()) {

            if (!pathToSimpler.mkdirs()) {
                throw new LocatorException(MessageFormat.format("Error creating directory [ {0} ] for store simpler with id [ {1} ]", pathToSimpler.getAbsolutePath(), _configuration.getId()));
            }
        }

        JavaStyleLocatorUtils.storeSimplerConfiguration(new File(pathToSimpler, JavaStyleLocatorUtils.getSimplerFileName(_configuration.getId())), _configuration);
        scanObjects();
    }

    private void scanObjects() {
        this.rootIndex = JavaStyleLocatorUtils.pathToIndex(this.root);

        this.objects = newHashMap();

        this.objects.put(ObjectType.CHANNEL, new ArrayList<String>());
        this.objects.put(ObjectType.DATEPARSER, new ArrayList<String>());
        this.objects.put(ObjectType.INTERPRETER, new ArrayList<String>());
        this.objects.put(ObjectType.OUTPUT, new ArrayList<String>());
        this.objects.put(ObjectType.PROCESSOR, new ArrayList<String>());
        this.objects.put(ObjectType.SOURCE, new ArrayList<String>());
        this.objects.put(ObjectType.STORAGE, new ArrayList<String>());
        this.objects.put(ObjectType.SIMPLER, new ArrayList<String>());

        scanDirectory(new File(this.root));
    }

    private void storeTemplates(final List<Template> _templates, final File dest) throws LocatorException {

        for (Template template : _templates) {
            JavaStyleLocatorUtils.storeTemplate(dest, template);
            this.log.info("Template [ " + template.getName() + " ] stored");
        }
    }

    private File getDestinationDirectory(String _name) throws LocatorException {
        File result = new File(PathTools.normalize(this.root + JavaStyleLocatorUtils.nameToPath(_name)));

        if (result.exists()) {
            throw new LocatorException("Can not create templates named [ " + _name + " ] because directory already exists");
        }

        if (!result.mkdirs()) {
            throw new LocatorException("Can not create directory for templates [ " + result.getAbsolutePath() + " ] ");
        }

        return result;
    }

    private String getSourceFileName(final String _id, final ObjectType _type) {
        String index = idToIndex(_id);
        List<String> branch = this.objects.get(_type);

        return exists(index, branch) ? JavaStyleLocatorUtils.indexToPath(index) : "";
    }

    private String getAbsoluteRoot(final String _root) {
        String result = _root;

        try {
            result = PathTools.normalize(new File(_root).getCanonicalPath());
        } catch (IOException e) {
            this.log.warn("Error when retrieving absolute path.", e);
        }

        return result;
    }

    private boolean exists(final String _name, final List<String> _branch) {
        boolean result = false;

        for (String current : _branch) {

            if (_name.equalsIgnoreCase(current)) {
                result = true;
                break;
            }
        }

        return result;
    }

    private String indexToId(final String _index) {
        return _index.startsWith(this.rootIndex) ?
                _index.substring(this.rootIndex.length(), _index.length() - JavaStyleLocatorUtils.XML_EXTENSION.length()).replaceAll("\\*", ".")
                : null;
    }

    private String idToIndex(final String _id) {

        return new StringBuilder().append(this.rootIndex)
                .append(JavaStyleLocatorUtils.pathToIndex(_id.replaceAll("\\.", JavaStyleLocatorUtils.INDEX_DIVIDER)))
                .append(JavaStyleLocatorUtils.XML_EXTENSION)
                .toString();
    }

    private void scanDirectory(final File _path) {
        File[] items = _path.listFiles();

        List<File> directories = new ArrayList<File>();
        List<File> files = new ArrayList<File>();

        for (File item : items) {

            if (item.isDirectory()) {
                directories.add(item);
            }

            // todo потом рассмотреть возможность xmlFileNameFilter -> static util 
            if (item.isFile() && this.xmlFileNameFilter.accept(item, item.getName())) {
                files.add(item);
            }
        }

        for (File directory : directories) {
            scanDirectory(directory);
        }

        for (File file : files) {
            processFile(file);
        }
    }

    private void processFile(final File _file) {
        InputStream stream = null;

        try {
            stream = new FileInputStream(_file);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document doc = factory.newDocumentBuilder().parse(stream);
            Element root = doc.getDocumentElement();
            String rootName = root.getNodeName();

            ObjectType type = StandardTypeNames.getType(rootName);

            if (type == null) {
                this.log.error("Unknown object type reading from file [ " + _file.getAbsolutePath() + CLOSING_BRACE);
            } else {
                appendObjectToIndex(_file, type);
            }
        } catch (FileNotFoundException e) {
            this.log.error(ERROR_READING_OBJECT_FROM_FILE_TOKEN + _file.getAbsolutePath() + CLOSING_BRACE, e);
        } catch (IOException e) {
            this.log.error(ERROR_READING_OBJECT_FROM_FILE_TOKEN + _file.getAbsolutePath() + CLOSING_BRACE, e);
        } catch (SAXException e) {
            this.log.error(ERROR_READING_OBJECT_FROM_FILE_TOKEN + _file.getAbsolutePath() + CLOSING_BRACE, e);
        } catch (ParserConfigurationException e) {
            this.log.error(ERROR_READING_OBJECT_FROM_FILE_TOKEN + _file.getAbsolutePath() + CLOSING_BRACE, e);
        } finally {
            IOTools.close(stream);
        }
    }

    private void appendObjectToIndex(final File _file, final ObjectType type) {
        List<String> list = this.objects.get(type);

        if (list != null) {
            list.add(JavaStyleLocatorUtils.pathToIndex(_file.getAbsolutePath()));

            this.log.debug("File [ " + _file.getAbsolutePath() + " ] of type [ " + type + " ] added to index.");
        } else {
            this.log.debug("File [ " + _file.getAbsolutePath() + " ] of unsupported type [ " + type + " ] ignored.");
        }
    }

}
