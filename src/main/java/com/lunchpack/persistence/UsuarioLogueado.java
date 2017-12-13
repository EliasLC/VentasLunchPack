package com.lunchpack.persistence;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.EntityManager;
/**
 *
 * @author eliaslc
 */
public class UsuarioLogueado {


    private static Usuarios usser;
    private static Date horaIngreso;
  
    
    private UsuarioLogueado(){ }
    
    public static void setUsuarioLog(int id){
        EntityManager em = EManagerFactory.getEntityManagerFactory().createEntityManager();
        usser = em.find(Usuarios.class, id);
        em.close();
    }
    
    public static Usuarios getUsuario(){
        return usser;    
    }
    
       public static Date getHoraIngreso() {
        return horaIngreso;
    }

    public static void setHoraIngreso(Date aHoraIngreso) {
        horaIngreso = aHoraIngreso;
    }
    
    public static String horaLogueo(){
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm:ss a");
        return sdf.format(horaIngreso);
    }
    
}