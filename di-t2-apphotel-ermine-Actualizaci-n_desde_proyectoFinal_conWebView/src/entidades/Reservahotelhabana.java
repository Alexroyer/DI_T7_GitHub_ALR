/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rafae
 */
@Entity
@Table(name = "RESERVAHOTELHABANA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reservahotelhabana.findAll", query = "SELECT r FROM Reservahotelhabana r")
    , @NamedQuery(name = "Reservahotelhabana.findById", query = "SELECT r FROM Reservahotelhabana r WHERE r.id = :id")
    , @NamedQuery(name = "Reservahotelhabana.findByFechaEvento", query = "SELECT r FROM Reservahotelhabana r WHERE r.fechaEvento = :fechaEvento")
    , @NamedQuery(name = "Reservahotelhabana.findByEvento", query = "SELECT r FROM Reservahotelhabana r WHERE r.evento = :evento")
    , @NamedQuery(name = "Reservahotelhabana.findByTipoCocina", query = "SELECT r FROM Reservahotelhabana r WHERE r.tipoCocina = :tipoCocina")
    , @NamedQuery(name = "Reservahotelhabana.findByNumPers", query = "SELECT r FROM Reservahotelhabana r WHERE r.numPers = :numPers")
    , @NamedQuery(name = "Reservahotelhabana.findByNecesita", query = "SELECT r FROM Reservahotelhabana r WHERE r.necesita = :necesita")
    , @NamedQuery(name = "Reservahotelhabana.findByNumHab", query = "SELECT r FROM Reservahotelhabana r WHERE r.numHab = :numHab")
    , @NamedQuery(name = "Reservahotelhabana.findByNumDias", query = "SELECT r FROM Reservahotelhabana r WHERE r.numDias = :numDias")})
public class Reservahotelhabana implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "FECHA_EVENTO")
    @Temporal(TemporalType.DATE)
    private Date fechaEvento;
    @Column(name = "EVENTO")
    private Character evento;
    @Column(name = "TIPO_COCINA")
    private String tipoCocina;
    @Column(name = "NUM_PERS")
    private String numPers;
    @Column(name = "NECESITA")
    private Boolean necesita;
    @Column(name = "NUM_HAB")
    private String numHab;
    @Column(name = "NUM_DIAS")
    private String numDias;
    @JoinColumn(name = "DNI", referencedColumnName = "DNI")
    @ManyToOne
    private Cliente dni;

    public Reservahotelhabana() {
    }

    public Reservahotelhabana(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public Character getEvento() {
        return evento;
    }

    public void setEvento(Character evento) {
        this.evento = evento;
    }

    public String getTipoCocina() {
        return tipoCocina;
    }

    public void setTipoCocina(String tipoCocina) {
        this.tipoCocina = tipoCocina;
    }

    public String getNumPers() {
        return numPers;
    }

    public void setNumPers(String numPers) {
        this.numPers = numPers;
    }

    public Boolean getNecesita() {
        return necesita;
    }

    public void setNecesita(Boolean necesita) {
        this.necesita = necesita;
    }

    public String getNumHab() {
        return numHab;
    }

    public void setNumHab(String numHab) {
        this.numHab = numHab;
    }

    public String getNumDias() {
        return numDias;
    }

    public void setNumDias(String numDias) {
        this.numDias = numDias;
    }

    public Cliente getDni() {
        return dni;
    }

    public void setDni(Cliente dni) {
        this.dni = dni;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reservahotelhabana)) {
            return false;
        }
        Reservahotelhabana other = (Reservahotelhabana) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Reservahotelhabana[ id=" + id + " ]";
    }
    
}
