package research.processbuilder;

import junit.framework.TestCase;

import java.io.IOException;

/**
 * @author Igor Usenko
 *         Date: 17.11.2009
 */
public class ProcessBuilderTest extends TestCase {

    public ProcessBuilderTest(final String _s) {
        super(_s);
    }

    public void testSmoke() throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("C:/Program Files/Windows NT/Accessories/wordpad.exe");
        Process process = builder.start();
        // FutureTask


//        FutureTask<String> future =
//          new FutureTask<String>(new Callable<String>() {
//            public String call() {
//              return searcher.search(target);
//          }});
//        executor.execute(future);

        //process.waitFor();
    }
}
