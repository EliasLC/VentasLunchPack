package com.lunchpack.persistence;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 *
 * @author eliaslc
 */

@Embeddable 
public class Tarje_ProdId implements Serializable {

    private VentasTarjeta ventatarjeta;
    private Productos producto;
    
    
    @ManyToOne(cascade= CascadeType.ALL)
    public VentasTarjeta getVentatarjeta() {
        return ventatarjeta;
    }
    

    public void setVentatarjeta(VentasTarjeta ventatarjeta) {
        this.ventatarjeta = ventatarjeta;
    }

    @ManyToOne(cascade= CascadeType.ALL)
    public Productos getProducto() {
        return producto;
    }

    public void setProducto(Productos producto) {
        this.producto = producto;
    }
   
}
