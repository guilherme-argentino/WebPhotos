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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Guilherme L A Silva
 */
@Entity
@Table(name = "CREDITOS")
@NamedQueries({@NamedQuery(name = "CreditosVO.findByCreditoID", query = "SELECT c FROM CreditosVO c WHERE c.creditoid = :creditoid"), @NamedQuery(name = "CreditosVO.findByNome", query = "SELECT c FROM CreditosVO c WHERE c.nome = :nome")})
public class CreditosVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "CREDITOID", nullable = false)
    private Integer creditoid;
    @Column(name = "NOME", nullable = false)
    private String nome;

    public CreditosVO() {
    }

    public CreditosVO(Integer creditoid) {
        this.creditoid = creditoid;
    }

    public CreditosVO(Integer creditoid, String nome) {
        this.creditoid = creditoid;
        this.nome = nome;
    }

    public Integer getCreditoid() {
        return creditoid;
    }

    public void setCreditoid(Integer creditoid) {
        this.creditoid = creditoid;
    }

    public String getNome() {
        return nome;
    }

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
        if (!(object instanceof CreditosVO)) {
            return false;
        }
        CreditosVO other = (CreditosVO) object;
        if ((this.creditoid == null && other.creditoid != null) || (this.creditoid != null && !this.creditoid.equals(other.creditoid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.nom.guilherme.webfotos.CreditosVO[creditoid=" + creditoid + "]";
    }

}
