package com.lunchpack.ventas;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lunchpack.persistence.EManagerFactory;
import com.lunchpack.persistence.UsuarioLogueado;
import com.lunchpack.persistence.VentasEfectivoTurno;
import com.lunchpack.persistence.VentasTarjetaTurno;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.JOptionPane;
/**
 *
 * @author eliaslc
 */
public class CorteCaja extends Thread {
 
    private final static String path = System.clearProperty("user.home");;
    
    @Override
    public void run(){
        try {
            realizarCorteCaja();
        } catch (Exception e) {
           JOptionPane.showMessageDialog(null,"Imposible Generar Corte De Caja", "Error De Conexion", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void realizarCorteCaja() throws BadElementException, IOException {

        Document doc = new Document();
        doc.setMargins(25, 25, 1, 10);
        
        double totalefectivo, totaltarjeta;
        totalefectivo= totaltarjeta=0.0;

        Font fonts[] = {
            new Font(Font.FontFamily.HELVETICA, 30, Font.BOLD, new BaseColor(255, 255, 255)),
            new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, new BaseColor(255, 255, 255)),
            new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, new BaseColor(0, 0, 0))

        };

        try {

            PdfWriter.getInstance(doc, new FileOutputStream(path + "/Text.pdf"));

            doc.open();
            //Backgroud aka formato
            Rectangle rec = new Rectangle(600, 120);
            BaseColor b = new BaseColor(153, 153, 153);
            rec.setBackgroundColor(b);
            rec.setBottom(842);
            rec.setTop(710);
            doc.add(rec);  //END

            Paragraph p2 = new Paragraph(fecha(), fonts[1]);
            p2.setIndentationLeft(470);
            doc.add(p2);

            //Titulo
            Paragraph pa = new Paragraph("Corte De Caja", fonts[0]);
            pa.setIndentationLeft(172);
            doc.add(pa); //END

            //Titulo
            Paragraph user = new Paragraph("Usuario: Elias Lopez Cabrera", fonts[1]);
            user.setIndentationLeft(172);
            doc.add(user); //END

            Date sal = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("h:mm:ss a");
            String horasal= sdf.format(sal);
            //Titulo
            Paragraph intervalo = new Paragraph("De:"+UsuarioLogueado.horaLogueo()+ " A:"+horasal, fonts[1]);
            intervalo.setIndentationLeft(190);
            doc.add(intervalo); //END

            //Tabla de las ventas realizadas con tarjeta del turno del dia
            PdfPTable pt = new PdfPTable(3);

            PdfPCell cell = new PdfPCell(new Paragraph("Ventas Con Tarjeta", fonts[1]));
            cell.setColspan(6);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(b);
            pt.addCell(cell);

            PdfPCell tit1 = new PdfPCell(new Paragraph("ID Venta", fonts[1]));
            tit1.setColspan(1);
            tit1.setHorizontalAlignment(Element.ALIGN_CENTER);
            tit1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            pt.addCell(tit1);

            PdfPCell tit2 = new PdfPCell(new Paragraph("Total", fonts[1]));
            tit2.setColspan(1);
            tit2.setHorizontalAlignment(Element.ALIGN_CENTER);
            tit2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            pt.addCell(tit2);

            PdfPCell tit3 = new PdfPCell(new Paragraph("Hora", fonts[1]));
            tit3.setColspan(1);
            tit3.setHorizontalAlignment(Element.ALIGN_CENTER);
            tit3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            pt.addCell(tit3);
            
            try{
            EntityManager em = EManagerFactory.getEntityManagerFactory().createEntityManager();
            Query query = em.createNamedQuery("VentasTarjeta.findByFechaHora");
            query.setParameter("fechaLog", UsuarioLogueado.getHoraIngreso());
            query.setParameter("fechaSal", sal);
            Query query2 = em.createNamedQuery("VentasEfectivo.findByFechaHora");
            query2.setParameter("fechaLog", UsuarioLogueado.getHoraIngreso());
            query2.setParameter("fechaSal", sal);
            
            
            List<VentasTarjetaTurno> vt= (List<VentasTarjetaTurno>) query.getResultList();
            List<VentasEfectivoTurno> ve = (List<VentasEfectivoTurno>) query2.getResultList();
            em.close();
            
                for (VentasTarjetaTurno vtt : vt) {
                pt.addCell(String.valueOf(vtt.getIdVenta()));
                pt.addCell(String.valueOf(vtt.getMonto())); totaltarjeta+=vtt.getMonto();
                pt.addCell(sdf.format(vtt.getFechaHora()));
            }
            pt.setSpacingBefore(40);

            PdfPCell totalv = new PdfPCell(new Paragraph("Total De Ventas Con Tarjeta: "+String.valueOf(totaltarjeta), fonts[1]));
            totalv.setColspan(6);
            totalv.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalv.setBackgroundColor(b);
            pt.addCell(totalv);

            doc.add(pt); //END

            //Tabla de las ventas en efectivo
            PdfPTable efectivo = new PdfPTable(3);

            PdfPCell te = new PdfPCell(new Paragraph("Ventas En Efectivo", fonts[1]));
            te.setColspan(6);
            te.setHorizontalAlignment(Element.ALIGN_CENTER);
            te.setBackgroundColor(b);
            efectivo.addCell(te);

            efectivo.addCell(tit1);
            efectivo.addCell(tit2);
            efectivo.addCell(tit3);
            
            
            
            for (VentasEfectivoTurno vet : ve) {
                efectivo.addCell(String.valueOf(vet.getIdVenta()));
                efectivo.addCell(String.valueOf(vet.getMonto())); totalefectivo+=vet.getMonto();
                efectivo.addCell(sdf.format(vet.getFechaHora()));
            }
            efectivo.setSpacingBefore(15);

            PdfPCell totale = new PdfPCell(new Paragraph("Total De Ventas En Efectivo: "+String.valueOf(totalefectivo), fonts[1]));
            totale.setColspan(6);
            totale.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totale.setBackgroundColor(b);
            efectivo.addCell(totale);

            doc.add(efectivo); //END

            Paragraph totalturno = new Paragraph("Ventas Netas Del Turno: "+String.valueOf(totalefectivo+totaltarjeta), fonts[2]);
            totalturno.setSpacingBefore(30);
            totalturno.setAlignment(Element.ALIGN_CENTER);
            doc.add(totalturno);

            Image fin = Image.getInstance(MainApp.class.getResource("/Images/logop.png"));
            fin.setAlignment(Element.ALIGN_RIGHT);
            fin.setSpacingBefore(20);
            doc.add(fin);

            Image marca = Image.getInstance(MainApp.class.getResource("/Images/Marcapdf.png"));
            marca.setAbsolutePosition(0, 740);
            doc.add(marca);

            Image sales = Image.getInstance(MainApp.class.getResource("/Images/Salespdf.png"));
            sales.setAbsolutePosition(0, 710);
            doc.add(sales);

            doc.close();
            
            } catch(Exception e){
              
            }
        

            mandarEmail(path);
        } catch (FileNotFoundException | DocumentException ex) {
            System.out.println("neee");
        }

    }

    private String fecha() {
        Date fecha = new Date();
        String fe = "";
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy", new Locale("es"));
        fe = formato.format(fecha);
        return fe;
    }
    
    private  void mandarEmail(String path){
        
        
         final String Username= "lunchpack.no.replay@gmail.com";
        Properties props = new Properties(); 
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        Session session = Session.getInstance(props,
                new Authenticator()
                {
                     @Override
                     protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Username, "St.anger1");
                    }
                });
            
        
                try
                {
                    
                    BodyPart mime = new MimeBodyPart();
                    mime.setText("Corte de caja");
                    BodyPart adjunto = new MimeBodyPart();
                    adjunto.setDataHandler(new DataHandler(new FileDataSource(
                    path+"/Text.pdf")));
                    adjunto.setFileName("CorteDeCaja.pdf");
                    
                    MimeMultipart message = new MimeMultipart();
                    message.addBodyPart(mime);
                    message.addBodyPart(adjunto);
                    
                    EntityManager em = EManagerFactory.getEntityManagerFactory().createEntityManager();
                    em.getTransaction();
                    Query query = em.createNamedQuery("Usuarios.findCorreos");
                    query.setParameter("tipo", "ADMINISTRADOR");
                    List<String> correos = (List<String>) query.getResultList();
                    em.close();
                    
                    for (String correo : correos){
                        Message struct = new MimeMessage(session);
                        struct.setFrom(new InternetAddress(Username));
                        struct.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(correo));
                        struct.setSubject("Corte del Caja");
                        struct.setContent(message);
                        Transport.send(struct);
                    }
                       
                }
                catch (MessagingException e)
                {
                    Alertas.error("Error De Conexion", "No Se Pudo Enviar El Corte De Caja", "Revise La Conexion A Internet");
                }     
     }     
  
}