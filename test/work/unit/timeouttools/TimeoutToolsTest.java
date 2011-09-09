package work.unit.timeouttools;

import junit.framework.TestCase;
import util.TimeoutTools;

/**
 * @author Igor Usenko
 *         Date: 13.07.2010
 */
public class TimeoutToolsTest extends TestCase {

    public TimeoutToolsTest(final String _s) {
        super(_s);
    }

    public void testSmoke() {
        assertEquals(10, TimeoutTools.getTimeout(10, 100, 0, 9));
        assertEquals(20, TimeoutTools.getTimeout(10, 100, 1, 9));
        assertEquals(30, TimeoutTools.getTimeout(10, 100, 2, 9));
        assertEquals(40, TimeoutTools.getTimeout(10, 100, 3, 9));
        assertEquals(50, TimeoutTools.getTimeout(10, 100, 4, 9));
        assertEquals(60, TimeoutTools.getTimeout(10, 100, 5, 9));
        assertEquals(70, TimeoutTools.getTimeout(10, 100, 6, 9));
        assertEquals(80, TimeoutTools.getTimeout(10, 100, 7, 9));
        assertEquals(90, TimeoutTools.getTimeout(10, 100, 8, 9));
        assertEquals(100, TimeoutTools.getTimeout(10, 100, 9, 9));    
    }
}
