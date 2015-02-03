package pl.exsio.frameset.i18n;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author exsio
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/i18n-test-ctx.xml"})
public abstract class I18nBaseApplicationContextAwaresTest {

    protected static final String TEST_TRANSLATION_PATH_SPRING = "classpath:i18n/test-translations.yml";
}
