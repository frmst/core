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
package pl.exsio.frameset.core.repository;

import java.io.Serializable;
import pl.exsio.frameset.core.dao.GenericDao;
import pl.exsio.nestedj.model.NestedNode;
import pl.exsio.nestedj.dao.NestedNodeDao;
import pl.exsio.nestedj.ex.InvalidNodesHierarchyException;
import pl.exsio.nestedj.model.Tree;

/**
 *
 * @author exsio
 * @param <T>
 * @param <ID>
 */
public class NestedNodeRepository<T extends NestedNode, ID extends Serializable> implements NestedNodeDao<T>, GenericDao<T, ID>, Serializable {

    private GenericDao baseRepository;

    private NestedNodeDao nestedNodeRepository;

    public void setBaseRepository(GenericDao baseRepository) {
        this.baseRepository = baseRepository;
    }

    public void setNestedNodeRepository(NestedNodeDao nestedNodeRepository) {
        this.nestedNodeRepository = nestedNodeRepository;
    }

    @Override
    public T insertAsFirstChildOf(NestedNode node, NestedNode parent) throws InvalidNodesHierarchyException {
        return (T) this.nestedNodeRepository.insertAsFirstChildOf(node, parent);
    }

    @Override
    public T insertAsLastChildOf(NestedNode node, NestedNode parent) throws InvalidNodesHierarchyException {
        return (T) this.nestedNodeRepository.insertAsLastChildOf(node, parent);
    }

    @Override
    public T insertAsNextSiblingOf(NestedNode node, NestedNode parent) throws InvalidNodesHierarchyException {
        return (T) this.nestedNodeRepository.insertAsNextSiblingOf(node, parent);
    }

    @Override
    public T insertAsPrevSiblingOf(NestedNode node, NestedNode parent) throws InvalidNodesHierarchyException {
        return (T) this.nestedNodeRepository.insertAsPrevSiblingOf(node, parent);
    }

    @Override
    public void removeSingle(NestedNode node) {
        this.nestedNodeRepository.removeSingle(node);
    }

    @Override
    public void removeSubtree(NestedNode node) {
        this.nestedNodeRepository.removeSubtree(node);
    }

    @Override
    public Iterable<T> getTreeAsList(NestedNode node) {
        return this.nestedNodeRepository.getTreeAsList(node);
    }

    @Override
    public Iterable<T> getChildren(NestedNode node) {
        return this.nestedNodeRepository.getChildren(node);
    }

    @Override
    public T getParent(NestedNode node) {
        return (T) this.nestedNodeRepository.getParent(node);
    }
 
    @Override
    public Tree<T> getTree(T node) {
        return this.nestedNodeRepository.getTree(node);
    }

    @Override
    public long count() {
        return this.baseRepository.count();
    }

    @Override
    public void deleteAll() {
        this.baseRepository.deleteAll();
    }

    @Override
    public Iterable<T> findAll() {
        return this.baseRepository.findAll();
    }

    @Override
    public void delete(Serializable id) {
        this.baseRepository.delete(id);
    }

    @Override
    public void delete(Iterable entities) {
        this.baseRepository.delete(entities);
    }

    @Override
    public void delete(T entity) {
        this.baseRepository.delete(entity);
    }

    @Override
    public boolean exists(Serializable id) {
        return this.baseRepository.exists(id);
    }

    @Override
    public Iterable findAll(Iterable ids) {
        return this.baseRepository.findAll(ids);
    }

    @Override
    public T findOne(Serializable id) {
        return (T) this.baseRepository.findOne(id);
    }

    @Override
    public Iterable save(Iterable entities) {
        return this.baseRepository.save(entities);
    }

    @Override
    public <S extends T> S save(S entity) {
        return (S) this.baseRepository.save(entity);
    }

    @Override
    public Iterable<T> getParents(T node) {
        return this.nestedNodeRepository.getParents(node);
    }

    @Override
    public void rebuildTree(Class<? extends NestedNode> nodeClass) throws InvalidNodesHierarchyException {
        this.nestedNodeRepository.rebuildTree(nodeClass);
    }
}
