/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.guilherme.webfotos.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Guilhe
 */
@Entity
@Table(name = "FOTOS")
@NamedQueries({@NamedQuery(name = "FotosVO.findByFotoid", query = "SELECT f FROM FotosVO f WHERE f.fotoid = :fotoid"), @NamedQuery(name = "FotosVO.findByAlbumid", query = "SELECT f FROM FotosVO f WHERE f.album.albumid = :albumid"), @NamedQuery(name = "FotosVO.findByNmfoto", query = "SELECT f FROM FotosVO f WHERE f.nmfoto = :nmfoto"), @NamedQuery(name = "FotosVO.findByLegenda", query = "SELECT f FROM FotosVO f WHERE f.legenda = :legenda"), @NamedQuery(name = "FotosVO.findByCreditoid", query = "SELECT f FROM FotosVO f WHERE f.creditos.creditoid = :creditoid")})
public class FotosVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "FOTOID", nullable = false)
    private Integer fotoid;

    @Column(name = "NMFOTO")
    private String nmfoto;

    @Column(name = "LEGENDA", nullable = false)
    private String legenda;
    
    @OneToOne
    @JoinColumn(name="CREDITOID")
    private CreditosVO creditos;
    
    @ManyToOne
    @JoinColumn(name="ALBUMID")
    private AlbunsVO album;

    public FotosVO() {
    }

    public FotosVO(Integer fotoid) {
        this.fotoid = fotoid;
    }

    public FotosVO(Integer fotoid, int albumid, String legenda, int creditoid) {
        this.fotoid = fotoid;
        this.legenda = legenda;
    }

    public Integer getFotoid() {
        return fotoid;
    }

    public void setFotoid(Integer fotoid) {
        this.fotoid = fotoid;
    }

    public String getNmfoto() {
        return nmfoto;
    }

    public void setNmfoto(String nmfoto) {
        this.nmfoto = nmfoto;
    }

    public String getLegenda() {
        return legenda;
    }

    public void setLegenda(String legenda) {
        this.legenda = legenda;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fotoid != null ? fotoid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FotosVO)) {
            return false;
        }
        FotosVO other = (FotosVO) object;
        if ((this.fotoid == null && other.fotoid != null) || (this.fotoid != null && !this.fotoid.equals(other.fotoid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.nom.guilherme.webfotos.dao.FotosVO[fotoid=" + fotoid + "]";
    }

    public CreditosVO getCreditos() {
        return creditos;
    }

    public void setCreditos(CreditosVO creditos) {
        this.creditos = creditos;
    }

    public AlbunsVO getAlbum() {
        return album;
    }

    public void setAlbum(AlbunsVO album) {
        this.album = album;
    }

}
