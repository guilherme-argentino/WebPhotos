/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.guilherme.webfotos.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
        @NamedQuery(name = "AlbunsVO.findByCategoriaID", query = "SELECT a FROM AlbunsVO a WHERE a.categoriaVO.categoriaid = :categoriaid"),
        @NamedQuery(name = "AlbunsVO.findByCreditoID", query = "SELECT a FROM AlbunsVO a WHERE a.creditoid = :creditoid")
})
public class AlbunsVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ALBUMID", nullable = false)
    private Integer albumid;
    
    @Column(name = "NMALBUM", nullable = false)
    private String nmalbum;
    
    @Column(name = "DT_INSERCAO", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dtInsercao;
    
    @ManyToOne
    @JoinColumn(name="CATEGORIAID")
    private CategoriasVO categoriasVO;

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

}
