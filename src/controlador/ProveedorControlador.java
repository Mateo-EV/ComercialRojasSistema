package controlador;

import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import conexion.Conexion;
import java.sql.ResultSet;
import java.sql.Statement;
import modelo.Proveedor;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author intel
 */
public class ProveedorControlador {
    static public List<Proveedor> obtenerProveedores(){
        List<Proveedor> proveedores = new ArrayList<>();
        String sql = "SELECT * FROM Proveedor";
        
        try {
            Statement st = Conexion.db.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()){
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String direccion = rs.getString("direccion");
                String telefono = rs.getString("telefono");
                String email = rs.getString("email");
                
                proveedores.add(new Proveedor(id, nombre, direccion, telefono, email));
            }
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        return proveedores;
    }
    static public List<Proveedor> obtenerProveedores(String search){
        List<Proveedor> proveedores = new ArrayList<>();
        String sql = "SELECT * FROM Proveedor "
                + "WHERE nombre like '%"+search+"%' OR "
                + "telefono like '%"+search+"%' OR "
                + "email like '%"+search+"%' OR "
                + "direccion like '%"+search+"%'";
        
        try {
            Statement st = Conexion.db.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()){
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String direccion = rs.getString("direccion");
                String telefono = rs.getString("telefono");
                String email = rs.getString("email");
                
                proveedores.add(new Proveedor(id, nombre, direccion, telefono, email));
            }
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        return proveedores;
    }
    
    static public boolean crearProveedor(Proveedor proveedor){
        boolean respuesta = false;
        try {
            PreparedStatement pst = Conexion.db.prepareStatement("INSERT INTO Proveedor VALUES (?, ?, ?, ?)");
            pst.setString(1, proveedor.getNombre());
            pst.setString(2, proveedor.getDireccion());
            pst.setString(3, proveedor.getTelefono());
            pst.setString(4, proveedor.getEmail());
            
            if(pst.executeUpdate() > 0) respuesta = true;
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        return respuesta;
    }
    
    
    
    static public Proveedor obtenerProveedor(String idProveedor){
        String sql = "SELECT * FROM Proveedor WHERE id='" + idProveedor + "'";
        Proveedor proveedor = null;
        try {
            Statement st = Conexion.db.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String direccion = rs.getString("direccion");
                String telefono = rs.getString("telefono");
                String email = rs.getString("email");
                
                proveedor = new Proveedor(id, nombre, direccion, telefono, email);
            }
            
        } catch (SQLException e) {
            // Captura cualquier excepción SQL que pueda ocurrir durante la recuperación y muestra un mensaje de error
            System.out.println("Error: " + e.getMessage());
        }
        
        return proveedor;
    }
    static public int eliminarProveedor(String idProveedor){
        int respuesta = -1;
        try {
            
            Statement st = Conexion.db.createStatement(); // Crea una declaración SQL para ejecutar la consulta
            ResultSet rs = st.executeQuery("SELECT TOP 1 id FROM Compra where idProveedor='"+ idProveedor +"'");
            
            if(!rs.next()){
                PreparedStatement pst = Conexion.db.prepareStatement("DELETE FROM Proveedor where id='" + idProveedor +"'");
                if(pst.executeUpdate() > 0) respuesta = 0;
            } else respuesta = 1;
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        return respuesta;
    }
    
    static public boolean actualizarProveedor(Proveedor proveedor){
        boolean respuesta = false;
        try {
            PreparedStatement pst = Conexion.db.prepareStatement("UPDATE Proveedor SET nombre=?, email=?, telefono=?, direccion=? WHERE id='" + proveedor.getId() +"'");
            
            pst.setString(1, proveedor.getNombre());
            pst.setString(2, proveedor.getEmail());
            pst.setString(3, proveedor.getTelefono());
            pst.setString(4, proveedor.getDireccion());
            if(pst.executeUpdate() > 0) respuesta = true;
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        return respuesta;
    }
}
