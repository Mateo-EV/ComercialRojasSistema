/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;

/**
 *
 * @author intel
 */
public class jInput extends JTextField implements FocusListener {

    private String placeholder; // Variable para almacenar el texto de marcador de posición
    private boolean isPlaceholder = true; // Bandera para determinar si el texto actual es el marcador de posición

    // Constructor que acepta un texto de marcador de posición
    public jInput(String placeholder) {
        this.placeholder = placeholder;
        this.setText(placeholder); // Establece el texto de marcador de posición como el texto inicial del campo de texto
        this.setForeground(Color.GRAY); // Establece el color del texto como gris
        addFocusListener(this); // Agrega el objeto actual como un listener para los eventos de foco
    }

    // Constructor predeterminado que establece un marcador de posición predeterminado
    public jInput() {
       this("Placeholder");
    }

    // Método para obtener el valor actual del campo de texto (ignorando el marcador de posición si está presente)
    public String getValue() {
        if (isPlaceholder) return "";
        return getText().trim();
    }
    
    public void setValue(String value) {
        setText(value);
        setForeground(Color.BLACK);
        isPlaceholder = false;
    }

    // Método que se ejecuta cuando el componente obtiene el foco
    @Override
    public void focusGained(FocusEvent e) {
        if (isPlaceholder) {
            this.setText(""); // Borra el texto de marcador de posición al obtener el foco
            this.setForeground(Color.BLACK); // Cambia el color del texto a negro
            isPlaceholder = false; // Cambia la bandera indicando que el marcador de posición ya no está presente
        }
    }

    // Método que se ejecuta cuando el componente pierde el foco
    @Override
    public void focusLost(FocusEvent e) {
        if (this.getText().isEmpty()) {
            this.setText(placeholder); // Restaura el texto de marcador de posición si el campo de texto está vacío
            this.setForeground(Color.GRAY); // Cambia el color del texto a gris
            isPlaceholder = true; // Restaura la bandera indicando que el marcador de posición está presente
        }
    }

    // Método para establecer un nuevo texto de marcador de posición
    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    // Método para obtener el texto de marcador de posición actual
    public String getPlaceholder() {
        return placeholder;
    }
}






