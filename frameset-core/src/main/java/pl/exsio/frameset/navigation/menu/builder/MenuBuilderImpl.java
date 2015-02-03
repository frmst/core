/* 
 * The MIT License
 *
 * Copyright 2014 exsio.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package pl.exsio.frameset.navigation.menu.builder;

import org.springframework.security.acls.domain.BasePermission;
import pl.exsio.frameset.core.dao.FrameDao;
import pl.exsio.frameset.core.model.Frame;
import pl.exsio.frameset.core.repository.provider.CoreRepositoryProvider;
import pl.exsio.frameset.navigation.menu.MenuItem;
import pl.exsio.frameset.navigation.menu.MenuItemImpl;
import pl.exsio.frameset.security.acl.AclManager;

/**
 *
 * @author exsio
 */
public class MenuBuilderImpl implements MenuBuilder {

    private CoreRepositoryProvider coreRepositories;

    private AclManager acl;

    @Override
    public MenuItem build(Frame root) {
        return this.build(root, false);
    }

    @Override
    public MenuItem build(Frame root, boolean recurse) {
        MenuItem item = new MenuItemImpl();
        if (this.acl.isGranted(root, BasePermission.READ)) {
            item.setLabel(root.getMenuLabel());
            item.setFrame(root);
            return this.addChildren(item, root, recurse);
        } else {
            return item;
        }
    }

    private MenuItem addChildren(MenuItem parent, Frame frame, boolean recurse) {

        for (Frame child : this.coreRepositories.getFrameRepository().getChildren(frame)) {
            if (this.acl.isGranted(child, BasePermission.READ)) {
                MenuItem item = new MenuItemImpl();
                item.setLabel(child.getMenuLabel());
                item.setFrame(child);
                if (recurse) {
                    item = this.addChildren(item, child, recurse);
                }
                parent.addChild(item);
            }
        }
        return parent;
    }

    public void setAcl(AclManager acl) {
        this.acl = acl;
    }

    public void setCoreRepositories(CoreRepositoryProvider coreRepositories) {
        this.coreRepositories = coreRepositories;
    }

}
