/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.guilherme.webphotos.model;

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
