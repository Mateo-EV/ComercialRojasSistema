/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components;

import conexion.Conexion;
import dialogModals.ManageCategoriaModal;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import modelo.Rol;

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
        
        Map<String, Object> props = (Map<String, Object>) value;
        String model = (String) props.get("model");
        Boolean isVentaModel = model.equals("Venta");
        Boolean passed15Minutes = false;
        if(isVentaModel){
            LocalDateTime fecha = (LocalDateTime) props.get("fecha");
            Duration duration = Duration.between(fecha, LocalDateTime.now());
            passed15Minutes = duration.toMinutes() > 15;
        }
        
        int rolUsuario = Conexion.session.getIdRol();
        Boolean isAllowedToEdit = ((rolUsuario == Rol.ADMINISTRADOR && (!isVentaModel || !passed15Minutes)) || (rolUsuario == Rol.CAJERO && isVentaModel && !passed15Minutes));
        Boolean isAllowedToDelete = ((rolUsuario == Rol.ADMINISTRADOR && (!isVentaModel || !passed15Minutes)) || (rolUsuario == Rol.CAJERO && isVentaModel && !passed15Minutes));
        
        if(isAllowedToEdit){
            props.put("view", false);
            EDITAR.setBackground(new Color(255, 200, 0));
        } else {
            props.put("view", true);
            EDITAR.setBackground(Color.GREEN);
            EDITAR.setText("Ver");
        }
        
        EDITAR.setFont(new java.awt.Font("Segoe UI", 1, 12));
        
        ELIMINAR.setVisible(isAllowedToDelete);
        
        ELIMINAR.setBackground(new Color(255, 0, 0));
        ELIMINAR.setFont(new java.awt.Font("Segoe UI", 1, 12));
       
        panel.add(EDITAR);
        panel.add(ELIMINAR);
        // Devuelve la celda personalizada como un componente para que se muestre en la tabla
        return panel;
    }
}
