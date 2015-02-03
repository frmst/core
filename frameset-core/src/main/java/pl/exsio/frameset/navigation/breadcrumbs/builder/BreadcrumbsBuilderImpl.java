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
package pl.exsio.frameset.navigation.breadcrumbs.builder;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import pl.exsio.frameset.core.model.Frame;
import pl.exsio.frameset.core.repository.provider.CoreRepositoryProvider;

/**
 *
 * @author exsio
 */
public class BreadcrumbsBuilderImpl implements BreadcrumbsBuilder {

    private CoreRepositoryProvider coreRepositories;


    @Override
    public Set<Frame> build(Frame frame) {

        Set<Frame> breadcrumbs = new LinkedHashSet<>();
        List<Frame> parents = (List<Frame>) this.coreRepositories.getFrameRepository().getParents(frame);
        for (int i = parents.size() - 1; i >= 0; i--) {
            Frame parent = parents.get(i);
            if (!parent.isRoot()) {
                breadcrumbs.add(parent);
            }
        }

        if (!frame.isRoot()) {
            breadcrumbs.add(frame);
        }
        return breadcrumbs;
    }

    public void setCoreRepositories(CoreRepositoryProvider coreRepositories) {
        this.coreRepositories = coreRepositories;
    }
    
    

}
