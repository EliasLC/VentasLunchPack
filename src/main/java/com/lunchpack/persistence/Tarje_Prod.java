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
@Table(name ="TARJE_PROD")
@AssociationOverrides({
    @AssociationOverride(name = "primaryKey.ventatarjeta",
        joinColumns = @JoinColumn(name = "ID_VENTA")),
    @AssociationOverride(name = "primaryKey.producto",
        joinColumns = @JoinColumn(name = "ID_PRODUCTO")) })
public class Tarje_Prod implements Serializable {

    private Tarje_ProdId primaryKey = new Tarje_ProdId();;
    
    private int cantidad;
    private double subTotal;
    
    @EmbeddedId
    public Tarje_ProdId getPrimaryKey() {
        return primaryKey;
    }
    
    public void setPrimaryKey(Tarje_ProdId primaryKey) {
        this.primaryKey = primaryKey;
    }

    @Transient
    public VentasTarjeta getVentaTarjeta(){
        return primaryKey.getVentatarjeta();
    }
    
    public void setVentaTarjeta(VentasTarjeta ventatarjeta){
        primaryKey.setVentatarjeta(ventatarjeta);
    }
    
    
    @Transient
    public Productos getProducto(){
        return primaryKey.getProducto();
    }
    
    public void setProducto(Productos producto){
        primaryKey.setProducto(producto);
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
