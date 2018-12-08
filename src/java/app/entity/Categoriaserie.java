/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ofviak
 */
@Entity
@Table(name = "categoriaserie")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Categoriaserie.findAll", query = "SELECT c FROM Categoriaserie c")
    , @NamedQuery(name = "Categoriaserie.findByIdCategoriaSerie", query = "SELECT c FROM Categoriaserie c WHERE c.idCategoriaSerie = :idCategoriaSerie")})
public class Categoriaserie implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCategoriaSerie")
    private Integer idCategoriaSerie;
    @JoinColumn(name = "Categoria_idCategoria", referencedColumnName = "idCategoria")
    @ManyToOne(optional = false)
    private Categoria categoriaidCategoria;
    @JoinColumn(name = "Serie_idSerie", referencedColumnName = "idSerie")
    @ManyToOne(optional = false)
    private Serie serieidSerie;

    public Categoriaserie() {
    }

    public Categoriaserie(Integer idCategoriaSerie) {
        this.idCategoriaSerie = idCategoriaSerie;
    }

    public Integer getIdCategoriaSerie() {
        return idCategoriaSerie;
    }

    public void setIdCategoriaSerie(Integer idCategoriaSerie) {
        this.idCategoriaSerie = idCategoriaSerie;
    }

    public Categoria getCategoriaidCategoria() {
        return categoriaidCategoria;
    }

    public void setCategoriaidCategoria(Categoria categoriaidCategoria) {
        this.categoriaidCategoria = categoriaidCategoria;
    }

    public Serie getSerieidSerie() {
        return serieidSerie;
    }

    public void setSerieidSerie(Serie serieidSerie) {
        this.serieidSerie = serieidSerie;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCategoriaSerie != null ? idCategoriaSerie.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categoriaserie)) {
            return false;
        }
        Categoriaserie other = (Categoriaserie) object;
        if ((this.idCategoriaSerie == null && other.idCategoriaSerie != null) || (this.idCategoriaSerie != null && !this.idCategoriaSerie.equals(other.idCategoriaSerie))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Categoriaserie[ idCategoriaSerie=" + idCategoriaSerie + " ]";
    }
    
}
