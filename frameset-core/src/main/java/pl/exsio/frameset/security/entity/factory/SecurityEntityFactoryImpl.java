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
package pl.exsio.frameset.security.entity.factory;

import pl.exsio.frameset.security.entity.GroupImpl;
import pl.exsio.frameset.security.entity.RoleImpl;
import pl.exsio.frameset.security.entity.UserImpl;
import pl.exsio.frameset.security.model.Group;
import pl.exsio.frameset.security.model.Role;
import pl.exsio.frameset.security.model.User;


public class SecurityEntityFactoryImpl implements SecurityEntityFactory {

    @Override
    public <T extends User> T newUser() {
        return (T) new UserImpl();
    }

    @Override
    public <T extends Group> T newGroup() {
        return (T) new GroupImpl();
    }

    @Override
    public <T extends Role> T newRole() {
        return (T) new RoleImpl();
    }

    @Override
    public <T extends User> Class<T> getUserClass() {
        return (Class<T>) UserImpl.class;
    }

    @Override
    public <T extends Group> Class<T> getGroupClass() {
        return (Class<T>) GroupImpl.class;
    }

    @Override
    public <T extends Role> Class<T> getRoleClass() {
        return (Class<T>) RoleImpl.class;
    }
    
}
