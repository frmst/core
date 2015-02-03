package pl.exsio.frameset.i18n.locale.provider.provider;

import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import pl.exsio.frameset.i18n.I18nBaseApplicationContextAwaresTest;
import pl.exsio.jin.locale.provider.LocaleProvider;

/**
 *
 * @author exsio
 */
public class ApplicationContextLocaleProviderProviderImplTest extends I18nBaseApplicationContextAwaresTest {

    @Autowired
    private ApplicationContext ctx;

    /**
     * Test of getLocaleProvider method, of class
     * ApplicationContextLocaleProviderProviderImpl.
     */
    @Test
    public void testGetLocaleProvider() {

        ApplicationContextLocaleProviderProviderImpl instance = new ApplicationContextLocaleProviderProviderImpl();
        instance.setApplicationContext(this.ctx);
        LocaleProvider result = instance.getLocaleProvider();
        assertNotNull(result);

    }

}
