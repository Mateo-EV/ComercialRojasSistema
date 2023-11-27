package main;
import conexion.Conexion;

import vista.Login;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author intel
 */
public class Main {
    public static void main(String[] args) {
        Conexion.conectar(); //Conexión a la base de datos para ejecutar el programa
        Login.main(args); //Presentar interfaz de login
    }
}
