package pl.exsio.frameset.routing.modulelocator;

import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import pl.exsio.frameset.BaseTest;
import pl.exsio.frameset.core.model.EmptyModule;
import pl.exsio.frameset.core.model.Frame;
import pl.exsio.frameset.core.model.Module;
import pl.exsio.frameset.core.repository.FrameRepository;

/**
 *
 * @author exsio
 */
public class ModuleLocatorImplTest extends BaseTest {

    @Autowired
    private ModuleLocator locator;

    @Autowired
    private FrameRepository repository;

    @Autowired
    private ApplicationContext ctx;

    public ModuleLocatorImplTest() {
    }

    /**
     * Test of locate method, of class DefaultModuleLocator.
     */
    @Test
    public void testLocate() {
        this.ctx.getBean("homeModule");
        Map<Long, Class<? extends Module>> classMap = new HashMap<Long, Class<? extends Module>>() {
            {
                put(1l, Module.class);
                put(3l, EmptyModule.class);
                put(7l, null);
            }
        };

        for (Long i : classMap.keySet()) {
            Frame frame = this.repository.findOne(i);
            Class<? extends Module> moduleClass = classMap.get(i);
            Module m = this.locator.locate(frame);
            if (EmptyModule.class.equals(moduleClass)) {
                assertTrue(EmptyModule.class.isInstance(m));
            } else if (Module.class.equals(moduleClass)) {
                System.out.println(this.repository.findAll().iterator().next().getId());
                assertTrue(moduleClass.isInstance(m));
            } else {
                assertNull(m);
            }
        }
    }

}
