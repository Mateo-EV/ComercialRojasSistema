/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import conexion.Conexion;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Rol;

/**
 *
 * @author intel
 */
public class RolControlador {
    static public List<Rol> obtenerRoles(){
        List<Rol> roles = new ArrayList<>(); // Crea una lista para almacenar las categorías recuperadas
        String sql = "SELECT id, nombre FROM Rol"; // Consulta SQL para seleccionar todas las categorías
        
        try {
            Statement st = Conexion.db.createStatement(); // Crea una declaración SQL para ejecutar la consulta
            ResultSet rs = st.executeQuery(sql); // Ejecuta la consulta y obtiene el conjunto de resultados
            
            // Itera a través de los resultados y crea objetos Categoria, luego los agrega a la lista de categorías
            while(rs.next()){
                int id = rs.getInt("id"); // Obtiene el ID de la categoría desde la base de datos
                String nombre = rs.getString("nombre");// Obtiene la descripción de la categoría desde la base de datos
                roles.add(new Rol(id, nombre)); // Crea un objeto Categoria y lo agrega a la lista
            }
            
        } catch (SQLException e) {
            // Captura cualquier excepción SQL que pueda ocurrir durante la recuperación y muestra un mensaje de error
            System.out.println("Error: " + e.getMessage());
        }
        
        // Devuelve la lista de categorías recuperadas desde la base de datos
        return roles;
    }
}
