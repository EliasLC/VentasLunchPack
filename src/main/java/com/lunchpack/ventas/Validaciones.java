package com.lunchpack.ventas;
import com.lunchpack.persistence.EManagerFactory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Validaciones {
     
           //Limitar la cantidad de caracteres de un TextField
  public static void setTextFieldLimit(TextField tf, int Limit){
     tf.lengthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
         if (newValue.intValue() > oldValue.intValue()) {
             // Check if the new character is greater than LIMIT
             if (tf.getText().length() >= Limit) {
                 // if it's 11th character then just setText to previous one
                 tf.setText(tf.getText().substring(0,Limit));
             }
         }
     });   
  }
    
          //Validar solo numeros
      public static void TextFieldNumeros(TextField tf){
       tf.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
           if (!newValue.matches("[\\d*]")) {
               tf.setText(newValue.replaceAll("[^\\d]", ""));
           }
       });
    }  
    
    //Validad el cerrar de ventanas
     private static void valCerrar(Stage sta){
        sta.setOnCloseRequest((WindowEvent event) -> {  
            EManagerFactory.getEntityManagerFactory().close();
            
        });
        sta=null;
     }
     
    public static String fecha(){
        Date fecha = new Date();
        String fe = ""; String aux="";
        SimpleDateFormat formato = new SimpleDateFormat("dd 'De' MMMM 'Del' yyyy", new Locale("es"));
        fe = formato.format(fecha);
        String v =""+fe.charAt(6);
        for(int i=0; i<fe.length(); i++){
            if(i==6){
                aux+=v.toUpperCase();
            } else{
                aux+=fe.charAt(i);
            }
        }
        return aux;
    }
}
