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
public class jButton extends JButton {

    // Constructor de la clase
    public jButton() {
        // Establece el fondo del botón como rojo
        this.setBackground(new java.awt.Color(255, 0, 0));
        // Establece la fuente del texto del botón como "Segoe UI" con negrita y tamaño 12
        this.setFont(new java.awt.Font("Segoe UI", 1, 12));
        // Establece el color del texto del botón como blanco
        this.setForeground(new java.awt.Color(255, 255, 255));
        // Establece el cursor del ratón como el cursor de mano cuando pasa sobre el botón
        this.setCursor(new Cursor(12));
    }
}
