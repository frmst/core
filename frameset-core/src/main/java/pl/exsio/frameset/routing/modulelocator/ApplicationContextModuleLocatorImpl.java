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
package pl.exsio.frameset.routing.modulelocator;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import pl.exsio.frameset.core.model.EmptyModule;
import pl.exsio.frameset.core.model.Frame;
import pl.exsio.frameset.core.model.Module;

/**
 *
 * @author exsio
 * @param <T>
 */
public class ApplicationContextModuleLocatorImpl<T extends Module> implements ModuleLocator<T> {

    @Autowired
    private ApplicationContext context;

    @Override
    public T locate(Frame frame) {
        try {
            Module module = null;
            if (this.moduleIdIsNotEmpty(frame)) {
                module = (T) this.context.getBean(frame.getModuleId());
            } else {
                module = new EmptyModule();
            }
            return (T) module;
        } catch (NoSuchBeanDefinitionException ex) {
            return null;
        }
    }

    private boolean moduleIdIsNotEmpty(Frame frame) {
        return frame.getModuleId() != null && !frame.getModuleId().trim().equals("");
    }

}
