/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;
import conexion.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import modelo.Compra;
import modelo.CompraProducto;
import modelo.Producto;
import modelo.Usuario;
import modelo.Proveedor;
/**
 *
 * @author intel
 */
public class CompraControlador {
    static public List<Compra> obtenerCompras(){
        List<Compra> compras = new ArrayList<>();
        String sql = "SELECT Compra.*, Usuario.nombre as UsuarioNombre, Proveedor.nombre as ProveedorNombre "+
                        "FROM Compra INNER JOIN Usuario ON Compra.idUsuario = Usuario.id " + 
                        "INNER JOIN Proveedor ON Compra.idProveedor = Proveedor.id";
        
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
}