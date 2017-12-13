package com.lunchpack.persistence;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author eliaslc
 */

//Clase singleton para crear la conexion constante con la base datos
public class EManagerFactory {
    
    private static EntityManagerFactory emf;
    
    private EManagerFactory(){ }
    
    public static EntityManagerFactory getEntityManagerFactory(){
        
        if(emf==null){
            emf= Persistence.createEntityManagerFactory("Ventas");
        }
        
        return emf;
    }
}
