package com.lunchpack.persistence;
import com.lunchpack.ventas.MainApp;
import com.lunchpack.ventas.Stages;
import javafx.application.Platform;
import javax.swing.JOptionPane;
/**
 * @author eliaslc
 */
public class Conexion extends Thread{

    @Override
    public void run(){
        //Crreacion del entity Manager Factory
          try{ 
          EManagerFactory.getEntityManagerFactory();
          } catch(Exception e){
            JOptionPane.showMessageDialog(null,"Imposible Conextarse Con El Servidor, Verifique Su Conexion A Internet","Error De Conexion", JOptionPane.ERROR_MESSAGE); 
            System.exit(0);
          }
               
        //Hilo para cambiar la pantalla a  
        Platform.runLater(() -> {
            MainApp.stage.close();
            Stages.createLogIn();
             MainApp.stage=null; 
        });
        
        
        System.out.println("Done");
    }   
}