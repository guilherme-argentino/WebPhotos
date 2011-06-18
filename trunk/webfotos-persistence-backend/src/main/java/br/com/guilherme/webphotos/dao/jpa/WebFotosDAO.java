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
package br.com.guilherme.webphotos.dao.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Guilherme L A Silva
 */
public class WebFotosDAO<E, I> {

    @PersistenceContext
    protected EntityManager entityManager;

    private Class<E> entityClass;

    public WebFotosDAO(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    public E findBy(I id) {
        return entityManager.find(this.entityClass, id);
    }

    public void save(E object) throws Exception {
        try {
            if (getEntityManager().find(object.getClass(), object) != null) {
                getEntityManager().merge(object);
            } else {
                getEntityManager().persist(object);
            }
        } catch (Exception e) {
            getEntityManager().getTransaction().rollback();
            throw e;
        }
    }

    public void remove(E object) throws Exception {
        try {
            getEntityManager().remove(object);
        } catch (Exception e) {
            getEntityManager().getTransaction().rollback();
            throw e;
        }
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
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    /**
     * Workarround for a rapid migration from RowSet
     * @param query
     * @return
     * @deprecated
     */
    @Deprecated
    public List<Object[]> findByNativeQuery(String query) {
        return entityManager.createNativeQuery(query).getResultList();
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
