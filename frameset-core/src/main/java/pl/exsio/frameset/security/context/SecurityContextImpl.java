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
package pl.exsio.frameset.security.context;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.Permission;
import pl.exsio.frameset.security.acl.AclManager;
import pl.exsio.frameset.security.acl.AclSubject;

public class SecurityContextImpl implements SecurityContext {

    private final AclManager acl;

    private final AclSubject subject;

    public SecurityContextImpl(AclManager acl, AclSubject subject) {
        this.acl = acl;
        this.subject = subject;
    }

    @Override
    public boolean canRead() {
        return this.acl.isGranted(subject, BasePermission.READ);
    }

    @Override
    public boolean canWrite() {
        return this.acl.isGranted(subject, BasePermission.WRITE);
    }

    @Override
    public boolean canCreate() {
        return this.acl.isGranted(subject, BasePermission.CREATE);
    }

    @Override
    public boolean canDelete() {
        return this.acl.isGranted(subject, BasePermission.DELETE);
    }

    @Override
    public boolean canAdminister() {
        return this.acl.isGranted(subject, BasePermission.ADMINISTRATION);
    }

    @Override
    public boolean hasPermission(Permission permission) {
        return this.acl.isGranted(subject, permission);
    }

}
