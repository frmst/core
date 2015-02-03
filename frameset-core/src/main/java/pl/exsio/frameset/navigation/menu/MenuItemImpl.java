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
package pl.exsio.frameset.navigation.menu;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import pl.exsio.frameset.core.model.Frame;

/**
 *
 * @author exsio
 */
public class MenuItemImpl implements MenuItem {
    
    private String label;
    
    private Frame frame;
    
    private MenuItem parent;
    
    private final List<MenuItem> children;
    
    private final Map<String, Object> params;
    
    public MenuItemImpl() {
        this.children = new LinkedList<MenuItem>();
        this.params = new HashMap<String, Object>();
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public Frame getFrame() {
        return frame;
    }

    @Override
    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    @Override
    public MenuItem getParent() {
        return parent;
    }

    @Override
    public void setParent(MenuItem parent) {
        this.parent = parent;
    }

    @Override
    public List<MenuItem> getChildren() {
        return children;
    }

    @Override
    public void addChild(MenuItem item) {
        this.children.add(item);
        item.setParent(this);
    }

    @Override
    public void addParam(String key, Object value) {
        this.params.put(key, value);
    }

    @Override
    public Object getParam(String key) {
        if(this.params.containsKey(key)) {
            return this.params.get(key);
        } else {
            return null;
        }
    }
    
    @Override
    public Map<String, Object> getParams() {
        return this.params;
    } 
}
