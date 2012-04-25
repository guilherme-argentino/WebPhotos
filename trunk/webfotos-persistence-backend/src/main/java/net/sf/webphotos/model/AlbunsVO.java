/**
 * Copyright 2008 WebPhotos
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package net.sf.webphotos.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Guilhe
 */
@Entity
@Table(name = "ALBUNS")
@NamedQueries({
    @NamedQuery(name = "AlbunsVO.findByAlbumID", query = "SELECT a FROM AlbunsVO a WHERE a.albumid = :albumid"),
    @NamedQuery(name = "AlbunsVO.findByNmAlbum", query = "SELECT a FROM AlbunsVO a WHERE a.nmalbum = :nmalbum"),
    @NamedQuery(name = "AlbunsVO.findByDtInsercao", query = "SELECT a FROM AlbunsVO a WHERE a.dtInsercao = :dtInsercao"),
    @NamedQuery(name = "AlbunsVO.findByCategoriaID", query = "SELECT a FROM AlbunsVO a WHERE a.categoriasVO.categoriaID = :categoriaID")
})
public class AlbunsVO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ALBUMID", nullable = false)
    private Integer albumid;
    
    @Column(name = "NMALBUM", nullable = false)
    private String nmalbum;
    
    @Column(name = "DESCRICAO", nullable = true)
    private String descricao;
    
    @Column(name = "DTINSERCAO", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dtInsercao;
    
    @ManyToOne
    @JoinColumn(name = "CATEGORIAID", nullable=false)
    private CategoriasVO categoriasVO;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "album")
    private Set<FotosVO> photos;

    public CategoriasVO getCategoriasVO() {
        return categoriasVO;
    }

    public void setCategoriasVO(CategoriasVO categoriasVO) {
        this.categoriasVO = categoriasVO;
    }

    public AlbunsVO() {
    }

    public AlbunsVO(Integer albumid) {
        this.albumid = albumid;
    }

    public AlbunsVO(Integer albumid, String nmalbum, Date dtInsercao, int categoriaid) {
        this.albumid = albumid;
        this.nmalbum = nmalbum;
        this.dtInsercao = dtInsercao;
        //this.categoriaid = categoriaid;
    }

    public Integer getAlbumid() {
        return albumid;
    }

    public void setAlbumid(Integer albumid) {
        this.albumid = albumid;
    }

    public String getNmalbum() {
        return nmalbum;
    }

    public void setNmalbum(String nmalbum) {
        this.nmalbum = nmalbum;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDtInsercao() {
        return dtInsercao;
    }

    public void setDtInsercao(Date dtInsercao) {
        this.dtInsercao = dtInsercao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (albumid != null ? albumid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AlbunsVO)) {
            return false;
        }
        AlbunsVO other = (AlbunsVO) object;
        if ((this.albumid == null && other.albumid != null) || (this.albumid != null && !this.albumid.equals(other.albumid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.nom.guilherme.webfotos.dao.AlbunsVO[albumid=" + albumid + "]";
    }

    /**
     * @return the photos
     */
    public Set<FotosVO> getPhotos() {
        return photos;
    }

    /**
     * @param photos the photos to set
     */
    public void setPhotos(HashSet<FotosVO> photos) {
        this.photos = photos;
    }
}
