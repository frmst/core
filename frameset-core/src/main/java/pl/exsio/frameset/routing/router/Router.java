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
package pl.exsio.frameset.routing.router;

import java.util.Map;
import pl.exsio.frameset.core.model.Frame;
import pl.exsio.frameset.core.model.Module;
import pl.exsio.frameset.routing.ex.PathNotFoundException;

/**
 *
 * @author exsio
 * @param <T>
 */
public interface Router<T extends Module> {
    
    /**
     * Level field name of a BaseFrame
     */
    String LEVEL_FIELD = "lvl";
    
    /**
     * Chilrend field name of a BaseFrame
     */
    String CHILDREN_FIELD = "children";
    
    /**
     * Slug field name of a BaseFrame
     */
    String SLUG_FIELD = "slug";
    
    Frame matchFrame(String path) throws PathNotFoundException;
    
    Frame matchFrame(String path, Class<? extends Frame> frameClass, Map<String, String> propertiesMap) throws PathNotFoundException;
    
    T match(String path) throws PathNotFoundException;
    
    T match(String path, Class<? extends Frame> frameClass, Map<String, String> propertiesMap) throws PathNotFoundException;
    
    void setPathSegmentDelimiter(String delimiter);
}
