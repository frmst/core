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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import pl.exsio.frameset.core.entity.FrameImpl;
import pl.exsio.frameset.core.model.Frame;
import pl.exsio.frameset.core.model.Module;
import pl.exsio.frameset.routing.ex.PathNotFoundException;
import pl.exsio.frameset.routing.modulelocator.ModuleLocator;

/**
 *
 * @author exsio
 * @param <T>
 */
public class RouterImpl<T extends Module> implements Router<T> {

    @PersistenceContext
    private EntityManager em;

    private ModuleLocator<T> moduleLocator;

    private String pathSegmentDelimiter = "/";

    public void setModuleLocator(ModuleLocator<T> moduleLocator) {
        this.moduleLocator = moduleLocator;
    }

    @Override
    public void setPathSegmentDelimiter(String delimiter) {
        this.pathSegmentDelimiter = delimiter;
    }

    @Override
    public Frame matchFrame(String path) throws PathNotFoundException {
        return this.matchFrame(path, FrameImpl.class, new HashMap<String, String>() {
            {
                put(CHILDREN_FIELD, CHILDREN_FIELD);
                put(LEVEL_FIELD, LEVEL_FIELD);
                put(SLUG_FIELD, SLUG_FIELD);
            }
        });
    }

    @Override
    public Frame matchFrame(String path, Class<? extends Frame> frameClass, Map<String, String> propertiesMap) throws PathNotFoundException {
        Query query = em.createQuery(this.getQuery(path, frameClass, propertiesMap));
        Frame frame = null;
        try {
            frame = (Frame) query.setMaxResults(1).getSingleResult();
            if (frame instanceof Frame) {
                return frame;
            } else {
                throw this.getExeptionForPath(path);
            }
        } catch (NoResultException ex) {
            throw this.getExeptionForPath(path);
        }
    }

    @Override
    public T match(String path) throws PathNotFoundException {
        return this.match(path, FrameImpl.class, new HashMap<String, String>() {
            {
                put(CHILDREN_FIELD, CHILDREN_FIELD);
                put(LEVEL_FIELD, LEVEL_FIELD);
                put(SLUG_FIELD, SLUG_FIELD);
            }
        });
    }

    @Override
    public T match(String path, Class<? extends Frame> frameClass, Map<String, String> propertiesMap) throws PathNotFoundException {
        return this.getModuleForFrame(this.matchFrame(path, frameClass, propertiesMap));
    }

    private PathNotFoundException getExeptionForPath(String path) {
        return new PathNotFoundException("no Frame found for path '" + path + "'");
    }

    private T getModuleForFrame(Frame frame) {
        return this.moduleLocator.locate(frame);
    }

    /**
     * Creates a CriteriaQuery for looking up the persistence layer for a Frame
     * instance, that matches given path. It converts a String path to an array
     * of path segments. For each segment (starting with root segment) a child
     * Frame is joined.
     */
    private CriteriaQuery getQuery(String path, Class<? extends Frame> frameClass, Map<String, String> propertiesMap) {
        String[] pathArray = this.convertPathToArray(path);
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaQuery q;
        q = cb.createQuery(frameClass);
        Root<Frame> root = q.from(frameClass);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get(propertiesMap.get(SLUG_FIELD)), this.getRootPathSegment(pathArray)));
        predicates.add(cb.equal(root.get(propertiesMap.get(LEVEL_FIELD)), this.getLookupStartingLevel(pathArray)));
        From from = root;
        for (int i = 1; i < pathArray.length; i++) {
            Join<Frame, Frame> join = from.join(propertiesMap.get(CHILDREN_FIELD), JoinType.INNER);
            predicates.add(cb.equal(join.get(propertiesMap.get(SLUG_FIELD)), pathArray[i]));
            from = join;
        }
        q.select(from);
        q.where(predicates.toArray(new Predicate[]{}));
        return q;
    }

    private String getRootPathSegment(String[] pathArray) {
        if (pathArray.length > 0) {
            return pathArray[0];
        } else {
            return "";
        }
    }

    private Integer getLookupStartingLevel(String[] pathArray) {
        if (pathArray.length > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    private String[] convertPathToArray(String path) {

        if (path != null && path.startsWith("!")) {
            path = path.substring(1);
        }
        if (path != null && path.startsWith("/")) {
            path = path.substring(1);
        }
        String[] pathArray = null;
        try {
            pathArray = path.trim().split(this.pathSegmentDelimiter);
            if (pathArray.length == 1 && pathArray[0].trim().equals("")) {
                pathArray = new String[0];
            }
        } catch (NullPointerException ex) {
            pathArray = new String[0];
        }
        return pathArray;

    }

}
