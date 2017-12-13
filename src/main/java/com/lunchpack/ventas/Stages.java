package com.lunchpack.ventas;
import com.lunchpack.persistence.EManagerFactory;
import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
/**
 * @author eliaslc
 */
public class Stages {
    
    private static Stage stage, desarrollo;
    private static boolean salir;
    
    private Stages(){}
    
    //Metodo para crear el metodo login
    public static void createLogIn(){
          stage= new Stage();
          Parent pane = null;
          try {
              pane = FXMLLoader.load(MainApp.class.getResource("/fxml/LogIn.fxml"));
              stage.setScene(new Scene(pane));
              stage.setResizable(false);
              stage.setTitle("Bienvenido A LunchSales");
              stage.centerOnScreen();
              salir= false;
              valCerrar(stage);
              stage.show();
          } catch (IOException ex) {
              ex.printStackTrace();
          }
    }
    
    
    public static void abrirDesarrollo(){
        desarrollo = new Stage();
                  Parent pane = null;
          try {
              pane = FXMLLoader.load(MainApp.class.getResource("/fxml/Desarrollo.fxml"));
              desarrollo.setScene(new Scene(pane));
              desarrollo.setResizable(false);
              desarrollo.setTitle("Iniciativa");
              desarrollo.centerOnScreen();
              desarrollo.show();
          } catch (IOException ex) {
              ex.printStackTrace();
          }
        
    }
    
    
    public static void cerrarDesarrolo(){
        desarrollo.hide(); desarrollo = null;
    }
    

     
         public static void setToSales(){
          Parent root= null;  
          try {
           root = FXMLLoader.load(MainApp.class.getResource("/fxml/Ventas.fxml")
           );
             stage.setScene(new Scene(root));
             stage.centerOnScreen();
             stage.setTitle("Ventas");
        FadeTransition fadeIn= new FadeTransition(Duration.millis(800),root);
            fadeIn.setFromValue(0); fadeIn.setToValue(1); fadeIn.setCycleCount(1);
            fadeIn.play();
            salir = true;
        } catch (IOException ex) {
           
        }
          
          System.gc();
        }
         
         
              
         public static void setToLogIn(){
          Parent root= null;  
          try {
           root = FXMLLoader.load(MainApp.class.getResource("/fxml/LogIn.fxml")
           );
             stage.setScene(new Scene(root));
             stage.centerOnScreen();
             stage.setTitle("Bienvenido A LunchSales");
        FadeTransition fadeIn= new FadeTransition(Duration.millis(600),root);
            fadeIn.setFromValue(0); fadeIn.setToValue(1); fadeIn.setCycleCount(1);
            fadeIn.play();
            salir = false;
        } catch (IOException ex) {
           
        } 
          System.gc();
        }
    
    
        //Validad el cerrar de ventanas
     private static void valCerrar(Stage sta){
        sta.setOnCloseRequest((WindowEvent event) -> {
            
            if(salir){
                event.consume();
                Alertas.warning("Imposible cerrar aplicación","No es posiible cerrar la aplicación" , 
                        "Para cerrar la aplicación realice el corte de caja");
            } else{
                EManagerFactory.getEntityManagerFactory().close();
            }
        });
        sta=null;
     }
    
  
}