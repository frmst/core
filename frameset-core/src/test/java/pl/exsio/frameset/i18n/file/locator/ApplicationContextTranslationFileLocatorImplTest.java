package pl.exsio.frameset.i18n.file.locator;

import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import pl.exsio.frameset.i18n.I18nBaseApplicationContextAwaresTest;

/**
 *
 * @author exsio
 */

public class ApplicationContextTranslationFileLocatorImplTest extends I18nBaseApplicationContextAwaresTest {

    @Autowired
    private ApplicationContext ctx;

    /**
     * Test of locateFile method, of class
     * ApplicationContextTranslationFileLocatorImpl.
     */
    @Test
    public void testLocateFile() throws Exception {

        try {
            ApplicationContextTranslationFileLocatorImpl instance = new ApplicationContextTranslationFileLocatorImpl();
            instance.setApplicationContext(this.ctx);
            InputStream is = instance.locateFile(TEST_TRANSLATION_PATH_SPRING);
            assertNotNull(is);
        } catch (IOException ex) {
            fail("unable to locate file: " + ex.getMessage());
        }
    }

}
