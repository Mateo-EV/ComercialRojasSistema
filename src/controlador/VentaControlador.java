/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.util.List;
import conexion.Conexion;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import modelo.Venta;
import modelo.Usuario;
import modelo.Cliente;
import modelo.Producto;
import modelo.VentaProducto;

/**
 *
 * @author intel
 */
public class VentaControlador {
    static public List<Venta> obtenerVentas(){
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT Venta.*, Usuario.nombre as UsuarioNombre, Cliente.nombre as ClienteNombre "+
                        "FROM Venta INNER JOIN Usuario ON Venta.idUsuario = Usuario.id " + 
                        "INNER JOIN Cliente ON Venta.idCliente = Cliente.id";
        
        try {
            Statement st = Conexion.db.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()){
                int id = rs.getInt("id");
                double ganancia = rs.getDouble("ganancia");
                
                String fechaSinFormatear = rs.getString("fecha");
                int lastIndex = fechaSinFormatear.lastIndexOf(".");
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime fecha = LocalDateTime.parse(fechaSinFormatear.substring(0, lastIndex), formatter);
                
                Usuario usuario = new Usuario();
                usuario.setId(rs.getString("idUsuario"));
                usuario.setNombre(rs.getString("UsuarioNombre"));
                
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("idCliente"));
                cliente.setNombre(rs.getString("ClienteNombre"));
                
                ventas.add(new Venta(id, cliente, usuario, ganancia, fecha));
            }
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        return ventas;
    }
    
    static public int crearVenta(Venta venta){
        int respuesta = -1;
        venta.setUsuario(Conexion.session);
        venta.calcularGanancia();
        
        try {
            PreparedStatement pst = Conexion.db.prepareStatement("INSERT INTO Venta VALUES (?, ?, ?, GETDATE())", Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, venta.getCliente().getId());
            pst.setString(2, venta.getUsuario().getId());
            pst.setDouble(3, venta.getGanancia());
            
            if(pst.executeUpdate() > 0){
                ResultSet generateKeys = pst.getGeneratedKeys();
                if(generateKeys.next()){
                    int id = generateKeys.getInt(1);
                    for (VentaProducto ventaProducto : venta.getVentasProducto()) {
                        pst = Conexion.db.prepareStatement("INSERT INTO Venta_Producto VALUES (?, ?, ?, ?, ?)");
                        pst.setInt(1, id);
                        pst.setInt(2, ventaProducto.getProducto().getId());
                        pst.setInt(3, ventaProducto.getCantidad());
                        pst.setDouble(4, ventaProducto.getPrecioUnitario());
                        pst.setDouble(5, ventaProducto.getTotal());

                        if(pst.executeUpdate() > 0){
                            pst = Conexion.db.prepareStatement("UPDATE Producto SET stock = stock - ? WHERE id = ?");
                            pst.setInt(1, ventaProducto.getCantidad());
                            pst.setInt(2, ventaProducto.getProducto().getId());

                            if(pst.executeUpdate() > 0) respuesta = id;
                            else respuesta = -1;
                            
                        } else{
                            respuesta = -1;
                            break;
                        }
                    }
                } 
            };
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        return respuesta;
    }
    
    static public boolean actualizarVenta(Venta venta){
        boolean respuesta = false;
        venta.setUsuario(Conexion.session);
        venta.calcularGanancia();
        try {
            Statement st = Conexion.db.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Venta_Producto WHERE idVenta='"+ venta.getId() +"'");
            while(rs.next()){
                PreparedStatement pst = Conexion.db.prepareStatement("UPDATE Producto SET stock = stock + ? WHERE id = '"+ rs.getInt("idProducto") +"'");
                pst.setInt(1, rs.getInt("cantidad"));
                pst.execute();
            }
            
            PreparedStatement pst = Conexion.db.prepareStatement("DELETE FROM Venta_Producto WHERE idVenta='"+ venta.getId() +"'");
            if(pst.executeUpdate() > 0){
                pst = Conexion.db.prepareStatement("UPDATE Venta SET idCliente=?, idUsuario=?, ganancia=?, fecha=GETDATE() WHERE id='" + venta.getId() +"'");
                pst.setInt(1, venta.getCliente().getId());
                pst.setString(2, venta.getUsuario().getId());
                pst.setDouble(3, venta.getGanancia());
                
                if(pst.executeUpdate() > 0){
                    for (VentaProducto ventaProducto : venta.getVentasProducto()) {
                        pst = Conexion.db.prepareStatement("INSERT INTO Venta_Producto VALUES (?, ?, ?, ?, ?)");
                        pst.setInt(1, venta.getId());
                        pst.setInt(2, ventaProducto.getProducto().getId());
                        pst.setInt(3, ventaProducto.getCantidad());
                        pst.setDouble(4, ventaProducto.getPrecioUnitario());
                        pst.setDouble(5, ventaProducto.getTotal());
                        
                        if(pst.executeUpdate() > 0){
                            pst = Conexion.db.prepareStatement("UPDATE Producto SET stock = stock - ? WHERE id = ?");
                            pst.setInt(1, ventaProducto.getCantidad());
                            pst.setInt(2, ventaProducto.getProducto().getId());
                            
                            if(pst.executeUpdate() > 0) respuesta = true;
                        }
                    }
                }
            };
            
        } catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        return respuesta;
    }
    
    static public boolean eliminarVenta(String idVenta){
        boolean respuesta = false;
        try {
            Statement st = Conexion.db.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Venta_Producto WHERE idVenta='"+ idVenta +"'");
            while(rs.next()){
                PreparedStatement pst = Conexion.db.prepareStatement("UPDATE Producto SET stock = stock + ? WHERE id = '"+ rs.getInt("idProducto") +"'");
                pst.setInt(1, rs.getInt("cantidad"));
                pst.execute();
            }
            
             PreparedStatement pst = Conexion.db.prepareStatement("DELETE FROM Venta_Producto WHERE idVenta='"+ idVenta +"'");
             if(pst.executeUpdate() > 0){
                 pst = Conexion.db.prepareStatement("DELETE FROM Venta WHERE id='"+ idVenta +"'");
                 if(pst.executeUpdate() > 0){
                     respuesta = true;
                 }
             }
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        return respuesta;
    }
   
    static public Venta obtenerVenta(String idVenta){
        String sql = "SELECT *, (SELECT nombre FROM Usuario WHERE Usuario.id = Venta.idUsuario) as UsuarioNombre FROM Venta WHERE id='" + idVenta + "'";
        Venta venta = null;
        try {
            Statement st = Conexion.db.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                int id = rs.getInt("id");
                String idUsuario = rs.getString("idUsuario");
                String nombreUsuario = rs.getString("UsuarioNombre");
                int idCliente = rs.getInt("idCliente");
                double ganancia = rs.getDouble("ganancia");
                
                String fechaSinFormatear = rs.getString("fecha");
                int lastIndex = fechaSinFormatear.lastIndexOf(".");
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime fecha = LocalDateTime.parse(fechaSinFormatear.substring(0, lastIndex), formatter);
                venta = new Venta(id, idCliente, idUsuario, ganancia, fecha);
                venta.setUsuario(new Usuario());
                venta.getUsuario().setNombre(nombreUsuario);
                rs = st.executeQuery("SELECT *, (SELECT nombre FROM Producto WHERE Producto.id = idProducto) as ProductoNombre FROM Venta_Producto WHERE idVenta='" + idVenta +"'");
                while(rs.next()){
                    int idProducto = rs.getInt("idProducto");
                    int cantidad = rs.getInt("cantidad");
                    double precioUnitario = rs.getDouble("precioUnitario");
                    double total = rs.getDouble("total");
                    String productoNombre = rs.getString("ProductoNombre");
                    
                    VentaProducto ventaProducto = new VentaProducto(id, idProducto, cantidad, precioUnitario, total);
                    ventaProducto.setProducto(new Producto());
                    ventaProducto.getProducto().setNombre(productoNombre);
                    ventaProducto.getProducto().setId(idProducto);
                    venta.ventasProducto.add(ventaProducto);
                }
                
            }
            
        } catch (SQLException e) {
            // Captura cualquier excepción SQL que pueda ocurrir durante la recuperación y muestra un mensaje de error
            System.out.println("Error: " + e.getMessage());
        }
        
        return venta;
    }
    
    static public void generarFactura(String idVenta){
        try{
            Venta venta = obtenerVenta(idVenta);
            Cliente cliente = ClienteControlador.obtenerCliente(String.valueOf(venta.getIdCliente()));
            venta.setCliente(cliente);
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime date = LocalDateTime.now();
            
            String fecha = date.format(formatter);
            String nombreArchivo = "Venta_" + venta.getCliente().getNombre() + "_" + fecha + ".pdf";
            
            FileOutputStream archivo;
            File file = new File("src/pdf/" + nombreArchivo);
            archivo = new FileOutputStream(file);
            
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();
            
            Paragraph p = new Paragraph();
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.DARK_GRAY);
            Font blanca = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.WHITE);
            p.add(Chunk.NEWLINE);
            p.add("Factura: " + venta.getId() + "\nFecha: " + fecha + "\n");
            
            PdfPTable encabezado = new PdfPTable(4);
            encabezado.setWidthPercentage(100);
            encabezado.getDefaultCell().setBorder(0);
            
            int columnaWidths[] = new int[]{70, 10, 60, 40};
            encabezado.setWidths(columnaWidths);
            encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);
            
            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD, BaseColor.DARK_GRAY);
            
            Phrase title = new Phrase("Comercial Rojas", titleFont);
            encabezado.addCell(title);

            String ruc = "98765432101";
            String nombre = "Comercial Rojas";
            String telefono = "0987654321";
            String direccion = "Ica";
            String razon = "Tienda de Abarrotes al Servicio de la Comunidad";
            
            encabezado.addCell("");
            encabezado.addCell("RUC: " + ruc +
                    "\nNombre: " + nombre +
                    "\nTeléfono: " + telefono +
                    "\nDirección: " + direccion +
                    "\nRazón Social: " + razon);
            encabezado.addCell(p);
            
            doc.add(encabezado);
            
            // Cuerpo
            Paragraph clienteP = new Paragraph();
            clienteP.add(Chunk.NEWLINE);
            clienteP.add("Datos del cliente" + "\n\n");
            doc.add(clienteP);
            
            PdfPTable tablaCliente = new PdfPTable(4);
            tablaCliente.setWidthPercentage(100);
            tablaCliente.getDefaultCell().setBorder(0);
            
            int columnaWidthsClientes[] = new int[]{25, 45, 30, 40};
            tablaCliente.setWidths(columnaWidthsClientes);
            tablaCliente.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell clienteDNIRUC = new PdfPCell(new Phrase(venta.getCliente().getTipoCliente().equals("Persona") ? "DNI: " : "RUC: ", negrita));
            PdfPCell clienteNombre = new PdfPCell(new Phrase("Nombre: ", negrita));
            PdfPCell clienteTelefono = new PdfPCell(new Phrase("Teléfono: ", negrita));
            PdfPCell clienteDireccion = new PdfPCell(new Phrase("Dirección: ", negrita));
            
            clienteDNIRUC.setBorder(0);
            clienteNombre.setBorder(0);
            clienteTelefono.setBorder(0);
            clienteDireccion.setBorder(0);
            
            tablaCliente.addCell(clienteDNIRUC);
            tablaCliente.addCell(clienteNombre);
            tablaCliente.addCell(clienteTelefono);
            tablaCliente.addCell(clienteDireccion);
            tablaCliente.addCell(venta.getCliente().getDni_ruc());
            tablaCliente.addCell(venta.getCliente().getNombre());
            tablaCliente.addCell(venta.getCliente().getTelefono());
            tablaCliente.addCell(venta.getCliente().getDireccion());
            
            doc.add(tablaCliente);
            
            // ESPACIO EN BLANCO
            Paragraph espacio = new Paragraph();
            espacio.add(Chunk.NEWLINE);
            espacio.add("");
            espacio.setAlignment(Element.ALIGN_CENTER);
            doc.add(espacio);
            
            // AGREGAR PRODUCTOS
            PdfPTable tablaProducto = new PdfPTable(5);
            tablaProducto.setWidthPercentage(100);
            tablaProducto.getDefaultCell().setBorder(0);
            
            int ColumnasProductos[] = new int[]{20, 15, 15, 20, 20};
            tablaProducto.setWidths(ColumnasProductos);
            tablaProducto.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell producto1 = new PdfPCell(new Phrase("Nombre", blanca));
            PdfPCell producto2 = new PdfPCell(new Phrase("Cantidad", blanca));
            PdfPCell producto3 = new PdfPCell(new Phrase("Precio Unitario", blanca));
            PdfPCell producto4 = new PdfPCell(new Phrase("Subtotal", blanca));
            PdfPCell producto5 = new PdfPCell(new Phrase("Total", blanca));
            
            producto1.setBorder(0);
            producto2.setBorder(0);
            producto3.setBorder(0);
            producto4.setBorder(0);
            producto5.setBorder(0);
            
            producto1.setPadding(12);
            producto2.setPadding(12);
            producto3.setPadding(12);
            producto4.setPadding(12);
            producto5.setPadding(12);
            
            producto1.setBackgroundColor(BaseColor.RED);
            producto2.setBackgroundColor(BaseColor.RED);
            producto3.setBackgroundColor(BaseColor.RED);
            producto4.setBackgroundColor(BaseColor.RED);
            producto5.setBackgroundColor(BaseColor.RED);
            
            tablaProducto.addCell(producto1);
            tablaProducto.addCell(producto2);
            tablaProducto.addCell(producto3);
            tablaProducto.addCell(producto4);
            tablaProducto.addCell(producto5);
            
            double totalVenta = 0;
            
            for(VentaProducto ventaProducto : venta.ventasProducto){
                String nombreProducto = ventaProducto.getProducto().getNombre();
                String cantidad = String.valueOf(ventaProducto.getCantidad());
                String precioUnitario = String.valueOf(ventaProducto.getPrecioUnitario());
                String subTotal = String.valueOf(ventaProducto.getSubTotal());
                totalVenta += ventaProducto.getTotal();
                String total = String.valueOf(ventaProducto.getTotal());
                
                tablaProducto.addCell(nombreProducto);
                tablaProducto.addCell(cantidad);
                tablaProducto.addCell(precioUnitario);
                tablaProducto.addCell(subTotal);
                tablaProducto.addCell(total);
            }
            
            totalVenta = (double) Math.round(totalVenta * 100) / 100;
            
            doc.add(tablaProducto);
            
            //Total a pagar
            Paragraph info = new Paragraph();
            info.add(Chunk.NEWLINE);
            info.add("Total a pagar: " + totalVenta);
            info.setAlignment(Element.ALIGN_RIGHT);
            doc.add(info);
            
            Paragraph mensaje = new Paragraph();
            mensaje.add(Chunk.NEWLINE);
            mensaje.add("¡Gracias por su compra!\n");
            mensaje.add("____________________");
            mensaje.setAlignment(Element.ALIGN_CENTER);
            doc.add(mensaje);
            
            doc.close();
            archivo.close();
            
            Desktop.getDesktop().open(file);
            
        } catch(DocumentException | IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
