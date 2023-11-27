/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.util.List;
import conexion.Conexion;
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
    
    static public boolean crearVenta(Venta venta){
        boolean respuesta = false;
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
                            pst = Conexion.db.prepareStatement("UPDATE Producto SET Stock = Stock - ? WHERE id = ?");
                            pst.setInt(1, ventaProducto.getCantidad());
                            pst.setInt(2, ventaProducto.getProducto().getId());

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
    
}