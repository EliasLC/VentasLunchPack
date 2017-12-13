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
public class Efec_ProdId implements Serializable {
        
    private VentasEfectivo ventaefectivo;
    private Productos producto;

    @ManyToOne(cascade = CascadeType.ALL)
    public VentasEfectivo getVentaefectivo() {
        return ventaefectivo;
    }

    public void setVentaefectivo(VentasEfectivo ventaefectivo) {
        this.ventaefectivo = ventaefectivo;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Productos getProducto() {
        return producto;
    }

    public void setProducto(Productos producto) {
        this.producto = producto;
    }
  
}
