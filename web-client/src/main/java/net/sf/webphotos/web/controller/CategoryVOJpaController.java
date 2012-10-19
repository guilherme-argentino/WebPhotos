/*
 * Copyright 2012 Guilherme.
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
package net.sf.webphotos.web.controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import net.sf.webphotos.model.CategoryVO;
import net.sf.webphotos.web.controller.exceptions.NonexistentEntityException;
import net.sf.webphotos.web.controller.exceptions.RollbackFailureException;

/**
 *
 * @author Guilherme
 */
public class CategoryVOJpaController implements Serializable {

    public CategoryVOJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CategoryVO categoryVO) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CategoryVO categoriaPai = categoryVO.getCategoriaPai();
            if (categoriaPai != null) {
                categoriaPai = em.getReference(categoriaPai.getClass(), categoriaPai.getCategoriaID());
                categoryVO.setCategoriaPai(categoriaPai);
            }
            em.persist(categoryVO);
            if (categoriaPai != null) {
                CategoryVO oldCategoriaPaiOfCategoriaPai = categoriaPai.getCategoriaPai();
                if (oldCategoriaPaiOfCategoriaPai != null) {
                    oldCategoriaPaiOfCategoriaPai.setCategoriaPai(null);
                    oldCategoriaPaiOfCategoriaPai = em.merge(oldCategoriaPaiOfCategoriaPai);
                }
                categoriaPai.setCategoriaPai(categoryVO);
                categoriaPai = em.merge(categoriaPai);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CategoryVO categoryVO) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CategoryVO persistentCategoryVO = em.find(CategoryVO.class, categoryVO.getCategoriaID());
            CategoryVO categoriaPaiOld = persistentCategoryVO.getCategoriaPai();
            CategoryVO categoriaPaiNew = categoryVO.getCategoriaPai();
            if (categoriaPaiNew != null) {
                categoriaPaiNew = em.getReference(categoriaPaiNew.getClass(), categoriaPaiNew.getCategoriaID());
                categoryVO.setCategoriaPai(categoriaPaiNew);
            }
            categoryVO = em.merge(categoryVO);
            if (categoriaPaiOld != null && !categoriaPaiOld.equals(categoriaPaiNew)) {
                categoriaPaiOld.setCategoriaPai(null);
                categoriaPaiOld = em.merge(categoriaPaiOld);
            }
            if (categoriaPaiNew != null && !categoriaPaiNew.equals(categoriaPaiOld)) {
                CategoryVO oldCategoriaPaiOfCategoriaPai = categoriaPaiNew.getCategoriaPai();
                if (oldCategoriaPaiOfCategoriaPai != null) {
                    oldCategoriaPaiOfCategoriaPai.setCategoriaPai(null);
                    oldCategoriaPaiOfCategoriaPai = em.merge(oldCategoriaPaiOfCategoriaPai);
                }
                categoriaPaiNew.setCategoriaPai(categoryVO);
                categoriaPaiNew = em.merge(categoriaPaiNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = categoryVO.getCategoriaID();
                if (findCategoryVO(id) == null) {
                    throw new NonexistentEntityException("The categoryVO with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CategoryVO categoryVO;
            try {
                categoryVO = em.getReference(CategoryVO.class, id);
                categoryVO.getCategoriaID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoryVO with id " + id + " no longer exists.", enfe);
            }
            CategoryVO categoriaPai = categoryVO.getCategoriaPai();
            if (categoriaPai != null) {
                categoriaPai.setCategoriaPai(null);
                categoriaPai = em.merge(categoriaPai);
            }
            em.remove(categoryVO);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CategoryVO> findCategoryVOEntities() {
        return findCategoryVOEntities(true, -1, -1);
    }

    public List<CategoryVO> findCategoryVOEntities(int maxResults, int firstResult) {
        return findCategoryVOEntities(false, maxResults, firstResult);
    }

    private List<CategoryVO> findCategoryVOEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CategoryVO.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public CategoryVO findCategoryVO(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CategoryVO.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoryVOCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CategoryVO> rt = cq.from(CategoryVO.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
