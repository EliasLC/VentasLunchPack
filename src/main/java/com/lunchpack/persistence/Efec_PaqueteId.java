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
public class Efec_PaqueteId implements Serializable {

    private VentasEfectivo ventaefectivo;
    private Paquetes paquete;
    
    @ManyToOne(cascade = CascadeType.ALL)
    public VentasEfectivo getVentaefectivo() {
        return ventaefectivo;
    }

    public void setVentaefectivo(VentasEfectivo ventaefectivo) {
        this.ventaefectivo = ventaefectivo;
    }

   @ManyToOne(cascade = CascadeType.ALL)
    public Paquetes getPaquete() {
        return paquete;
    }

    public void setPaquete(Paquetes paquete) {
        this.paquete = paquete;
    }
   
    
}
