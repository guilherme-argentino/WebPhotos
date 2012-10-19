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
import net.sf.webphotos.model.AlbumVO;
import net.sf.webphotos.model.PhotoVO;
import net.sf.webphotos.web.controller.exceptions.NonexistentEntityException;
import net.sf.webphotos.web.controller.exceptions.RollbackFailureException;

/**
 *
 * @author Guilherme
 */
public class PhotoVOJpaController implements Serializable {

    public PhotoVOJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PhotoVO photoVO) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AlbumVO album = photoVO.getAlbum();
            if (album != null) {
                album = em.getReference(album.getClass(), album.getAlbumid());
                photoVO.setAlbum(album);
            }
            em.persist(photoVO);
            if (album != null) {
                album.getPhotos().add(photoVO);
                album = em.merge(album);
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

    public void edit(PhotoVO photoVO) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            PhotoVO persistentPhotoVO = em.find(PhotoVO.class, photoVO.getFotoid());
            AlbumVO albumOld = persistentPhotoVO.getAlbum();
            AlbumVO albumNew = photoVO.getAlbum();
            if (albumNew != null) {
                albumNew = em.getReference(albumNew.getClass(), albumNew.getAlbumid());
                photoVO.setAlbum(albumNew);
            }
            photoVO = em.merge(photoVO);
            if (albumOld != null && !albumOld.equals(albumNew)) {
                albumOld.getPhotos().remove(photoVO);
                albumOld = em.merge(albumOld);
            }
            if (albumNew != null && !albumNew.equals(albumOld)) {
                albumNew.getPhotos().add(photoVO);
                albumNew = em.merge(albumNew);
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
                Integer id = photoVO.getFotoid();
                if (findPhotoVO(id) == null) {
                    throw new NonexistentEntityException("The photoVO with id " + id + " no longer exists.");
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
            PhotoVO photoVO;
            try {
                photoVO = em.getReference(PhotoVO.class, id);
                photoVO.getFotoid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The photoVO with id " + id + " no longer exists.", enfe);
            }
            AlbumVO album = photoVO.getAlbum();
            if (album != null) {
                album.getPhotos().remove(photoVO);
                album = em.merge(album);
            }
            em.remove(photoVO);
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

    public List<PhotoVO> findPhotoVOEntities() {
        return findPhotoVOEntities(true, -1, -1);
    }

    public List<PhotoVO> findPhotoVOEntities(int maxResults, int firstResult) {
        return findPhotoVOEntities(false, maxResults, firstResult);
    }

    private List<PhotoVO> findPhotoVOEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PhotoVO.class));
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

    public PhotoVO findPhotoVO(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PhotoVO.class, id);
        } finally {
            em.close();
        }
    }

    public int getPhotoVOCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PhotoVO> rt = cq.from(PhotoVO.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
