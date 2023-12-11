/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Producto;
import modelo.Rol;
import modelo.Usuario;

/**
 *
 * @author intel
 */
public class UsuarioControlador {
    
    // Método estático para verificar las credenciales de un usuario en la base de datos
    static public boolean login(Usuario usuario) {
        boolean respuesta = false; // Inicializa la variable de respuesta como falso
        String sql = "SELECT id, nombre, idRol FROM Usuario WHERE id='"+ usuario.getId() +"' AND PASSWORD = '"+ usuario.getPassword() +"' AND ESTADO=1"; // Consulta SQL para verificar las credenciales
        
        try {
            // Ejecuta la consulta SQL y obtiene el conjunto de resultados
            ResultSet rs = Conexion.db.createStatement().executeQuery(sql);
            // Itera a través de los resultados
            while (rs.next()) {
                usuario.setIdRol(rs.getInt("idRol"));
                Conexion.session = usuario;
                respuesta = true; // Si hay al menos una fila en el resultado, establece la respuesta como verdadero (credenciales válidas)
            }
            
        } catch (SQLException error) {
            // Captura cualquier excepción SQL que pueda ocurrir durante la autenticación y muestra un mensaje de error
            System.out.println("Error: " + error.getMessage());
        }
        
        // Devuelve true si las credenciales son válidas, de lo contrario, devuelve false
        return respuesta;
    }
    
    static public List<Usuario> obtenerUsuarios(){
        List<Usuario> usuarios = new ArrayList(); // Crea una lista para almacenar los productos recuperados
        String sql = "SELECT *, (SELECT Rol.Nombre FROM Rol WHERE Rol.id = Usuario.idRol) as rolNombre FROM Usuario"; // Consulta SQL para seleccionar todas los productos
        
        try {
            Statement st = Conexion.db.createStatement(); // Crea una declaración SQL para ejecutar la consulta
            ResultSet rs = st.executeQuery(sql); // Ejecuta la consulta y obtiene el conjunto de resultados
            
            // Itera a través de los resultados y crea objetos Producto, luego los agrega a la lista de los productos
            while(rs.next()){
                String id = rs.getString("id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String telefono = rs.getString("telefono");
                String email = rs.getString("email");
                String dni = rs.getString("dni");
                boolean estado = rs.getBoolean("estado");
                
                Rol rol = new Rol();
                rol.setId(rs.getInt("idRol"));
                rol.setNombre(rs.getString("rolNombre"));
                
                usuarios.add(new Usuario(id, nombre, apellido, telefono, dni, email, estado, rol));
            }
            
        } catch (SQLException e) {
            // Captura cualquier excepción SQL que pueda ocurrir durante la recuperación y muestra un mensaje de error
            System.out.println("Error: " + e.getMessage());
        }
        
        // Devuelve la lista de los productos recuperados desde la base de datos
        return usuarios;
    }
    
    static public List<Usuario> obtenerUsuarios(String search){
        List<Usuario> usuarios = new ArrayList(); // Crea una lista para almacenar los productos recuperados
        String sql = "SELECT Usuario.*, Rol.nombre as rolNombre FROM Usuario "
                + "INNER JOIN Rol ON Rol.id = Usuario.idRol "
                + "WHERE Usuario.nombre like '%"+search+"%' OR "
                + "apellido like '%"+search+"%' OR "
                + "telefono like '%"+search+"%' OR "
                + "email like '%"+search+"%' OR "
                + "dni like '%"+search+"%' OR "
                + "Rol.nombre like '%"+search+"%'"; // Consulta SQL para seleccionar todas los productos
        
        try {
            Statement st = Conexion.db.createStatement(); // Crea una declaración SQL para ejecutar la consulta
            ResultSet rs = st.executeQuery(sql); // Ejecuta la consulta y obtiene el conjunto de resultados
            
            // Itera a través de los resultados y crea objetos Producto, luego los agrega a la lista de los productos
            while(rs.next()){
                String id = rs.getString("id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String telefono = rs.getString("telefono");
                String email = rs.getString("email");
                String dni = rs.getString("dni");
                boolean estado = rs.getBoolean("estado");
                
                Rol rol = new Rol();
                rol.setId(rs.getInt("idRol"));
                rol.setNombre(rs.getString("rolNombre"));
                
                usuarios.add(new Usuario(id, nombre, apellido, telefono, dni, email, estado, rol));
            }
            
        } catch (SQLException e) {
            // Captura cualquier excepción SQL que pueda ocurrir durante la recuperación y muestra un mensaje de error
            System.out.println("Error: " + e.getMessage());
        }
        
        // Devuelve la lista de los productos recuperados desde la base de datos
        return usuarios;
    }
    
    static public boolean crearUsuario(Usuario usuario){
        boolean respuesta = false;
        try {
            Statement st = Conexion.db.createStatement(); // Crea una declaración SQL para ejecutar la consulta
            ResultSet rs = st.executeQuery("SELECT TOP 1 id FROM Usuario ORDER BY id DESC");
            
            String ultimoId = "";
            while(rs.next())
                ultimoId = rs.getString("id");
            
            usuario.setId(Usuario.generarSiguienteCodigo(ultimoId));
            
            PreparedStatement pst = Conexion.db.prepareStatement("INSERT INTO Usuario VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pst.setString(1, usuario.getId());
            pst.setString(2, usuario.getNombre());
            pst.setString(3, usuario.getApellido());
            pst.setString(4, usuario.getDni());
            pst.setString(5, usuario.getPassword());
            pst.setString(6, usuario.getEmail());
            pst.setString(7, usuario.getTelefono());
            pst.setInt(8, usuario.getRol().getId());
            pst.setBoolean(9, usuario.getEstado());
            
            if(pst.executeUpdate() > 0) respuesta = true;
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        return respuesta;
    }
    static public int eliminarUsuario(String idUsuario){
        int respuesta = -1;
        try {
            
            Statement st = Conexion.db.createStatement(); // Crea una declaración SQL para ejecutar la consulta
            ResultSet rs = st.executeQuery("SELECT (SELECT Rol.nombre FROM Rol WHERE Rol.id = Usuario.idRol) as Rol FROM Usuario where id='"+ idUsuario +"'");
            
            if(rs.next()){
                if(!rs.getString("Rol").equals("ADMINISTRADOR")){
                    PreparedStatement pst = Conexion.db.prepareStatement("DELETE FROM Usuario where id='" + idUsuario +"'");
                    if(pst.executeUpdate() > 0) respuesta = 0;
                } else respuesta = 1;
            }
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        return respuesta;
    }
    
    
    static public boolean actualizarUsuario(Usuario usuario){
        boolean respuesta = false;
        try {
            String sql = "UPDATE Usuario SET nombre=?, apellido=?, dni=?,  email=?, telefono=?, idRol=?, estado=? WHERE id='" + usuario.getId() +"'";
            if(!usuario.getPassword().isEmpty())
                sql = "UPDATE Usuario SET nombre=?, apellido=?, dni=?,  email=?, telefono=?, idRol=?, estado=?, password=? WHERE id='" + usuario.getId() +"'";
            
            PreparedStatement pst = Conexion.db.prepareStatement(sql);
            
            pst.setString(1, usuario.getNombre());
            pst.setString(2, usuario.getApellido());
            pst.setString(3, usuario.getDni());
            pst.setString(4, usuario.getEmail());
            pst.setString(5, usuario.getTelefono());
            pst.setInt(6, usuario.getRol().getId());
            pst.setBoolean(7, usuario.getEstado());
            
            if(!usuario.getPassword().isEmpty())
                pst.setString(8, usuario.getPassword());
            
            if(pst.executeUpdate() > 0) respuesta = true;
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        return respuesta;
    }
    
    static public Usuario obtenerUsuario(String idUsuario){
        String sql = "SELECT * FROM Usuario WHERE id='" + idUsuario + "'";
        Usuario usuario = null;
        try {
            Statement st = Conexion.db.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                String id = rs.getString("id"); // Obtiene el ID de la categoría desde la base de datos
                String nombre = rs.getString("nombre"); // Obtiene el nombre de la categoría desde la base de datos
                String apellido = rs.getString("apellido");
                String telefono = rs.getString("telefono");
                String email = rs.getString("email");
                String dni = rs.getString("dni");
                int idRol = rs.getInt("idRol");
                boolean estado = rs.getBoolean("estado");
                
                
                usuario = new Usuario(id, nombre, apellido, telefono, dni, email, estado, idRol);
            }
            
        } catch (SQLException e) {
            // Captura cualquier excepción SQL que pueda ocurrir durante la recuperación y muestra un mensaje de error
            System.out.println("Error: " + e.getMessage());
        }
        
        return usuario;
    }
}
