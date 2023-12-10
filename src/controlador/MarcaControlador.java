/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import conexion.Conexion;
import java.util.List;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.Categoria;
import modelo.Marca;

/**
 *
 * @author intel
 */
public class MarcaControlador {
    // Método estático para crear una nueva categoría en la base de datos
    static public boolean crearMarca(Marca marca){
        boolean respuesta = false;
        try {
            // Prepara una declaración SQL para insertar una nueva fila en la tabla "Categoria"
            PreparedStatement pst = Conexion.db.prepareStatement("INSERT INTO Marca VALUES (?)");
            // Establece los valores de los parámetros en la declaración SQL
            pst.setString(1, marca.getNombre());
            
            // Ejecuta la declaración SQL y verifica si al menos una fila fue afectada en la base de datos
            if(pst.executeUpdate() > 0) respuesta = true;
            
        } catch (SQLException e) {
            // Captura cualquier excepción SQL que pueda ocurrir durante la inserción y muestra un mensaje de error
            System.out.println("Error: " + e.getMessage());
        }
        // Devuelve true si la inserción fue exitosa, de lo contrario, devuelve false
        return respuesta;
    }
    
    static public boolean actualizarMarca(Marca marca){
        boolean respuesta = false;
        try {
            PreparedStatement pst = Conexion.db.prepareStatement("UPDATE Marca SET nombre=? WHERE id='" + marca.getId() +"'");
            
            pst.setString(1, marca.getNombre());
            if(pst.executeUpdate() > 0) respuesta = true;
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        return respuesta;
    }
    
    static public int eliminarMarca(int idMarca){
        int respuesta = -1;
        try {
            
            Statement st = Conexion.db.createStatement(); // Crea una declaración SQL para ejecutar la consulta
            ResultSet rs = st.executeQuery("SELECT TOP 1 id FROM Producto where idMarca='"+ idMarca +"'");
            
            if(!rs.next()){
                PreparedStatement pst = Conexion.db.prepareStatement("DELETE FROM Marca where id='" + idMarca +"'");
                if(pst.executeUpdate() > 0) respuesta = 0;
            } else respuesta = 1;
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        return respuesta;
    }
    
    // Método estático para obtener una lista de todas las categorías desde la base de datos
    static public List<Marca> obtenerMarcas(){
        List<Marca> marcas = new ArrayList(); // Crea una lista para almacenar las categorías recuperadas
        String sql = "SELECT id, nombre FROM Marca"; // Consulta SQL para seleccionar todas las categorías
        
        try {
            Statement st = Conexion.db.createStatement(); // Crea una declaración SQL para ejecutar la consulta
            ResultSet rs = st.executeQuery(sql); // Ejecuta la consulta y obtiene el conjunto de resultados
            
            // Itera a través de los resultados y crea objetos Categoria, luego los agrega a la lista de categorías
            while(rs.next()){
                int id = rs.getInt("id"); // Obtiene el ID de la categoría desde la base de datos
                String nombre = rs.getString("nombre");// Obtiene la descripción de la categoría desde la base de datos
                marcas.add(new Marca(id, nombre)); // Crea un objeto Categoria y lo agrega a la lista
            }
            
        } catch (SQLException e) {
            // Captura cualquier excepción SQL que pueda ocurrir durante la recuperación y muestra un mensaje de error
            System.out.println("Error: " + e.getMessage());
        }
        
        // Devuelve la lista de categorías recuperadas desde la base de datos
        return marcas;
    }
    
    static public Categoria obtenerCategoria(String idCategoria){
        String sql = "SELECT id, nombre, descripcion FROM Categoria WHERE id='" + idCategoria + "'";
        Categoria categoria = null;
        try {
            Statement st = Conexion.db.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                int id = rs.getInt("id"); // Obtiene el ID de la categoría desde la base de datos
                String nombre = rs.getString("nombre"); // Obtiene el nombre de la categoría desde la base de datos
                String descripcion = rs.getString("descripcion"); // Obtiene la descripción de la categoría desde la base de datos
                
                categoria = new Categoria(id, nombre, descripcion);
            }
            
        } catch (SQLException e) {
            // Captura cualquier excepción SQL que pueda ocurrir durante la recuperación y muestra un mensaje de error
            System.out.println("Error: " + e.getMessage());
        }
        
        return categoria;
    }
}

