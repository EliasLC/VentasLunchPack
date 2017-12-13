/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lunchpack.persistence;

import java.util.Date;

/**
 *
 * @author eliaslc
 */
public class VentasTarjetaTurno {
     private int idVenta;
    private double monto;
    private Date fechaHora;
    
    public VentasTarjetaTurno(){
    
    }
    
    public VentasTarjetaTurno(int idVenta, double monto, Date fechaHora){
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
