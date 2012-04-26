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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.webphotos.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Guilhe
 */
@Entity
@Table(name = "CATEGORIAS")
@NamedQueries({
    @NamedQuery(name = "CategoryVO.findByCategoriaID", query = "SELECT c FROM CategoryVO c WHERE c.categoriaID = :categoriaID"),
    @NamedQuery(name = "CategoryVO.findByNmcategoria", query = "SELECT c FROM CategoryVO c WHERE c.nmcategoria = :nmcategoria")})
public class CategoryVO implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "CATEGORIAID", nullable = false)
    private Integer categoriaID;
    @Column(name = "NMCATEGORIA", nullable = false)
    private String nmcategoria;
    @ManyToOne(optional = true)
    @JoinColumn(name = "SUBCATEGORIA")
    private CategoryVO categoriaPai;

    public CategoryVO() {
    }

    public CategoryVO(Integer categoriaid) {
        this.categoriaID = categoriaid;
    }

    public CategoryVO(Integer categoriaID, String nmcategoria) {
        this.categoriaID = categoriaID;
        this.nmcategoria = nmcategoria;
    }

    public Integer getCategoriaID() {
        return categoriaID;
    }

    public void setCategoriaID(Integer categoriaid) {
        this.categoriaID = categoriaid;
    }

    public String getNmcategoria() {
        return nmcategoria;
    }

    public void setNmcategoria(String nmcategoria) {
        this.nmcategoria = nmcategoria;
    }

    /**
     * @return the categoriaPai
     */
    public CategoryVO getCategoriaPai() {
        return categoriaPai;
    }

    /**
     * @param categoriaPai the categoriaPai to set
     */
    public void setCategoriaPai(CategoryVO categoriaPai) {
        this.categoriaPai = categoriaPai;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (categoriaID != null ? categoriaID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoryVO)) {
            return false;
        }
        CategoryVO other = (CategoryVO) object;
        if ((this.categoriaID == null && other.categoriaID != null) || (this.categoriaID != null && !this.categoriaID.equals(other.categoriaID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.nom.guilherme.webfotos.dao.CategoryVO[categoriaid=" + categoriaID + "]";
    }
}
