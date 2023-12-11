/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package vista.dashboard;


import dialogModals.ManageCategoriaModal;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import components.ManageButtonCellRenderer;
import components.ManageButtonEditorRenderer;
import controlador.ProveedorControlador;
import dialogModals.ManageProveedorModal;
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.TableColumn;
import modelo.Proveedor;

/**
 *
 * @author intel
 */
public class ProveedorPage extends javax.swing.JPanel {

    /**
     * Creates new form CategoriaPage
     */
     // Constructor de la clase, recibe el JFrame padre como parámetro
    public ProveedorPage(javax.swing.JFrame parent) {
        initComponents(); // Inicializa los componentes de la interfaz de usuario
        this.parent = parent; // Establece el JFrame padre
        
        // Configura el renderizador de celdas para la columna "Acciones" de la tabla
        configurarTabla();
    }
    
    private javax.swing.JFrame parent;

    private static void configurarTabla(){
        TableColumn columnActions = jTable1.getColumnModel().getColumn(5);
        
        columnActions.setCellRenderer(new ManageButtonCellRenderer());
        columnActions.setCellEditor(new ManageButtonEditorRenderer());
        columnActions.setMaxWidth(160);
        columnActions.setMinWidth(160);
        
        jTable1.getColumnModel().getColumn(0).setMaxWidth(50); // Establece el ancho máximo de la columna "Id"
        jTable1.setRowHeight(30); // Establece la altura de las filas de la tabla
    }
    
    public static void recagarTabla(){
        jTable1.setModel(cargarTablaProveedores());
        configurarTabla();
    }
    
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
        BuscarProveedoresInput = new components.jInput("Buscar proveedores");
        OpenModalButton = new components.jButton();
        jButton1 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Proveedores");

        jTable1.setModel(this.cargarTablaProveedores());
        jScrollPane1.setViewportView(jTable1);

        BuscarProveedoresInput.setText("Buscar proveedores");

        OpenModalButton.setText("Agregar Proveedor");
        OpenModalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenModalButtonActionPerformed(evt);
            }
        });

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
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
                        .addComponent(BuscarProveedoresInput, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addGap(335, 335, 335)
                        .addComponent(OpenModalButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(31, 31, 31))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(OpenModalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(BuscarProveedoresInput, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
                .addGap(31, 31, 31))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void OpenModalButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpenModalButtonActionPerformed
        // Crea una instancia del modal de agregar categoría y lo muestra en el centro de la pantalla
        ManageProveedorModal dialog = new ManageProveedorModal(parent, true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }//GEN-LAST:event_OpenModalButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String nombreCategoria = BuscarProveedoresInput.getValue();
        jTable1.setModel(cargarTablaProveedores(nombreCategoria));
        configurarTabla();
    }//GEN-LAST:event_jButton1ActionPerformed


    static public DefaultTableModel cargarTablaProveedores(){
        DefaultTableModel tableModel = new DefaultTableModel(); // Crea un modelo de tabla
        
        // Obtiene la lista de categorías desde el controlador de categorías
        List<Proveedor> proveedores = ProveedorControlador.obtenerProveedores();
  
        // Agrega las columnas al modelo de tabla
        tableModel.addColumn("Id");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Direccion");
        tableModel.addColumn("Telefono");
        tableModel.addColumn("Email");
        tableModel.addColumn("Acciones");
        
        // Llena el modelo de tabla con los datos de las categorías
        for (Proveedor proveedor : proveedores) {
            Map<String, Object> manageModelProps = new HashMap();
            manageModelProps.put("model", "Proveedor");
            manageModelProps.put("idModel", proveedor.getId());
            
            Object fila[] = {
                proveedor.getId(),
                proveedor.getNombre(),
                proveedor.getDireccion(),
                proveedor.getTelefono(),
                proveedor.getEmail(),
                manageModelProps
            };
            
            tableModel.addRow(fila); // Agrega una fila al modelo de tabla
        }
        
        return tableModel; // Devuelve el modelo de tabla lleno con los datos de las categorías
    }                                        


    static public DefaultTableModel cargarTablaProveedores(String search){
        DefaultTableModel tableModel = new DefaultTableModel(); // Crea un modelo de tabla
        
        // Obtiene la lista de categorías desde el controlador de categorías
        List<Proveedor> proveedores = ProveedorControlador.obtenerProveedores(search);
  
        // Agrega las columnas al modelo de tabla
        tableModel.addColumn("Id");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Direccion");
        tableModel.addColumn("Telefono");
        tableModel.addColumn("Email");
        tableModel.addColumn("Acciones");
        
        // Llena el modelo de tabla con los datos de las categorías
        for (Proveedor proveedor : proveedores) {
            Map<String, Object> manageModelProps = new HashMap();
            manageModelProps.put("model", "Proveedor");
            manageModelProps.put("idModel", proveedor.getId());
            
            Object fila[] = {
                proveedor.getId(),
                proveedor.getNombre(),
                proveedor.getDireccion(),
                proveedor.getTelefono(),
                proveedor.getEmail(),
                manageModelProps
            };
            
            tableModel.addRow(fila); // Agrega una fila al modelo de tabla
        }
        
        return tableModel; // Devuelve el modelo de tabla lleno con los datos de las categorías
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private components.jInput BuscarProveedoresInput;
    private components.jButton OpenModalButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
