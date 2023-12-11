/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import conexion.Conexion;
import static controlador.VentaControlador.obtenerVentas;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import modelo.Compra;
import modelo.CompraProducto;
import modelo.Producto;
import modelo.Usuario;
import modelo.Proveedor;
import modelo.Venta;
/**
 *
 * @author intel
 */
public class CompraControlador {
    static public List<Compra> obtenerCompras(String fechaInicio, String fechaFin){
        List<Compra> compras = new ArrayList<>();
        String sql = "SELECT Compra.*, Usuario.nombre as UsuarioNombre, Proveedor.nombre as ProveedorNombre "+
                        "FROM Compra INNER JOIN Usuario ON Compra.idUsuario = Usuario.id " + 
                        "INNER JOIN Proveedor ON Compra.idProveedor = Proveedor.id";
        
        if(!fechaInicio.isEmpty() && !fechaFin.isEmpty()){
            LocalDate fecha = LocalDate.parse(fechaFin);
            String fecha_fin = fecha.plusDays(1).toString();
            String fechaInicioFormatted = fechaInicio.replace("-", "");
            String fechaFinFormatted = fecha_fin.replace("-", "");
            sql += " WHERE Compra.fecha BETWEEN '"+fechaInicioFormatted +"' AND '"+fechaFinFormatted+"'";
        }
        
        try {
            Statement st = Conexion.db.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()){
                int id = rs.getInt("id");
                double pago = rs.getDouble("pago");
                
                String fechaSinFormatear = rs.getString("fecha");
                int lastIndex = fechaSinFormatear.lastIndexOf(".");
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime fecha = LocalDateTime.parse(fechaSinFormatear.substring(0, lastIndex), formatter);
                
                Usuario usuario = new Usuario();
                usuario.setId(rs.getString("idUsuario"));
                usuario.setNombre(rs.getString("UsuarioNombre"));
                
                Proveedor proveedor = new Proveedor();
                proveedor.setId(rs.getInt("idProveedor"));
                proveedor.setNombre(rs.getString("ProveedorNombre"));
                
                compras.add(new Compra(id, proveedor, usuario, pago, fecha));
            }
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        return compras;
    }
   
    static public Compra obtenerCompra(String idCompra){
        String sql = "SELECT *, (SELECT nombre FROM Usuario WHERE Usuario.id = Compra.idUsuario) as UsuarioNombre FROM Compra WHERE id='" + idCompra + "'";
        Compra compra = null;
        try {
            Statement st = Conexion.db.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                int id = rs.getInt("id");
                String idUsuario = rs.getString("idUsuario");
                String nombreUsuario = rs.getString("UsuarioNombre");
                int idProveedor = rs.getInt("idProveedor");
                double pago = rs.getDouble("pago");
                
                String fechaSinFormatear = rs.getString("fecha");
                int lastIndex = fechaSinFormatear.lastIndexOf(".");
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime fecha = LocalDateTime.parse(fechaSinFormatear.substring(0, lastIndex), formatter);
                compra = new Compra(id, idProveedor, idUsuario, pago, fecha);
                compra.setUsuario(new Usuario());
                compra.getUsuario().setNombre(nombreUsuario);
                
                rs = st.executeQuery("SELECT *, (SELECT nombre FROM Producto WHERE Producto.id = idProducto) as ProductoNombre FROM Compra_Producto WHERE idCompra='" + idCompra +"'");
                while(rs.next()){
                    int idProducto = rs.getInt("idProducto");
                    int cantidad = rs.getInt("cantidad");
                    double precioUnitario = rs.getDouble("precioUnitario");
                    String productoNombre = rs.getString("ProductoNombre");
                    
                    CompraProducto compraProducto = new CompraProducto(id, idProducto, cantidad, precioUnitario);
                    compraProducto.setProducto(new Producto());
                    compraProducto.getProducto().setNombre(productoNombre);
                    compraProducto.getProducto().setId(idProducto);
                    compra.comprasProducto.add(compraProducto);
                }
                
            }
            
        } catch (SQLException e) {
            // Captura cualquier excepción SQL que pueda ocurrir durante la recuperación y muestra un mensaje de error
            System.out.println("Error: " + e.getMessage());
        }
        
        return compra;
    }
    
    static public boolean crearCompra(Compra compra){
        boolean respuesta = false;
        compra.setUsuario(Conexion.session);
        compra.calcularPago();
        
        try {
            PreparedStatement pst = Conexion.db.prepareStatement("INSERT INTO Compra VALUES (?, ?, ?, GETDATE())", Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, compra.getProveedor().getId());
            pst.setString(2, compra.getUsuario().getId());
            pst.setDouble(3, compra.getPago());
            
            if(pst.executeUpdate() > 0){
                ResultSet generateKeys = pst.getGeneratedKeys();
                if(generateKeys.next()){
                    int id = generateKeys.getInt(1);
                    for (CompraProducto compraProducto : compra.getComprasProducto()) {
                        pst = Conexion.db.prepareStatement("INSERT INTO Compra_Producto VALUES (?, ?, ?, ?)");
                        pst.setInt(1, id);
                        pst.setInt(2, compraProducto.getProducto().getId());
                        pst.setInt(3, compraProducto.getCantidad());
                        pst.setDouble(4, compraProducto.getPrecioUnitario());

                        if(pst.executeUpdate() > 0){
                            pst = Conexion.db.prepareStatement("UPDATE Producto SET stock = stock + ? WHERE id = ?");
                            pst.setInt(1, compraProducto.getCantidad());
                            pst.setInt(2, compraProducto.getProducto().getId());

                            if(pst.executeUpdate() > 0) respuesta = true;
                            else respuesta = false;
                            
                        } else{
                            respuesta = false;
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
    
    static public boolean actualizarCompra(Compra compra){
        boolean respuesta = false;
        compra.setUsuario(Conexion.session);
        compra.calcularPago();
        try {
            Statement st = Conexion.db.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Compra_Producto WHERE idCompra='"+ compra.getId() +"'");
            while(rs.next()){
                PreparedStatement pst = Conexion.db.prepareStatement("UPDATE Producto SET stock = stock - ? WHERE id = '"+ rs.getInt("idProducto") +"'");
                pst.setInt(1, rs.getInt("cantidad"));
                pst.execute();
            }
            
            PreparedStatement pst = Conexion.db.prepareStatement("DELETE FROM Compra_Producto WHERE idCompra='"+ compra.getId() +"'");
            if(pst.executeUpdate() > 0){
                pst = Conexion.db.prepareStatement("UPDATE Compra SET idProveedor=?, idUsuario=?, pago=?, fecha=GETDATE() WHERE id='" + compra.getId() +"'");
                pst.setInt(1, compra.getProveedor().getId());
                pst.setString(2, compra.getUsuario().getId());
                pst.setDouble(3, compra.getPago());
                
                if(pst.executeUpdate() > 0){
                    for (CompraProducto compraProducto : compra.getComprasProducto()) {
                        pst = Conexion.db.prepareStatement("INSERT INTO Compra_Producto VALUES (?, ?, ?, ?)");
                        pst.setInt(1, compra.getId());
                        pst.setInt(2, compraProducto.getProducto().getId());
                        pst.setInt(3, compraProducto.getCantidad());
                        pst.setDouble(4, compraProducto.getPrecioUnitario());
                        
                        if(pst.executeUpdate() > 0){
                            pst = Conexion.db.prepareStatement("UPDATE Producto SET stock = stock + ? WHERE id = ?");
                            pst.setInt(1, compraProducto.getCantidad());
                            pst.setInt(2, compraProducto.getProducto().getId());
                            
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
    
    static public boolean eliminarCompra(String idCompra){
        boolean respuesta = false;
        try {
            Statement st = Conexion.db.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Compra_Producto WHERE idCompra='"+ idCompra +"'");
            while(rs.next()){
                PreparedStatement pst = Conexion.db.prepareStatement("UPDATE Producto SET stock = stock - ? WHERE id = '"+ rs.getInt("idProducto") +"'");
                pst.setInt(1, rs.getInt("cantidad"));
                pst.execute();
            }
            
             PreparedStatement pst = Conexion.db.prepareStatement("DELETE FROM Compra_Producto WHERE idCompra='"+ idCompra +"'");
             if(pst.executeUpdate() > 0){
                 pst = Conexion.db.prepareStatement("DELETE FROM Compra WHERE id='"+ idCompra +"'");
                 if(pst.executeUpdate() > 0){
                     respuesta = true;
                 }
             }
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        return respuesta;
    }
    
    static public void generarReporte(String fechaInicio, String fechaFin){
        try {
            FileOutputStream archivo;
            File file = new File("src/pdf/compras_" + fechaInicio + "_" + fechaFin+ ".pdf");
            archivo = new FileOutputStream(file);
            
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();
            
            
            Paragraph p = new Paragraph();
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.DARK_GRAY);
            Font blanca = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.WHITE);
            p.add(Chunk.NEWLINE);
            if(fechaInicio.equals(fechaFin)) p.add("Fecha: " +  fechaInicio);
            else p.add("Desde: " +  fechaInicio + "\nHasta: " +fechaFin);
            
            
            PdfPTable encabezado = new PdfPTable(2);
            encabezado.setWidthPercentage(100);
            encabezado.getDefaultCell().setBorder(0);
            
            int columnaWidths[] = new int[]{50, 50};
            encabezado.setWidths(columnaWidths);
            encabezado.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD, BaseColor.DARK_GRAY);
            
            Phrase title = new Phrase("Comercial Rojas", titleFont);
            encabezado.addCell(title);
            encabezado.addCell(p);
            
            doc.add(encabezado);
            
            Paragraph clienteP = new Paragraph();
            clienteP.setFont(negrita);
            clienteP.add(Chunk.NEWLINE);
            clienteP.add("Reporte de Compras" + "\n\n");
            doc.add(clienteP);
            
            PdfPTable tablaCompras = new PdfPTable(5);
            tablaCompras.setWidthPercentage(100);
            
            int columnaWidthsClientes[] = new int[]{25, 25, 25, 25, 25};
            tablaCompras.setWidths(columnaWidthsClientes);
            tablaCompras.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell producto1 = new PdfPCell(new Phrase("Proveedor: ", blanca));
            PdfPCell producto2 = new PdfPCell(new Phrase("Pago: ", blanca));
            PdfPCell producto3 = new PdfPCell(new Phrase("Usuario: ", blanca));
            PdfPCell producto4 = new PdfPCell(new Phrase("Fecha: ", blanca));
            PdfPCell producto5 = new PdfPCell(new Phrase("Hora: ", blanca));
            
            producto1.setBorder(0);
            producto2.setBorder(0);
            producto3.setBorder(0);
            producto4.setBorder(0);
            producto5.setBorder(0);
            
            producto1.setBackgroundColor(BaseColor.RED);
            producto2.setBackgroundColor(BaseColor.RED);
            producto3.setBackgroundColor(BaseColor.RED);
            producto4.setBackgroundColor(BaseColor.RED);
            producto5.setBackgroundColor(BaseColor.RED);
            
            tablaCompras.addCell(producto1);
            tablaCompras.addCell(producto2);
            tablaCompras.addCell(producto3);
            tablaCompras.addCell(producto4);
            tablaCompras.addCell(producto5);
            
            List<Compra> compras = obtenerCompras(fechaInicio, fechaFin);
            compras.forEach(compra -> {
                tablaCompras.addCell(compra.getProveedor().getNombre());
                tablaCompras.addCell("S/."+compra.getPago());
                tablaCompras.addCell(compra.getUsuario().getNombre());
                tablaCompras.addCell( compra.getFecha().format(DateTimeFormatter.ISO_DATE));
                tablaCompras.addCell(compra.getFecha().format(DateTimeFormatter.ISO_LOCAL_TIME).substring(0, 5));
            });
            
            doc.add(tablaCompras);
            
            doc.close();
            archivo.close();
            
            Desktop.getDesktop().open(file);
        } catch (DocumentException | IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}