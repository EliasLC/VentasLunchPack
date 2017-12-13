package com.lunchpack.ventas;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 *
 * @author eliaslc
 */
public class Alertas{ 
    
          //Mostrar alerta de aviso
          public static void warning(String titulo, String encabezado, String texto){
       
           Alert alert= new Alert(AlertType.WARNING);
           alert.setTitle(titulo);
           alert.setHeaderText(encabezado);
           alert.setContentText(texto);
           alert.showAndWait();
           
       }
       
          //Alerta de informacion
       public static void informacionEncabezado(String titulo, String encabezado, String texto){
           Alert alert = new Alert(AlertType.INFORMATION);
           alert.setTitle(titulo);
           alert.setHeaderText(encabezado);
           alert.setContentText(texto);
           alert.showAndWait();
       }
       
       //Alerta de informacion
       public static void informacion(String titulo, String texto){
           Alert alert = new Alert(AlertType.INFORMATION);
           alert.setTitle(titulo);
           alert.setHeaderText(null);
           alert.setContentText(texto);
           alert.showAndWait();
       }
       
       //Alert de error
       public static void error (String titulo, String encabezado, String texto){
           Alert alert = new Alert(AlertType.ERROR);
           alert.setTitle(titulo);
           alert.setHeaderText(encabezado);
           alert.setContentText(texto);
           alert.showAndWait();
       }
       
       //Alerta de confirmacion
       public static void confirmacionCorteDeCaja(){
           Alert alert = new Alert(AlertType.CONFIRMATION);
           alert.setTitle("Corte De Caja");
           alert.setHeaderText("Esta A Punto De Terminar Su Turno");
           alert.setContentText("¿Desea Terminar Su Turno?");

           Optional<ButtonType> result = alert.showAndWait();
           if (result.get() == ButtonType.OK){
               new CorteCaja().start();
               Stages.setToLogIn();
           } else {
            //Acciones de cancel
            }
        }
       
    
}
