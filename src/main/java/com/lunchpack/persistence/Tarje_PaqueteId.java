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
public class Tarje_PaqueteId implements Serializable {

    private VentasTarjeta ventatarjeta;
    private Paquetes paquete;
    
    /**
     * @return the ventastarjeta
     */
    
    @ManyToOne(cascade = CascadeType.ALL)
    public VentasTarjeta getVentatarjeta() {
        return ventatarjeta;
    }

    /**
     * @param ventatarjeta
     */
    public void setVentatarjeta(VentasTarjeta ventatarjeta) {
        this.ventatarjeta = ventatarjeta;
    }
 
    /**
     * @return the paquetes
     */
    @ManyToOne(cascade = CascadeType.ALL)
    public Paquetes getPaquete() {
        return paquete;
    }

    /**
     * @param paquetes the paquetes to set
     */
    public void setPaquete(Paquetes paquetes) {
        this.paquete = paquetes;
    }
    
    
}
