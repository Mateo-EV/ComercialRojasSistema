package controlador;

import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Producto;
import modelo.Categoria;

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
        String sql = "SELECT *, (SELECT Categoria.nombre FROM Categoria WHERE Categoria.id = Producto.idCategoria) as categoriaNombre FROM Producto"; // Consulta SQL para seleccionar todas los productos

        try {
            Statement st = Conexion.db.createStatement(); // Crea una declaración SQL para ejecutar la consulta
            ResultSet rs = st.executeQuery(sql); // Ejecuta la consulta y obtiene el conjunto de resultados

            // Itera a través de los resultados y crea objetos Producto, luego los agrega a la lista de los productos
            while (rs.next()) {
                int id = rs.getInt("id");
                int stock = rs.getInt("stock");
                double precio = rs.getDouble("stock");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                String marca = rs.getString("marca");

                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("idCategoria"));
                categoria.setNombre(rs.getString("categoriaNombre"));

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
            pst.setString(2, producto.getMarca());
            pst.setInt(3, producto.getStock());
            pst.setDouble(4, producto.getPrecio());
            pst.setInt(5, producto.getCategoria().getId());
            pst.setString(6, producto.getDescripcion());

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
            PreparedStatement pst = Conexion.db.prepareStatement("UPDATE Producto SET nombre=?, descripcion=?, marca=?, precio=?, idCategoria=?, stock=? WHERE id='" + producto.getId() + "'");

            pst.setString(1, producto.getNombre());
            pst.setString(2, producto.getDescripcion());
            pst.setString(3, producto.getMarca());
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
        String sql = "SELECT Producto.*, (SELECT Categoria.nombre FROM Categoria WHERE Categoria.id = Producto.idCategoria) as categoriaNombre  FROM Producto WHERE id='" + idProducto + "'";
        Producto producto = null;
        try {
            Statement st = Conexion.db.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                int stock = rs.getInt("stock");
                double precio = rs.getDouble("stock");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                String marca = rs.getString("marca");

                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("idCategoria"));
                categoria.setNombre(rs.getString("categoriaNombre"));

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
}
