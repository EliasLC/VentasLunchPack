package com.lunchpack.controllers;
import com.lunchpack.ventas.Validaciones;
import com.lunchpack.persistence.CuentaAlumno;
import com.lunchpack.persistence.EManagerFactory;
import com.lunchpack.persistence.Efec_Paquete;
import com.lunchpack.persistence.Efec_Prod;
import com.lunchpack.persistence.Paquetes;
import com.lunchpack.persistence.Personas;
import com.lunchpack.persistence.Productos;
import com.lunchpack.persistence.TablaVentas;
import com.lunchpack.persistence.Tarje_Paquete;
import com.lunchpack.persistence.Tarje_Prod;
import com.lunchpack.persistence.UsuarioLogueado;
import com.lunchpack.persistence.VentasEfectivo;
import com.lunchpack.persistence.VentasTarjeta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.lunchpack.ventas.Alertas;
import com.lunchpack.ventas.Stages;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javafx.event.ActionEvent;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
 /**
 * FXML Controller class
 * @author eliaslc
 */
public class VentasController implements Initializable {
    
    @FXML private JFXTextField CodBarras, Cantidad ,CodCuenta;
    
    @FXML private JFXButton Agregar,Borrar,Realizar,LogOut,Modificar,Iniciativa; 
    
    @FXML private JFXRadioButton RTarjeta,REfectivo;
    
    @FXML private TableView<TablaVentas> Tabla;
    
    @FXML private TableColumn<TablaVentas, Integer> CCodBarras;
    
    @FXML private TableColumn<TablaVentas, String> CNombre;
    
    @FXML private TableColumn<TablaVentas, String> CTipo;
    
    @FXML private TableColumn<TablaVentas, Double> CPrecio;
    
    @FXML private TableColumn<TablaVentas, Integer> CCantidad;
    
    @FXML private TableColumn<TablaVentas, Double> CSubtotal;
    
    @FXML private Label fecha,Nom,Correo,Total,Saldo;   
    
    private ObservableList<TablaVentas> elementosVenta;
    
    private List<Productos> ListaProductos;
    
    private List<Paquetes> ListaPaquetes;
    
    private double total;
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
        Validaciones.setTextFieldLimit(CodCuenta, 5);
        
        fecha.setText(Validaciones.fecha());
        //Agregar los datos del usuario
        setDatosUsuario();
     
        //Agregar funcion de comprobacion del saldo
        saldoCuenta();
        
        
        //Acciones RadioButton
        REfectivo.setOnAction((ActionEvent e)->{
            CodCuenta.setEditable(false);
            CodCuenta.setText("");
            Saldo.setText("0.0");
        });
       
        RTarjeta.setOnAction((ActionEvent e)->{
             CodCuenta.setEditable(true);
        });
        
        total=0.0;
        RTarjeta.setSelected(true);
       
             
       //Inicializar el observableList de los elementos que 
       elementosVenta= FXCollections.observableArrayList();
       Tabla.setItems(elementosVenta);
       
       //Obtener todos los productos  y paquetes de ventas
       llenarListas();
       
       //Enlazar columnas con atributos
       CCodBarras.setCellValueFactory(new PropertyValueFactory<>("CodBarras"));
       CNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
       CTipo.setCellValueFactory(new PropertyValueFactory<>("Tipo"));
       CPrecio.setCellValueFactory(new PropertyValueFactory<>("Precio"));
       CCantidad.setCellValueFactory(new PropertyValueFactory<>("Cantidad"));
       CSubtotal.setCellValueFactory(new PropertyValueFactory<>("Subtotal"));
       
       //Enviar datos de la tabla a textfield para modificar
       datosModificar();
        
       Validaciones.TextFieldNumeros(CodBarras); Validaciones.TextFieldNumeros(Cantidad);
       Validaciones.TextFieldNumeros(CodCuenta); Validaciones.setTextFieldLimit(CodCuenta, 11);
       Validaciones.setTextFieldLimit(CodBarras, 11); Validaciones.setTextFieldLimit(Cantidad, 2);
       
       //Acciones del boton Agregar
       Agregar.setOnAction((ActionEvent e)->{
           agregarRegistro();
           CodBarras.setText(""); Cantidad.setText("");
       });//END
       
       //Accion del boton para borrar
       Borrar.setOnAction((ActionEvent e)->{
           borrarRegistro();
       });//END
       
       //Accion para realizar la ventaa
       Realizar.setOnAction((ActionEvent e)->{
           realizarVenta();
       });//END
       
        Modificar.setOnAction((ActionEvent e) -> {
            modificarRegistro();
        });
       
       //Accion para cerrar sesion
        LogOut.setOnAction((ActionEvent e)->{
            Alertas.confirmacionCorteDeCaja();
        });
        
        //Accion de desarrollo 
        Iniciativa.setOnAction((ActionEvent e)->{
            Stages.abrirDesarrollo();
        });
       
    }//END    
    
    //Metodo que agregar la funcionabilidad al textfied
    private void saldoCuenta(){
        CodCuenta.textProperty().addListener((Observable, viejo, nuevo)->{
            //Verificar si se tienen los minimos digitos de una cuenta
            if(CodCuenta.getText().length()>=5){
                
                try{
                    
                //Crear conexion a al a base de datos
                EntityManager em = EManagerFactory.getEntityManagerFactory().createEntityManager();
                em.getTransaction().begin();
                
                //Consultar el saldo de la cuenta
                Query saldo = em.createQuery("SELECT s.saldo FROM CuentaAlumno s WHERE s.idCuenta=:id");
                saldo.setParameter("id", Integer.valueOf(CodCuenta.getText()));
           
                //Verificar si existe la  cuenta ingresada
                if(saldo.getResultList().isEmpty()){
                    Saldo.setTextFill(Color.web("#D85A4A"));
                    Saldo.setText("Cuenta Inexistente");
                } else{
                    
                    //Asignar un color dependiendo de si puede o no pagar la compra
                    double sal = (double) saldo.getSingleResult();
                   
                    if(sal>=total)
                        Saldo.setTextFill(Color.web("#05bf65"));
                    else
                        Saldo.setTextFill(Color.web("#dd0808"));
                    
                    Saldo.setText(String.valueOf(sal));
                }
                em.close();
                } catch(Exception e){
                    Alertas.error("Error De Conexion","Imposible Conectar Con La Base De Datos", "Verifique Su Conexion A Internet");
                }
            } else{
                Saldo.setText("0.0"); Saldo.setTextFill(Color.web("#000000"));
            }
            
        });
    }//END
    
    //Metodo para agregar los elementos de la base de datos a las listas
    private void llenarListas(){
       //Se crea una conexion a la base de datos 
       EntityManager em = EManagerFactory.getEntityManagerFactory().createEntityManager();
       em.getTransaction().begin();
       //Se consultan todos los productos y paquetes
       Query productos = em.createQuery("SELECT p FROM Productos p");
       Query paquetes = em.createQuery("SELECT pa FROM Paquetes pa");
       ListaProductos=  (List<Productos>) productos.getResultList();
       ListaPaquetes = (List<Paquetes>) paquetes.getResultList();
       em.close(); 
    }//END
    
    //Metodo para ingresar un registros a la tabla
    private void agregarRegistro(){
              //Obtener datos de los textfields
           String CodB= CodBarras.getText().trim();
           String Can = Cantidad.getText().trim();
           Tabla.getSelectionModel().clearSelection();
           if(CodB.equals("") || Can.equals("")){
               Alertas.error("Error De Captura", "Campos Vacios", "Algunos Campos De Texto Se Encuentran Vacios, Verifique!");
           } else if(CodB.length()>5){
               Alertas.error("Error De Captura", "Producto o paquete Inicorrecto", "Codigo de Barras Inexistente");
           } 
           else{
              
               if(CodB.charAt(0)=='8'&&CodB.charAt(1)=='1'){
                   TablaVentas tv = null;
                   
                   for(Paquetes p :ListaPaquetes){
                       
                       if(Objects.equals(Integer.valueOf(CodB), p.getIdPaquete())){
                           
                           double can = Double.valueOf(Can)*p.getCosto();
                           total+=can;
                           tv= new TablaVentas( p.getIdPaquete(), p.getDescripcion(),
                               "Paquete", p.getCosto(), Integer.valueOf(Can), can
                           );
                           elementosVenta.add(tv);
                           Total.setText(String.valueOf(total));
                           break;
                       }
                       
                       }
                   if(tv==null){
                           Alertas.error("Error De Captura", "Error De Codigo De Barras", "Codigo De Barras Inexistente");
                   }
               } else if(CodB.charAt(0)=='8'&&CodB.charAt(1)=='0'){
                   
                    TablaVentas tv=null;
                   
                   for(Productos p :ListaProductos){
                       
                       if(Objects.equals(Integer.valueOf(CodB), p.getIdProducto())){
                           
                           double can = Double.valueOf(Can)*p.getPrecio();
                           total+=can;
                           tv= new TablaVentas( p.getIdProducto(), p.getNomAlimento(),
                               "Unitario", p.getPrecio(), Integer.valueOf(Can), can
                           );
                           elementosVenta.add(tv);
                           Total.setText(String.valueOf(total));
                           break;
                       }
                   }
                   if(tv==null){
                           Alertas.error("Error De Captura", "Error De Codigo De Barras", "Codigo De Barras Inexistente");
                   }
               } else{
                   Alertas.error("Error De Captura", "Error De Codigo De Barras", "Codigo De Barras Inexistente");
               }
               String s = CodCuenta.getText().trim();
               if (!s.equals("") && s.length()>= 5 && !Saldo.getText().equals("Cuenta Inexistente")) {

                   if (Double.valueOf(Saldo.getText()) >= total) 
                       Saldo.setTextFill(Color.web("#05bf65"));
                    else 
                       Saldo.setTextFill(Color.web("#dd0808"));
                   }
           }
            
    }//END
    
    //Metodo para asignar el nombre del usuario logueado
    private void setDatosUsuario(){
         Personas nepe = UsuarioLogueado.getUsuario().getIdPersona();
                String nom= nepe.getNombre()+" "+
                            nepe.getApePaterno()+ " "+
                            nepe.getApeMaterno();
         Nom.setText(nom);
        Correo.setText(UsuarioLogueado.getUsuario().getCorreo());
    }//END
   
    
    //Metodo borrar borrar registros en la tabla
    private void borrarRegistro(){
        if(Tabla.getSelectionModel().getSelectedItem()!=null){
           total-=Tabla.getSelectionModel().getSelectedItem().getSubtotal();
           elementosVenta.remove(Tabla.getSelectionModel().getSelectedItem());
           Total.setText(String.valueOf(total));
           Tabla.getSelectionModel().clearSelection();
           Borrar.setDisable(true); Modificar.setDisable(true);
           Agregar.setDisable(false); Realizar.setDisable(false);
        } else{
               Alertas.warning("Advertencia", "Selccion Registro", "Debe Seleccionar Algun Registro");
          }
        CodBarras.setEditable(true);
        CodBarras.setText("");
        Cantidad.setText("");
        CodBarras.requestFocus();
        String s = CodCuenta.getText().trim();
            if (!s.equals("") && s.length()>= 5 & !Saldo.getText().equals("Cuenta Inexistente")) {

                if (Double.valueOf(Saldo.getText()) >= total) 
                    Saldo.setTextFill(Color.web("#05bf65"));
                else 
                    Saldo.setTextFill(Color.web("#dd0808"));
            }
        }//END
     
      //Metodo para enviar los datos a modificar
    private void datosModificar()  {
        Tabla.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends TablaVentas> 
                observable, TablaVentas oldValue, TablaVentas newValue) -> {
            if(newValue != null){
                CodBarras.setText(String.valueOf(newValue.getCodBarras()));
                CodBarras.setEditable(false);
                Cantidad.setText(String.valueOf(newValue.getCantidad()));
                Agregar.setDisable(true);
                Realizar.setDisable(true);
                Borrar.setDisable(false);
                Modificar.setDisable(false);
            }
        });
    }//END 
    
     
    //Metodo para modificar registro
    private void modificarRegistro(){
        if(!Cantidad.getText().trim().equals("")){
            String axd = Cantidad.getText();
            total-= Tabla.getSelectionModel().getSelectedItem().getSubtotal();
            double subtotal= Tabla.getSelectionModel().getSelectedItem().getPrecio()*Double.valueOf(axd);
            total+=subtotal;
            int index = Tabla.getSelectionModel().getSelectedIndex();
            TablaVentas vt= Tabla.getSelectionModel().getSelectedItem();
            elementosVenta.set(index, vt);
            vt.setCantidad(Integer.valueOf(axd)); vt.setSubtotal(subtotal);
            Total.setText(String.valueOf(total));
            Tabla.getSelectionModel().clearSelection();
            CodBarras.requestFocus();
            Agregar.setDisable(false);  Borrar.setDisable(true);
            Modificar.setDisable(true); Realizar.setDisable(false);
        } else{
              Alertas.error("Error De Captura", "Campos Vacios", "Algunos Campos De Texto Se Encuentran Vacios, Verifique!");
        }
        Cantidad.setText("");
        CodBarras.setText("");
        CodBarras.setEditable(true);
        String s = CodCuenta.getText().trim();
        if (!s.equals("") && s.length() >= 5 && !Saldo.getText().equals("Cuenta Inexistente")) {

            if (Double.valueOf(Saldo.getText()) >= total) {
                Saldo.setTextFill(Color.web("#05bf65"));
            } else {
                Saldo.setTextFill(Color.web("#dd0808"));
            }
        }
    } //END
    
    //Metodo para limiar todo los componentes de la vista despues de la venta
    private void clean(){
        CodBarras.setText(""); CodCuenta.setText(""); Cantidad.setText(""); Saldo.setTextFill(Color.web("#000000"));
        elementosVenta.clear(); Total.setText("0.0"); Saldo.setText("0.0"); total=0.0;
    }
    
    
    //Metodo para realizar la venta
    private void realizarVenta() throws NumberFormatException{
        
        //Realizar venta tarjeta
        if(RTarjeta.isSelected()){
            if(Total.getText().trim().equals("0.0")){
                Alertas.error("Error De Captura", "No Se Han Agregado Elementos", "No Se Ha Agregado Ningun Paquete O Producto");
            } else if(CodCuenta.getText().trim().equals("") || CodCuenta.getText().length()<5){
                Alertas.error("Error De Captura", "Numero De Cuenta Incorrecto", "Nomero De Cuenta Inexistente");
            } else if(Saldo.getText().equals("Cuenta Inexistente")){
                Alertas.error("Error De Captura", "Numero De Cuenta Incorrecto", "Nomero De Cuenta Inexistente");
            } else if(Double.valueOf(Saldo.getText())<total){
                Alertas.error("Error De Cuenta", "Saldo Insuficiente Para Realizar Venta", "La Cuenta No Cuenta Con Saldo Suficiente");
            }else{
                try{
                EntityManager em = EManagerFactory.getEntityManagerFactory().createEntityManager();
                em.getTransaction().begin();
                
                CuentaAlumno ca = em.find(CuentaAlumno.class, Integer.valueOf(CodCuenta.getText()));
                   if(ca!=null){
                   
                    VentasTarjeta venta = new VentasTarjeta();
                    venta.setMonto(Double.valueOf(Total.getText()));
                    venta.setIdCuenta(ca);
                    venta.setFechaHora(new Date());
                    venta.setIdUsuario(UsuarioLogueado.getUsuario());
                     
                    //Iteracion necesaria para generar las relaciones entre Ventas-Paquetes, Venta-Productos
                    elementosVenta.forEach((tv) -> {
                        if(tv.getTipo().equals("Paquete")){
                            Paquetes pa = em.find(Paquetes.class, tv.getCodBarras());
                            Tarje_Paquete tp = new Tarje_Paquete();
                            tp.setPaquete(pa);
                            tp.setVentaTarjeta(venta);
                            tp.setCantidad(tv.getCantidad()); tp.setSubTotal(tv.getSubtotal());
                            venta.addTarje_Paquete(tp);
                           
                        } else if(tv.getTipo().equals("Unitario")){
                            Productos prod = em.find(Productos.class, tv.getCodBarras());
                            Tarje_Prod tp= new Tarje_Prod();
                            tp.setProducto(prod);
                            tp.setVentaTarjeta(venta);
                            tp.setCantidad(tv.getCantidad()); tp.setSubTotal(tv.getSubtotal());
                            venta.addTarje_Prod(tp);
                        }
                    }); //END
                    venta.setFechaHora(new Date()); 
                    em.persist(venta);
                    em.getTransaction().commit();
                    em.close();
                    clean();
                   } 
                   
                     else{
                    Alertas.error("Error De Captura", "Numero De Cuenta Incorrecto", "Nomero De Cuenta Inexistente");
                }//END
               
                } catch(Exception e ){
                  Alertas.error("Error De Conexion","Imposible Conectar Con La Base De Datos", "Verifique Su Conexion A Internet");   
                }
             
                }//END 
                
             
            
        }//END REALIZAR VENTA TARJETA 
        
        //Realizar venta en efectivo
        else if(REfectivo.isSelected()){
            if(Total.getText().trim().equals("")){
                Alertas.error("Error De Captura", "No Se Han Agregado Elementos", "No Se Ha Agregado Ningun Paquete O Producto");
            } else{
               try{
                    EntityManager em = EManagerFactory.getEntityManagerFactory().createEntityManager();
                em.getTransaction().begin();
                VentasEfectivo ve= new VentasEfectivo();
                ve.setMonto(Double.valueOf(Total.getText()));
                
                elementosVenta.forEach((tv) -> {
                    if(tv.getTipo().equals("Paquete")){
                        Paquetes pa = em.find(Paquetes.class, tv.getCodBarras());
                        Efec_Paquete ep = new Efec_Paquete();
                        ep.setPaquete(pa);
                        ep.setVentaEfectivo(ve);
                        ep.setCantidad(tv.getCantidad()); ep.setSubTotal(tv.getSubtotal());
                        ve.addEfec_Paquete(ep);
                        
                    } else if(tv.getTipo().equals("Unitario")){
                        Productos prod = em.find(Productos.class, tv.getCodBarras());
                        Efec_Prod Ep= new Efec_Prod();
                        Ep.setProducto(prod);
                        Ep.setVentaefectivo(ve);
                        Ep.setCantidad(tv.getCantidad()); Ep.setSubTotal(tv.getSubtotal());
                        ve.addEfec_Prod(Ep);
                    }
                }); //END
                ve.setIdUsuario(UsuarioLogueado.getUsuario());
                ve.setFechaHora(new Date());
                em.persist(ve);
                em.getTransaction().commit();
                em.close();
                clean();
               } catch(Exception e){
                    Alertas.error("Error De Conexion","Imposible Conectar Con La Base De Datos", "Verifique Su Conexion A Internet");
               }
            }
        }//END
        
        else{
            Alertas.error("Error De Seleccion", "", "No se a seleccionado ningun tipo de pago");
        }//END
    }//END
}