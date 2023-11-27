/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;
import conexion.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import modelo.Compra;
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
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                LocalDateTime fecha = LocalDateTime.parse(rs.getString("fecha"), formatter);
                
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
    
}
