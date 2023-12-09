/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package dialogModals;

import controlador.ClienteControlador;
import controlador.CompraControlador;
import controlador.ProductoControlador;
import controlador.ProveedorControlador;
import controlador.VentaControlador;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import modelo.Proveedor;
import modelo.Producto;
import modelo.Compra;

import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.CompraProducto;
import modelo.VentaProducto;
import vista.dashboard.CompraPage;
import vista.dashboard.VentaPage;

/**
 *
 * @author intel
 */
public class ManageCompraModal extends javax.swing.JDialog {

    /**
     * Creates new form AddCategoriaModal
     */
    public ManageCompraModal(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        cargarProveedores();
        cargarProductos();
    }
    
    private List<Proveedor> proveedores;
    private List<Producto> productos;
    
    private void cargarProveedores(){
        proveedores = ProveedorControlador.obtenerProveedores();
        ProveedorComboBoxModel.addElement("Seleccionar Proveedor");
        int indexSelected = 0;
        for (int i = 0; i < proveedores.size(); i++) {
            ProveedorComboBoxModel.addElement(proveedores.get(i));
            if(this.idProveedor == proveedores.get(i).getId())
                indexSelected = i+1;  
        }
        ProveedoresComboBox.setModel(ProveedorComboBoxModel);
        if(indexSelected >= 1){
            ProveedoresComboBox.setSelectedIndex(indexSelected);
        }
    }
    
    private void cargarProductos(){
        productos = ProductoControlador.obtenerProductos();
        ProductoComboBoxModel.addElement("Seleccionar Producto");
        int indexSelected = 0;
        for (int i = 0; i < productos.size(); i++) {
            ProductoComboBoxModel.addElement(productos.get(i));
            if(this.idProducto == productos.get(i).getId())
                indexSelected = i+1;  
        }
        ProductosComboBox.setModel(ProductoComboBoxModel);
        if(indexSelected >= 1){
            ProductosComboBox.setSelectedIndex(indexSelected);
        }
    }
    
   
    private DefaultTableModel tablaProductosModel = new DefaultTableModel(new Object[]{"Nombre","Cantidad","PrecioUnitario","Total"}, 0);
    private DefaultComboBoxModel ProveedorComboBoxModel = new DefaultComboBoxModel();
    private DefaultComboBoxModel ProductoComboBoxModel = new DefaultComboBoxModel();
    
    private String idCompra;
    private int idProveedor = -1;
    private int idProducto = -1;
    
    public ManageCompraModal(java.awt.Frame parent, boolean modal, String id) {
        super(parent, modal);
        
        Compra compra = CompraControlador.obtenerCompra(id);
        initComponents();
        
        jLabel1.setText("Editar Compra");
        this.idProveedor = compra.getIdProveedor();
        
        crearVentaButton.setText("Guardar");
        cargarProveedores();
        cargarProductos();
        
        compra.getComprasProducto().forEach((compraProducto) -> {
            tablaProductosModel.addRow(new Object[]{
                compraProducto,
                compraProducto.getCantidad(),
                compraProducto.getPrecioUnitario(),
                compraProducto.calcularTotal(),
            });
        });
        
        cargarDatosFinales();
        
        this.idCompra = id;
        deleteProduct.setEnabled(true);
        CompraPage.recagarTabla();
    }
    
    public ManageCompraModal(java.awt.Frame parent, boolean modal, String id, boolean view) {
        super(parent, modal);
        
        Compra compra = CompraControlador.obtenerCompra(id);
        initComponents();
        
        jLabel1.setText("Detalle de la Venta");
        this.idProveedor = compra.getIdProveedor();
        
        cargarProveedores();
        jLabel5.setText("Usuario");
        
        compra.getComprasProducto().forEach((ventaProducto) -> {
            tablaProductosModel.addRow(new Object[]{
                ventaProducto,
                ventaProducto.getCantidad(),
                ventaProducto.getPrecioUnitario(),
                ventaProducto.calcularTotal(),
            });
        });
        
        cargarDatosFinales();
        
        ProveedoresComboBox.setEnabled(false);
        ProductoComboBoxModel.addElement(compra.getUsuario().getNombre());
        ProductosComboBox.setModel(ProductoComboBoxModel);
        ProductosComboBox.setEnabled(false);
        this.remove(crearVentaButton);
        AgregarProductoButton.setEnabled(false);
        BuscarProveedorInput.setVisible(false);
        BuscarProductoInput.setPlaceholder("");
        BuscarProductoInput.setEnabled(false);
          
        cantidadInput.setEnabled(false);
        
        this.idCompra = id;
        
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
        jLabel2 = new javax.swing.JLabel();
        AgregarProductoButton = new components.jButton();
        jLabel5 = new javax.swing.JLabel();
        cantidadInput = new components.jInput("");
        jLabel7 = new javax.swing.JLabel();
        BuscarProveedorInput = new components.jInput("Buscar Proveedor");
        ProveedoresComboBox = new javax.swing.JComboBox<>();
        ProductosComboBox = new javax.swing.JComboBox<>();
        BuscarProductoInput = new components.jInput("Buscar Producto");
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaProductos = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        TotalInput = new javax.swing.JTextField();
        crearVentaButton = new components.jButton();
        precioInput = new components.jInput("");
        jLabel8 = new javax.swing.JLabel();
        deleteProduct = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Crear Compra");

        jLabel2.setText("Proveedor");

        AgregarProductoButton.setText("Añadir");
        AgregarProductoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarProductoButtonActionPerformed(evt);
            }
        });

        jLabel5.setText("Producto");

        cantidadInput.setText("");

        jLabel7.setText("Cantidad");

        BuscarProveedorInput.setText("Buscar Proveedor");
        BuscarProveedorInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                BuscarProveedorInputKeyTyped(evt);
            }
        });

        BuscarProductoInput.setText("Buscar Producto");
        BuscarProductoInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                BuscarProductoInputKeyTyped(evt);
            }
        });

        TablaProductos.setModel(tablaProductosModel);
        jScrollPane1.setViewportView(TablaProductos);

        jLabel6.setText("Total");

        TotalInput.setEnabled(false);

        crearVentaButton.setText("Crear Compra");
        crearVentaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crearVentaButtonActionPerformed(evt);
            }
        });

        precioInput.setText("");

        jLabel8.setText("Precio");

        deleteProduct.setBackground(new java.awt.Color(255, 51, 51));
        deleteProduct.setForeground(new java.awt.Color(255, 255, 255));
        deleteProduct.setText("X");
        deleteProduct.setEnabled(false);
        deleteProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteProductActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5)
                            .addComponent(ProveedoresComboBox, 0, 249, Short.MAX_VALUE)
                            .addComponent(ProductosComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(BuscarProveedorInput, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(BuscarProductoInput, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(precioInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(cantidadInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(AgregarProductoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(deleteProduct))))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(TotalInput, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(crearVentaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ProveedoresComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BuscarProveedorInput, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(89, 89, 89)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(deleteProduct)
                                .addGap(14, 14, 14)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cantidadInput, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ProductosComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BuscarProductoInput, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AgregarProductoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(precioInput, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TotalInput, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(crearVentaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AgregarProductoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarProductoButtonActionPerformed
        Producto productoSeleccionado;
        try {
            productoSeleccionado = (Producto) ProductosComboBox.getSelectedItem();
        } catch (ClassCastException ex) {
            JOptionPane.showMessageDialog(null, "Elija un producto para agregar");
            return;
        }
        
        boolean productoAgregadoAnteriormente = false;
        for(Vector<Object> fila : tablaProductosModel.getDataVector()){
            CompraProducto compraProducto = (CompraProducto) fila.firstElement();
            if(compraProducto.getProducto().getId() == productoSeleccionado.getId()){
                productoAgregadoAnteriormente = true;
                break;
            }
        };
        
        if(productoAgregadoAnteriormente){
            JOptionPane.showMessageDialog(null, "El producto ya ha sido agregado con anterioridad");
            return;
        }
        
        Producto producto = ProductoControlador.obtenerProducto(String.valueOf(productoSeleccionado.getId()));
        
        double precioUnitario;
        try {
            precioUnitario = Double.parseDouble(precioInput.getValue());
            if(precioUnitario <= 0) throw new NumberFormatException();
            BigDecimal bigDecimal = new BigDecimal(precioUnitario).setScale(2, RoundingMode.HALF_UP);
            if (bigDecimal.valueOf(precioUnitario).scale() > 2) throw new NumberFormatException();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "El precio debe ser un número con 2 decimales máximos mayor a 0");
            return;
        }
        
        if(precioUnitario >= producto.getPrecio()) {
            JOptionPane.showMessageDialog(null, "El precio de compra es mayor o igual al precio de venta: S/." + producto.getPrecio());
            return;
        }
        
        int cantidadSolicitada;
        try {
            cantidadSolicitada = Integer.parseInt(cantidadInput.getValue());
            if(cantidadSolicitada <= 0) throw new NumberFormatException("Error");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "La cantidad debe ser un número entero mayor a 0");
            return;
        }
        
        CompraProducto compraProducto = new CompraProducto();
        compraProducto.setProducto(producto);
        compraProducto.setPrecioUnitario(precioUnitario);
        compraProducto.setCantidad(cantidadSolicitada);
        
        tablaProductosModel.addRow(
            new Object[]{
                compraProducto,
                compraProducto.getCantidad(),
                compraProducto.getPrecioUnitario(),
                compraProducto.calcularTotal(),
            }
        );
        
        deleteProduct.setEnabled(true);
        cargarDatosFinales();
    }//GEN-LAST:event_AgregarProductoButtonActionPerformed

    private void cargarDatosFinales(){
        Vector<Vector> filas = tablaProductosModel.getDataVector();
        double total = 0;
        
        for (Vector fila : filas) {
            CompraProducto compraProducto = (CompraProducto) fila.firstElement();
            total += compraProducto.calcularTotal();
        }
        
        TotalInput.setText(String.valueOf(total));
    }
    
    private void crearVentaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crearVentaButtonActionPerformed
        if(idCompra == null)
            crearNuevaCompra();
        else
            editarCompra();
    }//GEN-LAST:event_crearVentaButtonActionPerformed

    private void BuscarProveedorInputKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BuscarProveedorInputKeyTyped
        String nombreProveedor = BuscarProveedorInput.getValue();
        List <Proveedor> clientesFiltrados = proveedores.stream()
                .filter(cliente -> cliente.toString().toLowerCase().contains(nombreProveedor.toLowerCase()))
                .collect(Collectors.toList());
        
        ProveedorComboBoxModel.removeAllElements();
        ProveedorComboBoxModel.addElement("Seleccionar Proveedor");
        clientesFiltrados.forEach(cliente -> ProveedorComboBoxModel.addElement(cliente));
        
        ProveedoresComboBox.setModel(ProveedorComboBoxModel);
        ProveedoresComboBox.setPopupVisible(true);
    }//GEN-LAST:event_BuscarProveedorInputKeyTyped

    private void BuscarProductoInputKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BuscarProductoInputKeyTyped
        String nombreProducto = BuscarProductoInput.getValue();
        List <Producto> productosFiltrados = productos.stream()
                .filter(producto -> producto.toString().toLowerCase().contains(nombreProducto.toLowerCase()))
                .collect(Collectors.toList());
        
        ProductoComboBoxModel.removeAllElements();
        ProductoComboBoxModel.addElement("Seleccionar Producto");
        productosFiltrados.forEach(producto -> ProductoComboBoxModel.addElement(producto));
        
        ProductosComboBox.setModel(ProductoComboBoxModel);
        ProductosComboBox.setPopupVisible(true);
    }//GEN-LAST:event_BuscarProductoInputKeyTyped

    private void deleteProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteProductActionPerformed
        tablaProductosModel.removeRow(TablaProductos.getSelectedRow());
        if(tablaProductosModel.getRowCount() == 0) deleteProduct.setEnabled(false);
    }//GEN-LAST:event_deleteProductActionPerformed

    private void crearNuevaCompra(){
        Compra compra = new Compra();
        Proveedor proveedor;
        
        try {
            proveedor = (Proveedor) ProveedoresComboBox.getSelectedItem();
        } catch (ClassCastException ex) {
            JOptionPane.showMessageDialog(null, "Elija un proveedor para realizar la compra");
            return;
        }
        
        if(tablaProductosModel.getRowCount() == 0){
            JOptionPane.showMessageDialog(null, "Agrege al menos un producto para realizar la compra");
            return;
        };
        
        for(Vector fila : tablaProductosModel.getDataVector()){
            CompraProducto compraProducto = (CompraProducto) fila.firstElement();
            compra.comprasProducto.add(compraProducto);
        };
        
        compra.setProveedor(proveedor);
        
        if(CompraControlador.crearCompra(compra)){
            JOptionPane.showMessageDialog(null, "Registro guardado");
            CompraPage.recagarTabla();
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Error al guardar");
        }
    }
    
    private void editarCompra(){
        Compra compra = new Compra();
        compra.setId(Integer.parseInt(idCompra));
        Proveedor proveedor;
        
        try {
            proveedor = (Proveedor) ProveedoresComboBox.getSelectedItem();
        } catch (ClassCastException ex) {
            JOptionPane.showMessageDialog(null, "Elija un proveedor para editar la compra");
            return;
        }
        
        if(tablaProductosModel.getRowCount() == 0){
            JOptionPane.showMessageDialog(null, "Agrege al menos un producto para editar la compra");
            return;
        };
        
        for(Vector fila : tablaProductosModel.getDataVector()){
            CompraProducto compraProducto = (CompraProducto) fila.firstElement();
            compra.comprasProducto.add(compraProducto);
        };
        
        compra.setProveedor(proveedor);
        
        if(CompraControlador.actualizarCompra(compra)){
            JOptionPane.showMessageDialog(null, "Registro guardado");
            CompraPage.recagarTabla();
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Error al guardar");
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ManageCompraModal dialog = new ManageCompraModal(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private components.jButton AgregarProductoButton;
    private components.jInput BuscarProductoInput;
    private components.jInput BuscarProveedorInput;
    private javax.swing.JComboBox<String> ProductosComboBox;
    private javax.swing.JComboBox<String> ProveedoresComboBox;
    private javax.swing.JTable TablaProductos;
    private javax.swing.JTextField TotalInput;
    private components.jInput cantidadInput;
    private components.jButton crearVentaButton;
    private javax.swing.JButton deleteProduct;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private components.jInput precioInput;
    // End of variables declaration//GEN-END:variables
}
