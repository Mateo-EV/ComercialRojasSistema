/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package vista.dashboard;

import controlador.ProductoControlador;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import components.ManageButtonCellRenderer;
import components.ManageButtonEditorRenderer;
import dialogModals.ManageProductoModal;
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.TableColumn;
import modelo.Producto;

/**
 *
 * @author intel
 */
public class ProductoPage extends javax.swing.JPanel {

    /**
     * Creates new form CategoriaPage
     */
     // Constructor de la clase, recibe el JFrame padre como parámetro
    public ProductoPage(javax.swing.JFrame parent) {
        initComponents(); // Inicializa los componentes de la interfaz de usuario
        this.parent = parent; // Establece el JFrame padre
        
        configurarTabla();
    }
    private static void configurarTabla(){
        TableColumn columnActions = jTable1.getColumnModel().getColumn(7);
        
        columnActions.setCellRenderer(new ManageButtonCellRenderer());
        columnActions.setCellEditor(new ManageButtonEditorRenderer());
        columnActions.setMaxWidth(160);
        columnActions.setMinWidth(160);
        
        jTable1.getColumnModel().getColumn(0).setMaxWidth(50); // Establece el ancho máximo de la columna "Id"
        jTable1.setRowHeight(30); // Establece la altura de las filas de la tabla
    }
    
    public static void recagarTabla(){
        jTable1.setModel(cargarTablaProductos());
        configurarTabla();
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
        jInput1 = new components.jInput("Buscar Productos");
        OpenModalButton = new components.jButton();
        jButton1 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Productos");

        jTable1.setModel(this.cargarTablaProductos());
        jScrollPane1.setViewportView(jTable1);

        jInput1.setText("Buscar productos");

        OpenModalButton.setText("Agregar Producto");
        OpenModalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenModalButtonActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/img/reportes.png"))); // NOI18N
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
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jInput1, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                        .addGap(355, 355, 355)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(OpenModalButton, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)))
                .addGap(31, 31, 31))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(OpenModalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jInput1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
                .addGap(31, 31, 31))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void OpenModalButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpenModalButtonActionPerformed
        // Crea una instancia del modal de agregar categoría y lo muestra en el centro de la pantalla
        ManageProductoModal dialog = new ManageProductoModal(parent, true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }//GEN-LAST:event_OpenModalButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ProductoControlador.generarReporte();
    }//GEN-LAST:event_jButton1ActionPerformed


    static public DefaultTableModel cargarTablaProductos(){
        DefaultTableModel tableModel = new DefaultTableModel(); // Crea un modelo de tabla
        
        // Obtiene la lista de categorías desde el controlador de categorías
        List<Producto> productos = ProductoControlador.obtenerProductos();
  
        // Agrega las columnas al modelo de tabla
        tableModel.addColumn("Id");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Stock");
        tableModel.addColumn("Precio");
        tableModel.addColumn("Marca");
        tableModel.addColumn("Descripcion");
        tableModel.addColumn("Categoría");
        tableModel.addColumn("Acciones");
        
        // Llena el modelo de tabla con los datos de las categorías
        for (Producto producto : productos) {
            Map<String, Object> manageModelProps = new HashMap();
            manageModelProps.put("model", "Producto");
            manageModelProps.put("idModel", producto.getId());
            
            Object fila[] = {
                producto.getId(),
                producto.getNombre(),
                producto.getStock(),
                producto.getPrecio(),
                producto.getMarca().getNombre(),
                producto.getDescripcion(),
                producto.getCategoria().getNombre(),
                manageModelProps
            };
            
            tableModel.addRow(fila); // Agrega una fila al modelo de tabla
        }
        
        return tableModel; // Devuelve el modelo de tabla lleno con los datos de las categorías
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private components.jButton OpenModalButton;
    private javax.swing.JButton jButton1;
    private components.jInput jInput1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
