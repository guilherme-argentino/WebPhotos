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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import net.sf.webphotos.entity.HasID;
import net.sf.webphotos.entity.IsCredits;

/**
 *
 * @author Guilherme L A Silva
 */
@Entity
@Table(name = "CREDITOS")
@NamedQueries({
    @NamedQuery(name = "CreditsVO.findByCreditoID", query = "SELECT c FROM CreditsVO c WHERE c.creditoid = :creditoid"),
    @NamedQuery(name = "CreditsVO.findByNome", query = "SELECT c FROM CreditsVO c WHERE c.nome = :nome")})
public class CreditsVO implements Serializable, HasID<Integer>, IsCredits {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "CREDITOID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer creditoid;
    
    @Column(name = "NOME", nullable = false)
    private String nome;

    /**
     *
     */
    public CreditsVO() {
        nome = "";
    }

    /**
     *
     * @param nome
     */
    public CreditsVO(String nome) {
        this.nome = nome;
    }

    /**
     *
     * @param creditoid
     */
    public CreditsVO(Integer creditoid) {
        this();
        this.creditoid = creditoid;
    }

    /**
     *
     * @param creditoid
     * @param nome
     */
    public CreditsVO(Integer creditoid, String nome) {
        this();
        this.creditoid = creditoid;
        this.nome = nome;
    }

    /**
     *
     * @return
     */
    public Integer getCreditoid() {
        return creditoid;
    }

    /**
     *
     * @return
     */
    @Override
    public String getNome() {
        return nome;
    }

    /**
     *
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (creditoid != null ? creditoid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CreditsVO)) {
            return false;
        }
        CreditsVO other = (CreditsVO) object;
        if ((this.creditoid == null && other.creditoid != null) || (this.creditoid != null && !this.creditoid.equals(other.creditoid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getCanonicalName() + "[creditoid=" + creditoid + "]";
    }

    /**
     *
     * @return
     */
    @Override
    public Integer getId() {
        return creditoid;
    }
}
