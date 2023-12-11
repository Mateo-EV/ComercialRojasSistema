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
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Producto;
import modelo.Categoria;
import modelo.Marca;
import modelo.VentaProducto;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author intel
 */
public class ProductoControlador {

    static public List<Producto> obtenerProductos() {
        List<Producto> productos = new ArrayList(); // Crea una lista para almacenar los productos recuperados
        String sql = "SELECT *, "
                + "(SELECT Categoria.nombre FROM Categoria WHERE Categoria.id = Producto.idCategoria) as categoriaNombre, "
                + "(SELECT Marca.nombre FROM Marca WHERE Marca.id = Producto.idMarca) as marcaNombre "
                + "FROM Producto ORDER BY Producto.id";

        try {
            Statement st = Conexion.db.createStatement(); // Crea una declaración SQL para ejecutar la consulta
            ResultSet rs = st.executeQuery(sql); // Ejecuta la consulta y obtiene el conjunto de resultados

            // Itera a través de los resultados y crea objetos Producto, luego los agrega a la lista de los productos
            while (rs.next()) {
                int id = rs.getInt("id");
                int stock = rs.getInt("stock");
                double precio = rs.getDouble("precio");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");

                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("idCategoria"));
                categoria.setNombre(rs.getString("categoriaNombre"));
                
                Marca marca = new Marca();
                marca.setId(rs.getInt("idMarca"));
                marca.setNombre(rs.getString("marcaNombre"));

                productos.add(new Producto(id, nombre, descripcion, marca, stock, precio, categoria));
            }

        } catch (SQLException e) {
            // Captura cualquier excepción SQL que pueda ocurrir durante la recuperación y muestra un mensaje de error
            System.out.println("Error: " + e.getMessage());
        }

        // Devuelve la lista de los productos recuperados desde la base de datos
        return productos;
    }

    static public boolean crearProducto(Producto producto) {
        boolean respuesta = false;
        try {
            PreparedStatement pst = Conexion.db.prepareStatement("INSERT INTO Producto VALUES (?, ?, ?, ?, ?, ?)");
            pst.setString(1, producto.getNombre());
            pst.setInt(2, producto.getStock());
            pst.setDouble(3, producto.getPrecio());
            pst.setInt(4, producto.getCategoria().getId());
            pst.setString(5, producto.getDescripcion());
            if(producto.getMarca() != null){
                pst.setInt(6, producto.getMarca().getId());
            } else {
                pst.setNull(6, 0);
            }
            

            if (pst.executeUpdate() > 0) {
                respuesta = true;
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return respuesta;
    }

    static public boolean actualizarProducto(Producto producto) {
        boolean respuesta = false;
        try {
            PreparedStatement pst = Conexion.db.prepareStatement("UPDATE Producto SET nombre=?, descripcion=?, idMarca=?, precio=?, idCategoria=?, stock=? WHERE id='" + producto.getId() + "'");

            pst.setString(1, producto.getNombre());
            pst.setString(2, producto.getDescripcion());
            if(producto.getMarca() == null){
                pst.setNull(3, 0);
            } else {
                pst.setInt(3, producto.getMarca().getId());
            }
            
            pst.setDouble(4, producto.getPrecio());
            pst.setInt(5, producto.getCategoria().getId());
            pst.setInt(6, producto.getStock());
            if (pst.executeUpdate() > 0) {
                respuesta = true;
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return respuesta;
    }

    static public Producto obtenerProducto(String idProducto) {
        String sql = "SELECT Producto.*, "
                + "(SELECT Categoria.nombre FROM Categoria WHERE Categoria.id = Producto.idCategoria) as categoriaNombre, "
                + "(SELECT Marca.nombre FROM Marca WHERE Marca.id = Producto.idMarca) as marcaNombre "
                + "FROM Producto WHERE id='" + idProducto + "'";
        Producto producto = null;
        try {
            Statement st = Conexion.db.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                int stock = rs.getInt("stock");
                double precio = rs.getDouble("precio");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");

                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("idCategoria"));
                categoria.setNombre(rs.getString("categoriaNombre"));
                
                Marca marca = new Marca();
                marca.setId(rs.getInt("idMarca"));
                marca.setNombre(rs.getString("marcaNombre"));

                producto = new Producto(id, nombre, descripcion, marca, stock, precio, categoria);
            }

        } catch (SQLException e) {
            // Captura cualquier excepción SQL que pueda ocurrir durante la recuperación y muestra un mensaje de error
            System.out.println("Error: " + e.getMessage());
        }

        return producto;
    }

    static public int eliminarProducto(String idProducto) {
        int respuesta = -1;
        try {
            Statement st = Conexion.db.createStatement(); // Crea una declaración SQL para ejecutar la consulta
            ResultSet rs = st.executeQuery("SELECT TOP 1 idProducto FROM Venta_Producto where idProducto='" + idProducto + "'");
            if (!rs.next()) {
                rs = st.executeQuery("SELECT TOP 1 idProducto FROM Compra_Producto where idProducto='" + idProducto + "'");
                if (!rs.next()) {
                    PreparedStatement pst = Conexion.db.prepareStatement("DELETE FROM Producto where id='" + idProducto + "'");
                    if (pst.executeUpdate() > 0) {
                        respuesta = 0;
                    }
                } else {
                    respuesta = 2;
                }
            } else {
                respuesta = 1;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return respuesta;
    }
    
    static public void generarReporte(){
        try {
            FileOutputStream archivo;
            String fecha = LocalDate.now().toString();
            String hora = LocalTime.now().format(DateTimeFormatter.ofPattern("HH-mm"));
            String hora2 = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
            File file = new File("src/pdf/productos_" + fecha + "_" + hora+ ".pdf");
            archivo = new FileOutputStream(file);
            
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();
            
            
            Paragraph p = new Paragraph();
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.DARK_GRAY);
            Font blanca = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.WHITE);
            p.add(Chunk.NEWLINE);
            p.add("Fecha: " +  fecha + "\nHora: " +hora2);
            
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
            clienteP.add("Reporte de Productos" + "\n\n");
            doc.add(clienteP);
            
            PdfPTable tablaProductos = new PdfPTable(5);
            tablaProductos.setWidthPercentage(100);
            
            int columnaWidthsClientes[] = new int[]{25, 25, 25, 25, 25};
            tablaProductos.setWidths(columnaWidthsClientes);
            tablaProductos.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell producto1 = new PdfPCell(new Phrase("Nombre: ", blanca));
            PdfPCell producto2 = new PdfPCell(new Phrase("Stock: ", blanca));
            PdfPCell producto3 = new PdfPCell(new Phrase("Precio: ", blanca));
            PdfPCell producto4 = new PdfPCell(new Phrase("Marca: ", blanca));
            PdfPCell producto5 = new PdfPCell(new Phrase("Categoria: ", blanca));
            
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
            
            tablaProductos.addCell(producto1);
            tablaProductos.addCell(producto2);
            tablaProductos.addCell(producto3);
            tablaProductos.addCell(producto4);
            tablaProductos.addCell(producto5);
            
            List<Producto> productos = obtenerProductos();
            productos.forEach(producto -> {
                tablaProductos.addCell(producto.getNombre());
                tablaProductos.addCell(String.valueOf(producto.getStock()));
                tablaProductos.addCell("S/." + producto.getPrecio());
                tablaProductos.addCell(producto.getMarca().getNombre());
                tablaProductos.addCell(producto.getCategoria().getNombre());
            });
            
            doc.add(tablaProductos);
            
            doc.close();
            archivo.close();
            
            Desktop.getDesktop().open(file);
        } catch (DocumentException | IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
