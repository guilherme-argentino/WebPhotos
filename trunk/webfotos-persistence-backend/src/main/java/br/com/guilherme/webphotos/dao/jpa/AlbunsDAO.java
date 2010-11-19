/**
 *  Copyright 2009 gsilva.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */
package br.com.guilherme.webphotos.dao.jpa;

import java.util.List;

import br.com.guilherme.webphotos.model.AlbunsVO;

/**
 * @author Guilhe
 *
 */
public class AlbunsDAO extends WebFotosDAO<AlbunsVO> {

    public List<AlbunsVO> findAll() {
        return find("SELECT a FROM AlbunsVO a");
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