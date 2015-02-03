package pl.exsio.frameset.navigation.menu.builder;

import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import pl.exsio.frameset.BaseTest;
import pl.exsio.frameset.core.repository.NestedFrameRepository;
import pl.exsio.frameset.core.repository.provider.CoreRepositoryProvider;
import pl.exsio.frameset.navigation.menu.MenuItem;
import pl.exsio.frameset.security.acl.AclManager;
import pl.exsio.frameset.security.acl.AclSubject;

/**
 *
 * @author exsio
 */
public class MenuBuilderImplTest extends BaseTest {

    @Autowired
    private NestedFrameRepository repository;

    @Autowired
    private CoreRepositoryProvider coreRepositories;

    public MenuBuilderImplTest() {
    }

    /**
     * Test of build method, of class MenuBuilderImpl.
     */
    @Test
    public void testBuild() {

        MenuItem item = this.getBuilder().build(this.repository.findOne(new Long(1)));
        assertTrue("".equals(item.getLabel()));
        assertTrue(item.getChildren().size() == 2);
        assertTrue(item.getChildren().get(0).getChildren().isEmpty());
        assertTrue(item.getChildren().get(1).getChildren().isEmpty());
    }

    /**
     * Test of build method, of class MenuBuilderImpl.
     */
    @Test
    public void testBuildRecurse() {
        MenuItem item = this.getBuilder().build(this.repository.findOne(new Long(1)), true);
        assertTrue("".equals(item.getLabel()));
        assertTrue(item.getChildren().size() == 2);
        assertTrue(item.getChildren().get(0).getChildren().isEmpty());
        assertTrue(item.getChildren().get(1).getChildren().size() == 4);
    }

    private MenuBuilderImpl getBuilder() {
        MenuBuilderImpl builder = new MenuBuilderImpl();
        builder.setAcl(this.getAclManager());
        builder.setCoreRepositories(coreRepositories);
        return builder;
    }

    private AclManager getAclManager() {
        AclManager acl = mock(AclManager.class);
        when(acl.isGranted(any(AclSubject.class), any(Permission.class))).thenReturn(Boolean.TRUE);
        when(acl.isGranted(any(AclSubject.class), any(List.class))).thenReturn(Boolean.TRUE);
        when(acl.isGranted(any(AclSubject.class), any(Permission.class), any(Sid.class))).thenReturn(Boolean.TRUE);
        when(acl.isGranted(any(AclSubject.class), any(List.class), any(List.class))).thenReturn(Boolean.TRUE);
        return acl;
    }

}
