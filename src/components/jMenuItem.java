/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components;

import java.awt.Cursor;
import javax.swing.JButton;

/**
 *
 * @author intel
 */
public class jMenuItem extends JButton {
    
    // Constructor de la clase
    public jMenuItem() {
        // Establece el fondo del botón como rojo
        this.setBackground(new java.awt.Color(255, 0, 0));
        // Establece la fuente del texto del botón como "Segoe UI" con negrita y tamaño 12
        this.setFont(new java.awt.Font("Segoe UI", 1, 12));
        // Establece el color del texto del botón como blanco
        this.setForeground(java.awt.Color.white);
        // Elimina el borde del botón
        this.setBorder(null);
        // Establece el cursor del ratón como el cursor de mano cuando pasa sobre el botón
        this.setCursor(new Cursor(12));
    }
    
    // Método para establecer el estado activo o inactivo del botón
    public void setActive(boolean isActive) {
        // Si isActive es verdadero, establece el fondo del botón como blanco y el texto como rojo
        // Si isActive es falso, establece el fondo del botón como rojo y el texto como blanco
        this.setBackground(isActive ? java.awt.Color.white : new java.awt.Color(255, 0, 0));
        this.setForeground(isActive ? new java.awt.Color(255, 0, 0) : java.awt.Color.white);
    }
}

