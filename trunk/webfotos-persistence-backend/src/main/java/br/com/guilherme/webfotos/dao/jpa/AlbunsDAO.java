/**
 * 
 */
package br.com.guilherme.webfotos.dao.jpa;

import java.util.List;

import br.com.guilherme.webfotos.model.AlbunsVO;

/**
 * @author Guilhe
 *
 */
public class AlbunsDAO extends WebFotosDAO<AlbunsVO> {

	public List<AlbunsVO> findAll() {
		return find("SELECT o FROM AlbunsVO");
	}
	
	public List<AlbunsVO> findByCreditoId() {
		return findByNamedQuery("");
	}
	
	@SuppressWarnings("unchecked")
	protected List<AlbunsVO> find(String query) {
		return entityManager.createQuery(query).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	protected List<AlbunsVO> findByNamedQuery(String query) {
		return entityManager.createNamedQuery(query).getResultList();
	}
	
}
