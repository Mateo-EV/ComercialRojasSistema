/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components;

import controlador.CategoriaControlador;
import controlador.ClienteControlador;
import controlador.ProductoControlador;
import controlador.ProveedorControlador;
import controlador.UsuarioControlador;
import dialogModals.ManageCategoriaModal;
import dialogModals.ManageClienteModal;
import dialogModals.ManageProductoModal;
import dialogModals.ManageProveedorModal;
import dialogModals.ManageUsuarioModal;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.AbstractCellEditor;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import vista.dashboard.CategoriaPage;
import vista.dashboard.ClientePage;
import vista.dashboard.ProductoPage;
import vista.dashboard.ProveedorPage;
import vista.dashboard.UsuarioPage;

/**
 *
 * @author intel
 */
public class ManageButtonEditorRenderer extends AbstractCellEditor implements TableCellEditor, ActionListener {
    
    private JButton EDITAR;
    private JButton ELIMINAR;
    
    @Override
    public java.awt.Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        JPanel panel = new JPanel();
        
        EDITAR = new JButton("Editar");
        ELIMINAR = new JButton("Eliminar");

        EDITAR.setBackground(new Color(255, 200, 0));
        EDITAR.setFont(new java.awt.Font("Segoe UI", 1, 12));
        
        EDITAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditarButtonActionPerformed((Map<String, Object>) value);
            }
        });
        
        ELIMINAR.setBackground(new Color(255, 0, 0));
        ELIMINAR.setFont(new java.awt.Font("Segoe UI", 1, 12));
        
        ELIMINAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarButtonActionPerformed((Map<String, Object>) value);
            }
        });
       
        panel.add(EDITAR);
        panel.add(ELIMINAR);
        // Devuelve la celda personalizada como un componente para que se muestre en la tabla
        return panel;
    }
    
    private void EditarButtonActionPerformed(Map<String, Object> props){
        String model = (String) props.get("model");
        String id = String.valueOf(props.get("idModel"));
        javax.swing.JFrame parent = (javax.swing.JFrame) props.get("parent");
        
        switch (model) {
            case "Categoria" -> {
                ManageCategoriaModal dialog = new ManageCategoriaModal(parent, true, id);
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
            case "Producto" -> {
                ManageProductoModal dialog = new ManageProductoModal(parent, true, id);
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
            case "Usuario" -> {
                ManageUsuarioModal dialog = new ManageUsuarioModal(parent, true, id);
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
            case "Cliente" -> {
                ManageClienteModal dialog = new ManageClienteModal(parent, true, id);
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
            case "Proveedor" -> {
                ManageProveedorModal dialog = new ManageProveedorModal(parent, true, id);
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        }
    }
    
    private void EliminarButtonActionPerformed(Map<String, Object> props){
        String model = (String) props.get("model");
        String id = String.valueOf(props.get("idModel"));
        javax.swing.JFrame parent = (javax.swing.JFrame) props.get("parent");
        
        switch (model) {
            case "Categoria" -> {
                int respuesta = JOptionPane.showConfirmDialog(
                        parent,
                        "¿Estás seguro que desea eliminar la categoria?",
                        "Advertencia",
                        JOptionPane.OK_CANCEL_OPTION
                );
                
                if(respuesta == 0){
                    int operacion = CategoriaControlador.eliminarCategoria(id);
                    switch (operacion) {
                        case 1 -> JOptionPane.showMessageDialog(parent, "No se puede eliminar una categoria que tiene al menos un producto");
                        case 0 -> {
                            JOptionPane.showMessageDialog(parent, "Registro elminado con éxito");
                            CategoriaPage.recagarTabla();
                        }
                        default -> JOptionPane.showMessageDialog(parent, "Ocurrió un error al eliminar la categoría");
                    }
                }
            }
            case "Producto" -> {
                int respuesta = JOptionPane.showConfirmDialog(
                        parent,
                        "¿Estás seguro que desea eliminar el producto?",
                        "Advertencia",
                        JOptionPane.OK_CANCEL_OPTION
                );
                
                
                if(respuesta == 0){
                    int operacion = ProductoControlador.eliminarProducto(id);
                    
                    switch (operacion) {
                        case 1 -> JOptionPane.showMessageDialog(parent, "No se puede eliminar un producto que se ha vendido");
                        case 2 -> JOptionPane.showMessageDialog(parent, "No se puede eliminar un producto que se ha comprado");
                        case 0 -> {
                            JOptionPane.showMessageDialog(parent, "Registro elminado con éxito");
                            ProductoPage.recagarTabla();
                        }
                        default -> JOptionPane.showMessageDialog(parent, "Ocurrió un error al eliminar el producto");
                    }
                }
                
            }
            case "Usuario" -> {
                int respuesta = JOptionPane.showConfirmDialog(
                        parent,
                        "¿Estás seguro que desea eliminar al usuario?",
                        "Advertencia",
                        JOptionPane.OK_CANCEL_OPTION
                );
                
                if(respuesta == 0){
                    int operacion = UsuarioControlador.eliminarUsuario(id);
                    switch (operacion) {
                        case 1 -> JOptionPane.showMessageDialog(parent, "No se puede eliminar a un administrador");
                        case 0 -> {
                            JOptionPane.showMessageDialog(parent, "Usuario elminado con éxito");
                            UsuarioPage.recagarTabla();
                        }
                        default -> JOptionPane.showMessageDialog(parent, "Ocurrió un error al eliminar al usuario");
                    }
                }
            }
            case "Cliente" -> {
                int respuesta = JOptionPane.showConfirmDialog(
                        parent,
                        "¿Estás seguro que desea eliminar al cliente?",
                        "Advertencia",
                        JOptionPane.OK_CANCEL_OPTION
                );
                
                if(respuesta == 0){
                    int operacion = ClienteControlador.eliminarCliente(id);
                    switch (operacion) {
                        case 1 -> JOptionPane.showMessageDialog(parent, "No se puede eliminar un cliente al que se le ha vendido");
                        case 0 -> {
                            JOptionPane.showMessageDialog(parent, "Cliente elminado con éxito");
                            ClientePage.recagarTabla();
                        }
                        default -> JOptionPane.showMessageDialog(parent, "Ocurrió un error al eliminar el cliente");
                    }
                }
            }
            case "Proveedor" -> {
                int respuesta = JOptionPane.showConfirmDialog(
                        parent,
                        "¿Estás seguro que desea eliminar al proveedor?",
                        "Advertencia",
                        JOptionPane.OK_CANCEL_OPTION
                );
                
                if(respuesta == 0){
                    int operacion = ProveedorControlador.eliminarProveedor(id);
                    switch (operacion) {
                        case 1 -> JOptionPane.showMessageDialog(parent, "No se puede eliminar a un proveedor al que se le ha comprado");
                        case 0 -> {
                            JOptionPane.showMessageDialog(parent, "Proveedor elminado con éxito");
                            ProveedorPage.recagarTabla();
                        }
                        default -> JOptionPane.showMessageDialog(parent, "Ocurrió un error al eliminar al proveedor");
                    }
                }
            }
            
        }
    }

    @Override
    public Object getCellEditorValue() {
       return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fireEditingStopped();
    }
}
