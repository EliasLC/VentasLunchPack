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
@Table(name ="TARJE_PAQUETE")
@AssociationOverrides({
    @AssociationOverride(name = "primaryKey.ventatarjeta",
        joinColumns = @JoinColumn(name = "ID_VENTA")),
    @AssociationOverride(name = "primaryKey.paquete",
        joinColumns = @JoinColumn(name = "ID_PAQUETE")) })
public class Tarje_Paquete implements Serializable {
    
    //composite-id-key
    private  Tarje_PaqueteId primaryKey = new Tarje_PaqueteId();

    //Campos extra
    private int cantidad;
    private double subTotal;
    
    @EmbeddedId
    public Tarje_PaqueteId getPrimaryKey(){
        return primaryKey;
    }
    
    public void setPrimaryKey(Tarje_PaqueteId primaryKey){
        this.primaryKey=primaryKey;
    }
    
    @Transient
    public VentasTarjeta getVentaTarjeta(){
        return getPrimaryKey().getVentatarjeta();
    }
    
    
    public void setVentaTarjeta(VentasTarjeta ventatarjeta){
        getPrimaryKey().setVentatarjeta(ventatarjeta);
    }
    
    @Transient
    public Paquetes getPaquete(){
        return getPrimaryKey().getPaquete();
    }
    
    public void setPaquete(Paquetes paquete){
        getPrimaryKey().setPaquete(paquete);
    }
    
    @Column(name = "CANTIDAD")
    public int getCantidad(){
        return cantidad;
    }
    
    public void setCantidad(int cantidad){
        this.cantidad=cantidad;
    }
    
    @Column(name = "SUBTOTAL")
    public double getSubTotal(){
        return subTotal;
    }
    
    public void setSubTotal(double subTotal){
        this.subTotal= subTotal;
    }
}
