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
@Table(name ="EFEC_PROD")
@AssociationOverrides({
    @AssociationOverride(name = "primaryKey.ventaefectivo",
        joinColumns = @JoinColumn(name = "ID_VENTA")),
    @AssociationOverride(name = "primaryKey.producto",
        joinColumns = @JoinColumn(name = "ID_PRODUCTO")) })
public class Efec_Prod implements Serializable {
        
    private  Efec_ProdId primaryKey = new Efec_ProdId(); 
   
    private int Cantidad; private double SubTotal;
    
    @EmbeddedId
    public Efec_ProdId getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Efec_ProdId primaryKey) {
        this.primaryKey = primaryKey;
    }
   
    @Transient
    public VentasEfectivo getVentaefectivo() {
        return primaryKey.getVentaefectivo();
    }

    public void setVentaefectivo(VentasEfectivo ventaefectivo) {
        primaryKey.setVentaefectivo(ventaefectivo);
    }

    @Transient
    public Productos getProducto() {
        return primaryKey.getProducto();
    }

    public void setProducto(Productos Producto) {
         primaryKey.setProducto(Producto);
    }

    @Column(name = "CANTIDAD")
    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }

    @Column(name = "SUBTOTAL") 
    public double getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(double SubTotal) {
        this.SubTotal = SubTotal;
    }
    
}