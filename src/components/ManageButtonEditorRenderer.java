/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components;

import conexion.Conexion;
import controlador.CategoriaControlador;
import controlador.ClienteControlador;
import controlador.CompraControlador;
import controlador.ProductoControlador;
import controlador.ProveedorControlador;
import controlador.UsuarioControlador;
import controlador.VentaControlador;
import dialogModals.ManageCategoriaModal;
import dialogModals.ManageClienteModal;
import dialogModals.ManageCompraModal;
import dialogModals.ManageProductoModal;
import dialogModals.ManageProveedorModal;
import dialogModals.ManageUsuarioModal;
import dialogModals.ManageVentaModal;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Vector;
import javax.swing.AbstractCellEditor;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import modelo.Rol;
import modelo.Usuario;
import vista.dashboard.CategoriaPage;
import vista.dashboard.ClientePage;
import vista.dashboard.CompraPage;
import vista.dashboard.ProductoPage;
import vista.dashboard.ProveedorPage;
import vista.dashboard.UsuarioPage;
import vista.dashboard.VentaPage;

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
        
        Map<String, Object> props = (Map<String, Object>) value;
        String model = (String) props.get("model");
        Boolean isVentaModel = model.equals("Venta");
        Boolean isCompraModel = model.equals("Compra");
        Boolean passed15Minutes = false;
        if(isVentaModel || isCompraModel){
            LocalDateTime fecha = (LocalDateTime) props.get("fecha");
            Duration duration = Duration.between(fecha, LocalDateTime.now());
            passed15Minutes = duration.toMinutes() > 15;
        }
        
        EDITAR = new JButton("Editar");
        ELIMINAR = new JButton("Eliminar");
        
        int rolUsuario = Conexion.session.getIdRol();
        Boolean isAllowedToEdit = ((rolUsuario == Rol.ADMINISTRADOR && ((!isVentaModel && !isCompraModel) || !passed15Minutes)) || (rolUsuario == Rol.CAJERO && isVentaModel && !passed15Minutes) || (rolUsuario == Rol.COMPRADOR && isCompraModel && !passed15Minutes));
        Boolean isAllowedToDelete = ((rolUsuario == Rol.ADMINISTRADOR && ((!isVentaModel && !isCompraModel) || !passed15Minutes)) || (rolUsuario == Rol.CAJERO && isVentaModel && !passed15Minutes) || (rolUsuario == Rol.COMPRADOR && isCompraModel && !passed15Minutes));

        
        EDITAR.setFont(new java.awt.Font("Segoe UI", 1, 12));
        
        if(isAllowedToEdit){
            props.put("view", false);
            EDITAR.setBackground(new Color(255, 200, 0));
        } else {
            props.put("view", true);
            EDITAR.setBackground(Color.GREEN);
            EDITAR.setText("Ver");
        }
        
        ELIMINAR.setVisible(isAllowedToDelete);
        
        EDITAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditarButtonActionPerformed(props);
            }
        });
        
        ELIMINAR.setBackground(new Color(255, 0, 0));
        ELIMINAR.setFont(new java.awt.Font("Segoe UI", 1, 12));
        
        ELIMINAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarButtonActionPerformed(props);
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
                Usuario usuario = UsuarioControlador.obtenerUsuario(id);
                
                if(usuario.getIdRol() == Rol.ADMINISTRADOR && !Conexion.session.getId().equals(id)){
                    JOptionPane.showMessageDialog(parent, "No puede editar a otro usuario administrador");
                } else {
                    ManageUsuarioModal dialog = new ManageUsuarioModal(parent, true, id);
                    dialog.setLocationRelativeTo(null);
                    dialog.setVisible(true);
                }
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
            case "Venta" -> {
                ManageVentaModal dialog = null;
                if((Boolean) props.get("view")) dialog = new ManageVentaModal(parent, true, id, true);
                else dialog = new ManageVentaModal(parent, true, id);
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
            case "Compra" -> {
                ManageCompraModal dialog = null;
                if((Boolean) props.get("view")) dialog = new ManageCompraModal(parent, true, id, true);
                else dialog = new ManageCompraModal(parent, true, id);
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
            case "Venta" -> {
                int respuesta = JOptionPane.showConfirmDialog(
                        parent,
                        "¿Estás seguro que desea eliminar la venta?",
                        "Advertencia",
                        JOptionPane.OK_CANCEL_OPTION
                );
                
                if(respuesta == 0){
                    if(VentaControlador.eliminarVenta(id)){
                        JOptionPane.showMessageDialog(parent, "Registro elminado con éxito");
                        VentaPage.recagarTabla();
                    } else {
                        JOptionPane.showMessageDialog(parent, "Ocurrió un error al eliminar la venta");
                    }
                }
            }
            case "Compra" -> {
                int respuesta = JOptionPane.showConfirmDialog(
                        parent,
                        "¿Estás seguro que desea eliminar la compra?",
                        "Advertencia",
                        JOptionPane.OK_CANCEL_OPTION
                );
                
                if(respuesta == 0){
                    if(CompraControlador.eliminarCompra(id)){
                        JOptionPane.showMessageDialog(parent, "Registro elminado con éxito");
                        CompraPage.recagarTabla();
                    } else {
                        JOptionPane.showMessageDialog(parent, "Ocurrió un error al eliminar la compra");
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
