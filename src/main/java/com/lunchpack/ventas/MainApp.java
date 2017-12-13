package com.lunchpack.ventas;
import com.lunchpack.persistence.Conexion;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class MainApp extends Application {

    public static Stage stage;
    
    @Override
    public void start(Stage s) throws Exception {
        stage=s; 
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Splash.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
        //Inicio del hilo del splash
        new Conexion().start();
        s=null;
        System.gc();
        
    }

    
    public static void main(String[] args) {
        launch(args);
    }

}
