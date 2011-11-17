package work.unit.constructor.dateparser;

import constructor.dom.Constructor;
import constructor.dom.ConstructorFactory;
import constructor.dom.ObjectType;
import constructor.objects.dateparser.adapter.StandardDateParserAdapter;
import constructor.objects.dateparser.configuration.DateParserConfiguration;
import constructor.objects.dateparser.core.DateParser;
import constructor.objects.dateparser.core.DateParserAdapter;
import constructor.objects.dateparser.core.DateParserTools;
import junit.framework.TestCase;
import timeservice.StillTimeService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static work.testutil.ConstructorTestUtils.createConstructorFactory;

/**
 * @author Igor Usenko
 *         Date: 13.06.2009
 */
public class DateParserAdapterTest extends TestCase {

    public DateParserAdapterTest(final String _s) {
        super(_s);
    }

    // первоначальный тест 

    public void testSmoke() throws Constructor.ConstructorException {
        Map<String, String> streams = new HashMap<String, String>();
        streams.put("dateParser", "<dateParser><standard><pattern>MMMM dd, yyyy, h:mm a</pattern><months>January February March April May June July August September October November December</months></standard></dateParser>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        DateParserConfiguration configuration = (DateParserConfiguration) constructor.create("dateParser", ObjectType.DATEPARSER);

        DateParserAdapter adapter = new StandardDateParserAdapter(configuration);

        DateParser parser = adapter.getDateParser();

        assertNotNull(parser);

        Date date = parser.parse("May 27, 1974, 4:00 am", 0, new StillTimeService());

        assertEquals(27, DateParserTools.getDay(date));
        assertEquals(4, DateParserTools.getMonth(date));
        assertEquals(1974, DateParserTools.getYear(date));
        assertEquals(4, DateParserTools.getHour(date));
        assertEquals(0, DateParserTools.getMinute(date));
    }
}
