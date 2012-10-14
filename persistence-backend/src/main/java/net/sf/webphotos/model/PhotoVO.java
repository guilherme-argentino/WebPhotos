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
import javax.persistence.*;
import net.sf.webphotos.entity.HasID;
import net.sf.webphotos.entity.PhotoEntity;

/**
 *
 * @author Guilherme
 */
@Entity
@Table(name = "FOTOS")
@NamedQueries({
    @NamedQuery(name = "PhotoVO.findByFotoid", query = "SELECT f FROM PhotoVO f WHERE f.fotoid = :fotoid"),
    @NamedQuery(name = "PhotoVO.findByAlbumid", query = "SELECT f FROM PhotoVO f WHERE f.album.albumid = :albumid"),
    @NamedQuery(name = "PhotoVO.findByNmfoto", query = "SELECT f FROM PhotoVO f WHERE f.nmfoto = :nmfoto"),
    @NamedQuery(name = "PhotoVO.findByLegenda", query = "SELECT f FROM PhotoVO f WHERE f.legenda = :legenda"),
    @NamedQuery(name = "PhotoVO.findByCreditoid", query = "SELECT f FROM PhotoVO f WHERE f.creditos.creditoid = :creditoid")})
public class PhotoVO extends PhotoEntity implements Serializable, HasID<Integer> {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "FOTOID", nullable = false)
    private Integer fotoid;
    @Column(name = "NMFOTO")
    private String nmfoto;
    @Column(name = "LEGENDA", nullable = false)
    private String legenda;
    @ManyToOne
    @JoinColumn(name = "CREDITOID", nullable = false)
    private CreditsVO creditos;
    @ManyToOne
    @JoinColumn(name = "ALBUMID", nullable = false)
    private AlbumVO album;
    @Transient
    private String caminhoArquivo;
    @Transient
    private Integer largura, altura;

    /**
     * TODO: remove
     */
    public PhotoVO() {
        this.creditos = new CreditsVO();
        this.album = new AlbumVO();
        this.legenda = "";
    }

    public PhotoVO(String caminhoArquivo) {
        this();
        this.caminhoArquivo = caminhoArquivo;
    }

    public PhotoVO(Integer fotoid) {
        this();
        this.fotoid = fotoid;
    }

    public PhotoVO(Integer fotoid, int albumid, String legenda, int creditoid, Integer largura, Integer altura) {
        this();
        this.fotoid = fotoid;
        this.legenda = legenda;
        this.largura = largura;
        this.altura = altura;
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

    @Override
    public String getLegenda() {
        return legenda;
    }

    public void setLegenda(String legenda) {
        this.legenda = legenda;
    }

    @Override
    public String toString() {
        return this.getClass().getCanonicalName() + "[fotoid=" + fotoid + "]";
    }

    @Override
    public CreditsVO getCreditos() {
        return creditos;
    }

    public void setCreditos(CreditsVO creditos) {
        this.creditos = creditos;
    }

    public AlbumVO getAlbum() {
        return album;
    }

    public void setAlbum(AlbumVO album) {
        this.album = album;
    }

    /**
     * @return the caminhoArquivo
     */
    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    public Integer getAltura() {
        return altura;
    }

    public Integer getLargura() {
        return largura;
    }

    @Override
    public Integer getId() {
        return this.fotoid;
    }

    @Override
    public String getKey() {
        return this.getId().toString();
    }
}
