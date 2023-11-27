/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author intel
 */
public class Usuario{
    private String id;
    private String nombre;
    private String apellido;
    private String password;
    private String telefono;
    private String email;
    private String dni;
    private int idRol;
            
    private boolean estado;
    
    private Rol rol;
    
    public Usuario(){
        
    }
    
    public Usuario(String id, String nombre, String apellido, String telefono, String dni, String email, boolean estado, int idRol) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.dni = dni;
        this.estado = estado;
        this.idRol = idRol;
        this.email = email;
    }
    
    public Usuario(String id, String nombre, String apellido, String telefono, String dni, String email, boolean estado, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.estado = estado;
        this.dni = dni;
        this.email = email;
        this.rol = rol;
    }
    
    public static String generarSiguienteCodigo(String ultimoId) {
        Pattern patron = Pattern.compile("\\d+");
        Matcher matcher = patron.matcher(ultimoId);
        int numero = 0;
        if (matcher.find()) {
            numero = Integer.parseInt(matcher.group());
        }
 
        numero++;

        // Formatear el nuevo código con ceros a la izquierda
        String nuevoNumero = String.format("%08d", numero);
        String nuevoCodigo = "U" + nuevoNumero;

        return nuevoCodigo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getDni() {
        return dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }    

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }
}
