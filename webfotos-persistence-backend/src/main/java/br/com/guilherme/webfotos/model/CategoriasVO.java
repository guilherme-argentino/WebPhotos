/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.guilherme.webfotos.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Guilhe
 */
@Entity
@Table(name = "CATEGORIAS")
@NamedQueries({@NamedQuery(name = "CategoriasVO.findByCategoriaid", query = "SELECT c FROM CategoriasVO c WHERE c.categoriaid = :categoriaid"), @NamedQuery(name = "CategoriasVO.findByNmcategoria", query = "SELECT c FROM CategoriasVO c WHERE c.nmcategoria = :nmcategoria")})
public class CategoriasVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "CATEGORIAID", nullable = false)
    private Integer categoriaid;
    @Column(name = "NMCATEGORIA", nullable = false)
    private int nmcategoria;

    public CategoriasVO() {
    }

    public CategoriasVO(Integer categoriaid) {
        this.categoriaid = categoriaid;
    }

    public CategoriasVO(Integer categoriaid, int nmcategoria) {
        this.categoriaid = categoriaid;
        this.nmcategoria = nmcategoria;
    }

    public Integer getCategoriaid() {
        return categoriaid;
    }

    public void setCategoriaid(Integer categoriaid) {
        this.categoriaid = categoriaid;
    }

    public int getNmcategoria() {
        return nmcategoria;
    }

    public void setNmcategoria(int nmcategoria) {
        this.nmcategoria = nmcategoria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (categoriaid != null ? categoriaid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoriasVO)) {
            return false;
        }
        CategoriasVO other = (CategoriasVO) object;
        if ((this.categoriaid == null && other.categoriaid != null) || (this.categoriaid != null && !this.categoriaid.equals(other.categoriaid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.nom.guilherme.webfotos.dao.CategoriasVO[categoriaid=" + categoriaid + "]";
    }

}
