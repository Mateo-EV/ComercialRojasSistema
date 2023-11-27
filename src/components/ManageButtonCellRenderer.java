/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components;

import dialogModals.ManageCategoriaModal;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author intel
 */
public class ManageButtonCellRenderer extends DefaultTableCellRenderer {
    // Sobrescribe el método getTableCellRendererComponent para personalizar la apariencia de las celdas de botones en una JTable
    
    private JButton EDITAR = new JButton("Editar");
    private JButton ELIMINAR = new JButton("Eliminar");
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {      
        JPanel panel = new JPanel();
        
        EDITAR.setBackground(new Color(255, 200, 0));
        EDITAR.setFont(new java.awt.Font("Segoe UI", 1, 12));
        
        ELIMINAR.setBackground(new Color(255, 0, 0));
        ELIMINAR.setFont(new java.awt.Font("Segoe UI", 1, 12));
       
        panel.add(EDITAR);
        panel.add(ELIMINAR);
        // Devuelve la celda personalizada como un componente para que se muestre en la tabla
        return panel;
    }
}
