package pl.exsio.frameset;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author exsio
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/frameset-core-context.xml","classpath:/frameset-core-test-context.xml"})
public abstract class BaseTest {
    
}
