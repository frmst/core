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
package pl.exsio.frameset.security.context.factory;

import pl.exsio.frameset.security.acl.AclManager;
import pl.exsio.frameset.security.acl.AclSubject;
import pl.exsio.frameset.security.context.MultiSecurityContextImpl;
import pl.exsio.frameset.security.context.SecurityContext;
import pl.exsio.frameset.security.context.SecurityContextImpl;

public class SecurityContextFactoryImpl implements SecurityContextFactory {

    private AclManager acl;

    public void setAcl(AclManager acl) {
        this.acl = acl;
    }

    @Override
    public SecurityContext create(AclSubject subject) {
        return new SecurityContextImpl(acl, subject);
    }

    @Override
    public SecurityContext createOptimistic(AclSubject... subjects) {
        MultiSecurityContextImpl ctx = new MultiSecurityContextImpl(acl, subjects);
        ctx.setMode(MultiSecurityContextImpl.SECURITY_MODE_ANY);
        return ctx;
    }

    @Override
    public SecurityContext createPessimistic(AclSubject... subjects) {
        MultiSecurityContextImpl ctx = new MultiSecurityContextImpl(acl, subjects);
        ctx.setMode(MultiSecurityContextImpl.SECURITY_MODE_ALL);
        return ctx;
    }

}
