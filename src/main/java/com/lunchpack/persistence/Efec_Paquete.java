package com.lunchpack.persistence;

import java.io.Serializable;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author eliaslc
 */
@Entity
@Table(name ="EFEC_PAQUETE")
@AssociationOverrides({
    @AssociationOverride(name = "primaryKey.ventaefectivo",
        joinColumns = @JoinColumn(name = "ID_VENTA")),
    @AssociationOverride(name = "primaryKey.paquete",
        joinColumns = @JoinColumn(name = "ID_PAQUETE")) })
public class Efec_Paquete implements Serializable {

    //composite-id-key
    private  Efec_PaqueteId primaryKey = new Efec_PaqueteId();

    //Campos extra
    private int cantidad;
    private double subTotal;
    
    @EmbeddedId
    public Efec_PaqueteId getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Efec_PaqueteId primaryKey) {
        this.primaryKey = primaryKey;
    }
    
    @Transient
    public Paquetes getPaquete(){
        return primaryKey.getPaquete();
    }

    public void setPaquete(Paquetes paquete){
        getPrimaryKey().setPaquete(paquete);
    }
    
    @Transient 
    public VentasEfectivo getVentaEfectivo(){
        return getPrimaryKey().getVentaefectivo();
    }
    
    public void setVentaEfectivo(VentasEfectivo ventaefectivo){
        getPrimaryKey().setVentaefectivo(ventaefectivo);
    }

    @Column(name = "CANTIDAD")
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Column(name = "SUBTOTAL")
    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }
       
}