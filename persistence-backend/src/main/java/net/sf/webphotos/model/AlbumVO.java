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
package net.sf.webphotos.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import net.sf.webphotos.entity.HasID;

/**
 *
 * @author Guilhe
 */
@Entity
@Table(name = "ALBUNS")
@NamedQueries({
    @NamedQuery(name = "AlbumVO.findByAlbumID", query = "SELECT a FROM AlbumVO a WHERE a.albumid = :albumid"),
    @NamedQuery(name = "AlbumVO.findByNmAlbum", query = "SELECT a FROM AlbumVO a WHERE a.nmalbum = :nmalbum"),
    @NamedQuery(name = "AlbumVO.findByDtInsercao", query = "SELECT a FROM AlbumVO a WHERE a.dtInsercao = :dtInsercao"),
    @NamedQuery(name = "AlbumVO.findByCategoriaID", query = "SELECT a FROM AlbumVO a WHERE a.categoriasVO.categoriaID = :categoriaID")
})
public class AlbumVO implements Serializable, HasID<Integer> {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ALBUMID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer albumid;
    
    @Column(name = "NMALBUM", nullable = false)
    private String nmalbum;
    
    @Column(name = "DESCRICAO", nullable = true)
    private String descricao;
    
    @Column(name = "DTINSERCAO", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dtInsercao;
    
    @ManyToOne
    @JoinColumn(name = "CATEGORIAID", nullable = false)
    private CategoryVO categoriasVO;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "album")
    private Set<PhotoVO> photos;

    public CategoryVO getCategoriasVO() {
        return categoriasVO;
    }

    public void setCategoriasVO(CategoryVO categoriasVO) {
        this.categoriasVO = categoriasVO;
    }

    @Deprecated
    public AlbumVO() {
    }

    AlbumVO(String nmalbum, String descricao, Date dtInsercao, CategoryVO categoriasVO, Set<PhotoVO> photos) {
        this.nmalbum = nmalbum;
        this.descricao = descricao;
        this.dtInsercao = dtInsercao;
        this.categoriasVO = categoriasVO;
        this.photos = photos;
    }

    AlbumVO(Integer key, String nmalbum, String descricao, Date dtInsercao, CategoryVO categoriasVO, Set<PhotoVO> photos) {
        this(nmalbum, descricao, dtInsercao, categoriasVO, photos);
        this.albumid = key;
    }

    public static AlbumVOBuilder builder() {
        return new AlbumVOBuilder();
    }

    public static AlbumVOBuilder builder(Integer key) {
        return new AlbumVOBuilder(key);
    }

    /**
     *
     * @return
     */
    public Integer getAlbumid() {
        return albumid;
    }

    /**
     *
     * @param albumid
     */
    public void setAlbumid(Integer albumid) {
        this.albumid = albumid;
    }

    /**
     *
     * @return
     */
    public String getNmalbum() {
        return nmalbum;
    }

    /**
     *
     * @param nmalbum
     */
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

    /**
     *
     * @return
     */
    public Date getDtInsercao() {
        return dtInsercao;
    }

    /**
     *
     * @param dtInsercao
     */
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
        if (!(object instanceof AlbumVO)) {
            return false;
        }
        AlbumVO other = (AlbumVO) object;
        if ((this.albumid == null && other.albumid != null) || (this.albumid != null && !this.albumid.equals(other.albumid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getCanonicalName() + "[albumid=" + albumid + "]";
    }

    /**
     * @return the photos
     */
    public Set<PhotoVO> getPhotos() {
        return photos;
    }

    /**
     * @param photos the photos to add
     */
    public void addPhotos(HashSet<PhotoVO> photos) {
        this.photos.addAll(photos);
    }

    /**
     * @param photos the photo to add
     */
    public void addPhoto(PhotoVO photos) {
        this.photos.add(photos);
    }

    /**
     *
     * @param id
     * @return Photo
     */
    public PhotoVO getPhotoBy(Integer id) {
        return new ArrayList<PhotoVO>(this.photos).get(id);
    }

    /**
     * remove one photo
     *
     * @param id
     * @return Photo
     */
    public boolean removePhotoBy(Integer id) {
        return this.photos.remove(getPhotoBy(id));
    }

    /**
     *
     * @return
     */
    @Override
    public Integer getId() {
        return this.albumid;
    }
}
