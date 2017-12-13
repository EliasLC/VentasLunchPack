package com.lunchpack.persistence;

import java.util.Date;

/**
 *
 * @author eliaslc
 */
public class VentasEfectivoTurno {

    private int idVenta;
    private double monto;
    private Date fechaHora;
    
    public VentasEfectivoTurno(){ }
    
    public VentasEfectivoTurno(int idVenta, double monto, Date fechaHora){
        this.idVenta= idVenta;
        this.monto= monto;
        this.fechaHora= fechaHora;
    }

    public int getIdVenta() {
        return idVenta;
    }


    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Date getFechaHora() {
        return fechaHora;
    }


    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }
     
}