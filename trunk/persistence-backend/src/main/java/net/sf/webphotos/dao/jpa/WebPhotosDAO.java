/**
 * Copyright 2008 WebPhotos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.webphotos.dao.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import net.sf.webphotos.HasID;

/**
 *
 * @author Guilherme L A Silva
 */
public class WebPhotosDAO<E extends HasID, I> {

    protected EntityManager entityManager;
    private Class<E> entityClass;
    private Class<I> keyClass;

    public WebPhotosDAO(Class<E> entityClass, Class<I> keyClass) {
        this.entityClass = entityClass;
        this.keyClass = keyClass;
    }

    /**
     * Workarround for a rapid migration from RowSet
     *
     * @param query
     * @return
     * @deprecated
     */
    @Deprecated
    public Query createNativeQuery(String query) {
        return entityManager.createNativeQuery(query);
    }

    public E findBy(I id) {
        return entityManager.find(this.entityClass, id);
    }

    public void save(E object) throws Exception {
        if (object.getId() != null && object.equals(entityManager.find(entityClass, object.getId()))) {
            entityManager.merge(object);
        } else {
            entityManager.persist(object);
        }
    }

    public void remove(E object) throws Exception {
        entityManager.remove(object);
        entityManager.flush();
    }

    /**
     * @return the entityManager
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * @param entityManager the entityManager to set
     */
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Workarround for a rapid migration from RowSet
     *
     * @param query
     * @return
     * @deprecated
     */
    @Deprecated
    public List<Object[]> findByNativeQuery(String query) {
        return createNativeQuery(query).getResultList();
    }

    @SuppressWarnings(value = "unchecked")
    protected List<E> find(String query) {
        return entityManager.createQuery(query).getResultList();
    }

    @SuppressWarnings(value = "unchecked")
    protected List<E> findByNamedQuery(String query) {
        return entityManager.createNamedQuery(query).getResultList();
    }
}
