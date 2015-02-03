package pl.exsio.frameset.routing.router;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import pl.exsio.frameset.BaseTest;
import pl.exsio.frameset.core.model.Module;
import pl.exsio.frameset.routing.ex.PathNotFoundException;

/**
 *
 * @author exsio
 */
public class RouterImplTest extends BaseTest {

    private final static int RESULT_PATH_NOT_FOUND = -1;

    private final static int RESULT_PATH_FOUND_BUT_NO_MODULE = 0;

    private final static int RESULT_MODULE_FOUND = 1;

    @Autowired
    protected RouterImpl router;

    public RouterImplTest() {
    }

    /**
     * Test of match method, of class RouterImpl.
     */
    @Test
    public void testMatch() {

        Map<String, Integer> pathsToTest = new HashMap<String, Integer>() {
            {
//                put("settings/users", RESULT_MODULE_FOUND);
//                put("/home", RESULT_MODULE_FOUND);
//                put("settings/groups", RESULT_MODULE_FOUND);
//                put("/settings/frames", RESULT_MODULE_FOUND);
//                put("settings", RESULT_PATH_FOUND_BUT_NO_MODULE);
//                put("", RESULT_MODULE_FOUND);
//                put("/", RESULT_MODULE_FOUND);
 //               put("!/", RESULT_MODULE_FOUND);
                put("a/b/c", RESULT_PATH_NOT_FOUND);
                put("/a/b/c", RESULT_PATH_NOT_FOUND);
            }
        };
        for (String path : pathsToTest.keySet()) {
            try {
                Module m = this.router.match(path);
                if (pathsToTest.get(path) == RESULT_MODULE_FOUND) {
                    assertTrue(m instanceof Module);
                } else if (pathsToTest.get(path) == RESULT_PATH_FOUND_BUT_NO_MODULE) {
                    assertTrue(m == null);
                } else {
                    fail("unrecognized result type");
                }
            } catch (PathNotFoundException e) {
                if (pathsToTest.get(path) != RESULT_PATH_NOT_FOUND) {
                    fail(e.getMessage());
                }
            }
        }
    }
}
