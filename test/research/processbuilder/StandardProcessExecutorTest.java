package research.processbuilder;

import junit.framework.TestCase;
import app.workingarea.process.ExternalProcessExecutor;
import app.workingarea.ProcessWrapper;
import work.testutil.CompTestsUtils;

/**
 * @author Igor Usenko
 *         Date: 22.11.2009
 */
public class StandardProcessExecutorTest extends TestCase {

    public StandardProcessExecutorTest(String s) {
        super(s);
    }

    public void testSmoke() {
        String cmd = CompTestsUtils.getCompTestsRoot() + "StandardProcessExecutorTest/test.rtf";
        ExternalProcessExecutor executor = new ExternalProcessExecutor();

        executor.call("C:/Program Files/Windows NT/Accessories/wordpad.exe", cmd, ProcessWrapper.WAIT_FOREVER);
        executor.call("C:/Program Files/Windows NT/Accessories/wordpad.exe", cmd, 5000);
        executor.call("C:/Program Files/Windows NT/Accessories/wordpad.exe", cmd, 5000);
    }
}
