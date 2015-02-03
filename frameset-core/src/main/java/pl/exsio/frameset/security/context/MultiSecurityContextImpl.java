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

public class MultiSecurityContextImpl implements SecurityContext {

    private final AclManager acl;

    private final AclSubject[] subjects;
    
    public final static int SECURITY_MODE_ANY = 0;
    
    public final static int SECURITY_MODE_ALL = 1;
    
    private int mode = SECURITY_MODE_ALL;

    public MultiSecurityContextImpl(AclManager acl, AclSubject... subjects) {
        this.acl = acl;
        this.subjects = subjects;
    }

    @Override
    public boolean canRead() {
        return this.hasPermission(BasePermission.READ);
    }

    @Override
    public boolean canWrite() {
        return this.hasPermission(BasePermission.WRITE);
    }

    @Override
    public boolean canCreate() {
        return this.hasPermission(BasePermission.CREATE);
    }

    @Override
    public boolean canDelete() {
        return this.hasPermission(BasePermission.DELETE);
    }

    @Override
    public boolean canAdminister() {
        return this.hasPermission(BasePermission.ADMINISTRATION);
    }

    @Override
    public boolean hasPermission(Permission permission) {
        for(AclSubject subject: this.subjects) {
            if(this.acl.isGranted(subject, permission)) {
                if(this.mode == SECURITY_MODE_ANY) {
                    return true;
                }
            } else {
                if(this.mode == SECURITY_MODE_ALL) {
                    return false;
                }
            }
        }
        return this.getStartingConditions();
    }

    private boolean getStartingConditions() {
        return this.mode == SECURITY_MODE_ALL;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
