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
package pl.exsio.frameset.security.context.provider;

import pl.exsio.frameset.security.acl.AclSubject;
import pl.exsio.frameset.security.context.SecurityContext;
import pl.exsio.frameset.security.context.factory.SecurityContextFactory;

/**
 *
 * @author exsio
 */
public class SecurityContextProvider {

    private static SecurityContextFactory factory;

    public void setFactory(SecurityContextFactory factory) {
        SecurityContextProvider.factory = factory;
    }

    public static SecurityContext getFor(AclSubject subject) {
        return SecurityContextProvider.factory.create(subject);
    }
    
    public static SecurityContext getOptimistic(AclSubject... subjects) {
        return SecurityContextProvider.factory.createOptimistic(subjects);
    }
    
    public static SecurityContext getPessimistic(AclSubject... subjects) {
        return SecurityContextProvider.factory.createPessimistic(subjects);
    }

}
