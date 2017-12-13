package com.lunchpack.controllers;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
/**
 * FXML Controller class
 * @author eliaslc
 */
public class DesarrolloController implements Initializable {
    
    @FXML private Label spech;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        spech.setText("Este software es amigable con el medio ambienta, ya que estar siendo ejecutado "
                + "\nen un Raspberry Pi 3 genera un consumo energetico de 480 mA y 2.5 W.\n"
                + "Un consumo energetico muy bajo en comparacion a una pc convencional\n"
                + "la cual en promedio genera un consumo de 150 W y 12.5 A");
    }    
    
}