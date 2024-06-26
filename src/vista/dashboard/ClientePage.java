/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package vista.dashboard;

import components.ManageButtonCellRenderer;
import components.ManageButtonEditorRenderer;
import controlador.ClienteControlador;
import dialogModals.ManageClienteModal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import modelo.Cliente;

/**
 *
 * @author intel
 */
public class ClientePage extends javax.swing.JPanel {

    /**
     * Creates new form CategoriaPage
     */
    public ClientePage(javax.swing.JFrame parent) {
        initComponents();
        this.parent = parent;
        
        configurarTabla();
    }
    
    
    private static void configurarTabla(){
        TableColumn columnActions = jTable1.getColumnModel().getColumn(8);
        
        columnActions.setCellRenderer(new ManageButtonCellRenderer());
        columnActions.setCellEditor(new ManageButtonEditorRenderer());
        columnActions.setMaxWidth(160);
        columnActions.setMinWidth(160);
        
        jTable1.getColumnModel().getColumn(0).setMaxWidth(50); // Establece el ancho máximo de la columna "Id"
        jTable1.setRowHeight(30); // Establece la altura de las filas de la tabla
    }
    
    public static void recagarTabla(){
        jTable1.setModel(cargarTablaClientes());
        configurarTabla();
    }
    
    static public DefaultTableModel cargarTablaClientes(){
        DefaultTableModel tableModel = new DefaultTableModel(); // Crea un modelo de tabla
        
        // Obtiene la lista de categorías desde el controlador de categorías
        List<Cliente> clientes = ClienteControlador.obtenerClientes();
  
        // Agrega las columnas al modelo de tabla
        tableModel.addColumn("Id");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Apellido");
        tableModel.addColumn("DNI");
        tableModel.addColumn("Email");
        tableModel.addColumn("Telefono");
        tableModel.addColumn("Direccion");
        tableModel.addColumn("Tipo de Cliente");
        tableModel.addColumn("Acciones");
        
        // Llena el modelo de tabla con los datos de las categorías
        for (Cliente cliente : clientes) {
            Map<String, Object> manageModelProps = new HashMap<>();
            manageModelProps.put("model", "Cliente");
            manageModelProps.put("idModel", cliente.getId());
            
            Object fila[] = {
                cliente.getId(),
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getDni_ruc(),
                cliente.getEmail(),
                cliente.getTelefono(),
                cliente.getDireccion(),
                cliente.getTipoCliente(),
                manageModelProps
            };
            
            tableModel.addRow(fila); // Agrega una fila al modelo de tabla
        }
        
        return tableModel; // Devuelve el modelo de tabla lleno con los datos de las categorías
    }
    
    static public DefaultTableModel cargarTablaClientes(String search){
        DefaultTableModel tableModel = new DefaultTableModel(); // Crea un modelo de tabla
        
        // Obtiene la lista de categorías desde el controlador de categorías
        List<Cliente> clientes = ClienteControlador.obtenerClientes(search);
  
        // Agrega las columnas al modelo de tabla
        tableModel.addColumn("Id");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Apellido");
        tableModel.addColumn("DNI");
        tableModel.addColumn("Email");
        tableModel.addColumn("Telefono");
        tableModel.addColumn("Direccion");
        tableModel.addColumn("Tipo de Cliente");
        tableModel.addColumn("Acciones");
        
        // Llena el modelo de tabla con los datos de las categorías
        for (Cliente cliente : clientes) {
            Map<String, Object> manageModelProps = new HashMap<>();
            manageModelProps.put("model", "Cliente");
            manageModelProps.put("idModel", cliente.getId());
            
            Object fila[] = {
                cliente.getId(),
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getDni_ruc(),
                cliente.getEmail(),
                cliente.getTelefono(),
                cliente.getDireccion(),
                cliente.getTipoCliente(),
                manageModelProps
            };
            
            tableModel.addRow(fila); // Agrega una fila al modelo de tabla
        }
        
        return tableModel; // Devuelve el modelo de tabla lleno con los datos de las categorías
    }
    
    private javax.swing.JFrame parent;

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        BuscarClientesInput = new components.jInput("Buscar Clientes");
        jButton1 = new components.jButton();
        jButton2 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Clientes");

        jTable1.setModel(cargarTablaClientes());
        jScrollPane1.setViewportView(jTable1);

        BuscarClientesInput.setText("Buscar Clientes");

        jButton1.setText("Agregar Cliente");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Buscar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(BuscarClientesInput, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addGap(335, 335, 335)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(31, 31, 31))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BuscarClientesInput, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
                .addGap(31, 31, 31))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ManageClienteModal dialog = new ManageClienteModal(parent, true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String nombreCategoria = BuscarClientesInput.getValue();
        jTable1.setModel(cargarTablaClientes(nombreCategoria));
        configurarTabla();
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private components.jInput BuscarClientesInput;
    private components.jButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
