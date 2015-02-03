package pl.exsio.frameset.core.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pl.exsio.frameset.BaseTest;
import static org.junit.Assert.*;
import pl.exsio.frameset.core.model.Frame;

/**
 *
 * @author exsio
 */
@Transactional
public class FrameRepositoryTest extends BaseTest {

    @Autowired
    protected NestedFrameRepository frameRepository;

    @Test
    public void testFindOne() {

        Object f = this.frameRepository.findOne(new Long(1));
        assertTrue(f instanceof Frame);
        assertEquals(((Frame) f).getId(), new Long(1));
    }

}
