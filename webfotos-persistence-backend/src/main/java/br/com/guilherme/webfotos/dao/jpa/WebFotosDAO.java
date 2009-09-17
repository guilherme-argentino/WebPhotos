/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.guilherme.webfotos.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Guilherme L A Silva
 */
public class WebFotosDAO<E> {

    @PersistenceContext
    protected EntityManager entityManager;

    public void save(E object) throws Exception {
        try {
        	if(entityManager.find(object.getClass(), object) != null) {
        		entityManager.merge(object);
        	} else {
        		entityManager.persist(object);
        	}
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }

    public void remove(E object) throws Exception {
        try {
            entityManager.remove(object);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }
    
}
