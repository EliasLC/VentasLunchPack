package com.lunchpack.controllers;
import com.lunchpack.ventas.Validaciones;
import com.lunchpack.persistence.EManagerFactory;
import com.lunchpack.persistence.UsuarioLogueado;
import com.lunchpack.persistence.Usuarios;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.lunchpack.ventas.Alertas;
import com.lunchpack.ventas.Stages;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javax.persistence.EntityManager;
/**
 * FXML Controller class
 *
 * @author eliaslc
 * abdias
 */
public class LogInController implements Initializable {   
  
    @FXML private JFXTextField User;
    
    @FXML private JFXPasswordField Pass;

    @FXML private JFXButton In;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Validaciones.TextFieldNumeros(User); Validaciones.setTextFieldLimit(User, 11);
        Validaciones.setTextFieldLimit(Pass, 30);
       
        In.setOnAction((ActionEvent e)->{
            if(User.getText().trim().equals("")||Pass.getText().trim().equals("")){
                Alertas.error("Error De Captura", "Campos Vacios", "Algunos Campos De Texto Se Encuentran Vacios, Verifique!");
            } else{
                verify(Integer.valueOf(User.getText()),Pass.getText());
            }
        });
           
    }    
    
    //Metodo para verificar el ingreso del usuario ELias
    private void verify(int user, String pass){
        
        //Se crea una conexion coon la base de datos
        EntityManager manager = EManagerFactory.getEntityManagerFactory().createEntityManager();
        
        //Se busca al usuario ingresado en la base de datos
        Usuarios u = manager.find(Usuarios.class, user);
        manager.close();
        
        //se compara si el usuario ingresado existe o si tiene un usuario adecuado para ingresar
        if(u==null||u.getTipoUsuario().equals("RECEPCIONISTA")){
            Alertas.error("Error De Ingreso", "Usuario Inexistente", "Usuario Ingresado Inexistente");
        } else if(u.getContrasena().equals(pass)){
            
            //Se guardan los datos del usuario logueado
            UsuarioLogueado.setUsuarioLog(user);
            UsuarioLogueado.setHoraIngreso(new Date());
            Stages.setToSales();
            
        } else {
            Alertas.error("Error De Ingreso", "Contraseña Incorrecta", "Contraseña Ingresada Incorrecta");
        }
        } 
    }