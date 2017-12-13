/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lunchpack.persistence;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author eliaslc
 */
@Entity
@Table(name = "CUENTA_ALUMNO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CuentaAlumno.findAll", query = "SELECT c FROM CuentaAlumno c")
    , @NamedQuery(name = "CuentaAlumno.findByIdCuenta", query = "SELECT c FROM CuentaAlumno c WHERE c.idCuenta = :idCuenta")
    , @NamedQuery(name = "CuentaAlumno.findBySaldo", query = "SELECT c FROM CuentaAlumno c WHERE c.saldo = :saldo")})
public class CuentaAlumno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_CUENTA")
    private Integer idCuenta;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SALDO")
    private Double saldo;
    @JoinColumn(name = "ID_ALUMNO", referencedColumnName = "ID_ALUMNO")
    @ManyToOne
    private Alumnos idAlumno;
    @OneToMany(mappedBy = "idCuenta")
    private Collection<VentasTarjeta> ventasTarjetaCollection;

    public CuentaAlumno() {
    }

    public CuentaAlumno(Integer idCuenta) {
        this.idCuenta = idCuenta;
    }

    public Integer getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Integer idCuenta) {
        this.idCuenta = idCuenta;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Alumnos getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Alumnos idAlumno) {
        this.idAlumno = idAlumno;
    }

    @XmlTransient
    public Collection<VentasTarjeta> getVentasTarjetaCollection() {
        return ventasTarjetaCollection;
    }

    public void setVentasTarjetaCollection(Collection<VentasTarjeta> ventasTarjetaCollection) {
        this.ventasTarjetaCollection = ventasTarjetaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCuenta != null ? idCuenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CuentaAlumno)) {
            return false;
        }
        CuentaAlumno other = (CuentaAlumno) object;
        if ((this.idCuenta == null && other.idCuenta != null) || (this.idCuenta != null && !this.idCuenta.equals(other.idCuenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.lunchpack.persistence.CuentaAlumno[ idCuenta=" + idCuenta + " ]";
    }
    
}
