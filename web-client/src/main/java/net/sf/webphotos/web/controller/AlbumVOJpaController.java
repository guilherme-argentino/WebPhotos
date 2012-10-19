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
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import net.sf.webphotos.model.PhotoVO;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import net.sf.webphotos.model.AlbumVO;
import net.sf.webphotos.web.controller.exceptions.IllegalOrphanException;
import net.sf.webphotos.web.controller.exceptions.NonexistentEntityException;
import net.sf.webphotos.web.controller.exceptions.RollbackFailureException;

/**
 *
 * @author Guilherme
 */
public class AlbumVOJpaController implements Serializable {

    public AlbumVOJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AlbumVO albumVO) throws RollbackFailureException, Exception {
        if (albumVO.getPhotos() == null) {
            albumVO.setPhotos(new HashSet<PhotoVO>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Set<PhotoVO> attachedPhotos = new HashSet<PhotoVO>();
            for (PhotoVO photosPhotoVOToAttach : albumVO.getPhotos()) {
                photosPhotoVOToAttach = em.getReference(photosPhotoVOToAttach.getClass(), photosPhotoVOToAttach.getFotoid());
                attachedPhotos.add(photosPhotoVOToAttach);
            }
            albumVO.setPhotos(attachedPhotos);
            em.persist(albumVO);
            for (PhotoVO photosPhotoVO : albumVO.getPhotos()) {
                AlbumVO oldAlbumOfPhotosPhotoVO = photosPhotoVO.getAlbum();
                photosPhotoVO.setAlbum(albumVO);
                photosPhotoVO = em.merge(photosPhotoVO);
                if (oldAlbumOfPhotosPhotoVO != null) {
                    oldAlbumOfPhotosPhotoVO.getPhotos().remove(photosPhotoVO);
                    oldAlbumOfPhotosPhotoVO = em.merge(oldAlbumOfPhotosPhotoVO);
                }
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

    public void edit(AlbumVO albumVO) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AlbumVO persistentAlbumVO = em.find(AlbumVO.class, albumVO.getAlbumid());
            Set<PhotoVO> photosOld = persistentAlbumVO.getPhotos();
            Set<PhotoVO> photosNew = albumVO.getPhotos();
            List<String> illegalOrphanMessages = null;
            for (PhotoVO photosOldPhotoVO : photosOld) {
                if (!photosNew.contains(photosOldPhotoVO)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PhotoVO " + photosOldPhotoVO + " since its album field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Set<PhotoVO> attachedPhotosNew = new HashSet<PhotoVO>();
            for (PhotoVO photosNewPhotoVOToAttach : photosNew) {
                photosNewPhotoVOToAttach = em.getReference(photosNewPhotoVOToAttach.getClass(), photosNewPhotoVOToAttach.getFotoid());
                attachedPhotosNew.add(photosNewPhotoVOToAttach);
            }
            photosNew = attachedPhotosNew;
            albumVO.setPhotos(photosNew);
            albumVO = em.merge(albumVO);
            for (PhotoVO photosNewPhotoVO : photosNew) {
                if (!photosOld.contains(photosNewPhotoVO)) {
                    AlbumVO oldAlbumOfPhotosNewPhotoVO = photosNewPhotoVO.getAlbum();
                    photosNewPhotoVO.setAlbum(albumVO);
                    photosNewPhotoVO = em.merge(photosNewPhotoVO);
                    if (oldAlbumOfPhotosNewPhotoVO != null && !oldAlbumOfPhotosNewPhotoVO.equals(albumVO)) {
                        oldAlbumOfPhotosNewPhotoVO.getPhotos().remove(photosNewPhotoVO);
                        oldAlbumOfPhotosNewPhotoVO = em.merge(oldAlbumOfPhotosNewPhotoVO);
                    }
                }
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
                Integer id = albumVO.getAlbumid();
                if (findAlbumVO(id) == null) {
                    throw new NonexistentEntityException("The albumVO with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AlbumVO albumVO;
            try {
                albumVO = em.getReference(AlbumVO.class, id);
                albumVO.getAlbumid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The albumVO with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Set<PhotoVO> photosOrphanCheck = albumVO.getPhotos();
            for (PhotoVO photosOrphanCheckPhotoVO : photosOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This AlbumVO (" + albumVO + ") cannot be destroyed since the PhotoVO " + photosOrphanCheckPhotoVO + " in its photos field has a non-nullable album field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(albumVO);
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

    public List<AlbumVO> findAlbumVOEntities() {
        return findAlbumVOEntities(true, -1, -1);
    }

    public List<AlbumVO> findAlbumVOEntities(int maxResults, int firstResult) {
        return findAlbumVOEntities(false, maxResults, firstResult);
    }

    private List<AlbumVO> findAlbumVOEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AlbumVO.class));
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

    public AlbumVO findAlbumVO(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AlbumVO.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlbumVOCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AlbumVO> rt = cq.from(AlbumVO.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
