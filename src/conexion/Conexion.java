/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import modelo.Usuario;

/**
 *
 * @author intel
 */
public class Conexion {
    // Cadena de conexión para acceder a la base de datos SQL Server
    private static String connectionUrl =
                "jdbc:sqlserver://localhost:1433;"  // Dirección del servidor y número de puerto
                + "database=comercialRojas;"        // Nombre de la base de datos a la que conectarse
                + "user=sa;"                        // Nombre de usuario para la autenticación
                + "password=password;"           // Contraseña para la autenticación
                + "encrypt=true;"                   // Habilita la encriptación de datos durante la comunicación con el servidor
                + "trustServerCertificate=true;"    // Establece la confianza en el certificado del servidor
                + "loginTimeout=30;";               // Tiempo máximo en segundos para esperar una respuesta durante el intento de inicio de sesión

    // Método para establecer la conexión a la base de datos
    public static void conectar(){
        Connection con = null;
        try {
            // Intenta establecer la conexión utilizando la cadena de conexión proporcionada
            con = DriverManager.getConnection(connectionUrl);
        } catch (SQLException e) {
            // Captura cualquier excepción SQL que pueda ocurrir durante la conexión y muestra un mensaje de error
            System.out.println("Error: " + e.getMessage());
        }
        // Asigna la conexión establecida a la variable de clase 'db' para que pueda ser utilizada en otras partes del programa
        Conexion.db = con;
    }
    
    // Variable estática para almacenar la conexión a la base de datos, accesible desde otras partes del programa
    public static Connection db = null;
    
    public static Usuario session = null;
}
