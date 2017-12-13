package com.lunchpack.persistence;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author eliaslc
 */
public class TablaVentas {
    
    
    private IntegerProperty CodBarras,Cantidad;
    
    private StringProperty Nombre,Tipo;
    
    private DoubleProperty Precio,Subtotal;
    
    
    public TablaVentas (int CodBarras,String Nombre, String Tipo, double Precio, int Cantidad,double Subtotal){
        this.CodBarras= new SimpleIntegerProperty(CodBarras); 
        this.Nombre= new SimpleStringProperty(Nombre); 
        this.Tipo= new SimpleStringProperty(Tipo); 
        this.Precio= new SimpleDoubleProperty(Precio); 
        this.Subtotal=new SimpleDoubleProperty(Subtotal);
        this.Cantidad= new SimpleIntegerProperty(Cantidad);
    }

    /**
     * @return the CodBarras
     */
    public int getCodBarras() {
        return CodBarras.get();
    }

    /**
     * @param CodBarras the CodBarras to set
     */
    public void setCodBarras(int CodBarras) {
        this.CodBarras = new SimpleIntegerProperty(CodBarras);
    }

    /**
     * @return the Cantidad
     */
    public int getCantidad() {
        return Cantidad.get();
    }

    /**
     * @param Cantidad the Cantidad to set
     */
    public void setCantidad(int Cantidad) {
        this.Cantidad = new SimpleIntegerProperty(Cantidad);
    }

    /**
     * @return the Nombre
     */
    public String getNombre() {
        return Nombre.get();
    }

    /**
     * @param Nombre the Nombre to set
     */
    public void setNombre(String Nombre) {
        this.Nombre = new SimpleStringProperty(Nombre);
    }

    /**
     * @return the Tipo
     */
    public String getTipo() {
        return Tipo.get();
    }

    /**
     * @param Tipo the Tipo to set
     */
    public void setTipo(String Tipo) {
        this.Tipo = new SimpleStringProperty(Tipo);
    }

    /**
     * @return the Precio
     */
    public double getPrecio() {
        return Precio.get();
    }

    /**
     * @param Precio the Precio to set
     */
    public void setPrecio(double Precio) {
        this.Precio = new SimpleDoubleProperty(Precio);
    }

    /**
     * @return the Subtotal
     */
    public double getSubtotal() {
        return Subtotal.get();
    }

    /**
     * @param Subtotal the Subtotal to set
     */
    public void setSubtotal(double Subtotal) {
        this.Subtotal = new SimpleDoubleProperty(Subtotal);
    }
    
 
}
