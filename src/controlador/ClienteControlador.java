package controlador;

import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import conexion.Conexion;
import java.sql.ResultSet;
import java.sql.Statement;
import modelo.Cliente;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author intel
 */
public class ClienteControlador {
    static public List<Cliente> obtenerClientes(){
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";
        
        try {
            Statement st = Conexion.db.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()){
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String dni_ruc = rs.getString("dni_ruc");
                String email = rs.getString("email");
                String telefono = rs.getString("telefono");
                String direccion = rs.getString("direccion");
                String tipoCliente = rs.getString("tipoCliente");
                
                clientes.add(new Cliente(id, nombre, apellido, dni_ruc, email, telefono, direccion, tipoCliente));
            }
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        return clientes;
    }
    
    static public List<Cliente> obtenerClientes(String search){
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Cliente "
                + "WHERE nombre like '%"+search+"%' OR "
                + "dni_ruc like '%"+search+"%' OR "
                + "telefono like '%"+search+"%' OR "
                + "email like '%"+search+"%' OR "
                + "direccion like '%"+search+"%' OR "
                + "tipoCliente like '%"+search+"%' OR "
                + "apellido like '%"+search+"%'";
        
        try {
            Statement st = Conexion.db.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()){
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String dni_ruc = rs.getString("dni_ruc");
                String email = rs.getString("email");
                String telefono = rs.getString("telefono");
                String direccion = rs.getString("direccion");
                String tipoCliente = rs.getString("tipoCliente");
                
                clientes.add(new Cliente(id, nombre, apellido, dni_ruc, email, telefono, direccion, tipoCliente));
            }
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        return clientes;
    }
    
    static public boolean crearCliente(Cliente cliente){
        boolean respuesta = false;
        try {
            PreparedStatement pst = Conexion.db.prepareStatement("INSERT INTO Cliente VALUES (?, ?, ?, ?, ?, ?, ?)");
            pst.setString(1, cliente.getNombre());
            pst.setString(2, cliente.getApellido());
            pst.setString(3, cliente.getDni_ruc());
            pst.setString(4, cliente.getEmail());
            pst.setString(5, cliente.getTelefono());
            pst.setString(6, cliente.getTipoCliente());
            pst.setString(7, cliente.getDireccion());
            
            if(pst.executeUpdate() > 0) respuesta = true;
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        return respuesta;
    }
    
    
    
    static public Cliente obtenerCliente(String idCliente){
        String sql = "SELECT * FROM Cliente WHERE id='" + idCliente + "'";
        Cliente cliente = null;
        try {
            Statement st = Conexion.db.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String dni_ruc = rs.getString("dni_ruc");
                String email = rs.getString("email");
                String telefono = rs.getString("telefono");
                String direccion = rs.getString("direccion");
                String tipoCliente = rs.getString("tipoCliente");
                
                
                cliente = new Cliente(id, nombre, apellido, dni_ruc, email, telefono, direccion, tipoCliente);
            }
            
        } catch (SQLException e) {
            // Captura cualquier excepción SQL que pueda ocurrir durante la recuperación y muestra un mensaje de error
            System.out.println("Error: " + e.getMessage());
        }
        
        return cliente;
    }
    static public int eliminarCliente(String idCliente){
        int respuesta = -1;
        try {
            
            Statement st = Conexion.db.createStatement(); // Crea una declaración SQL para ejecutar la consulta
            ResultSet rs = st.executeQuery("SELECT TOP 1 id FROM Venta where idCliente='"+ idCliente +"'");
            
            if(!rs.next()){
                PreparedStatement pst = Conexion.db.prepareStatement("DELETE FROM Cliente where id='" + idCliente +"'");
                if(pst.executeUpdate() > 0) respuesta = 0;
            } else respuesta = 1;
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        return respuesta;
    }
    
    static public boolean actualizarCliente(Cliente cliente){
        boolean respuesta = false;
        try {
            PreparedStatement pst = Conexion.db.prepareStatement("UPDATE Cliente SET nombre=?, apellido=?, dni_ruc=?, email=?, telefono=?, tipoCliente=?, direccion=? WHERE id='" + cliente.getId() +"'");
            
            pst.setString(1, cliente.getNombre());
            pst.setString(2, cliente.getApellido());
            pst.setString(3, cliente.getDni_ruc());
            pst.setString(4, cliente.getEmail());
            pst.setString(5, cliente.getTelefono());
            pst.setString(6, cliente.getTipoCliente());
            pst.setString(7, cliente.getDireccion());
            if(pst.executeUpdate() > 0) respuesta = true;
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        return respuesta;
    }
}
