/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package dialogModals;

import controlador.ClienteControlador;
import controlador.ProductoControlador;
import controlador.ProveedorControlador;
import controlador.VentaControlador;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import modelo.Proveedor;
import modelo.Producto;
import modelo.Venta;

import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.VentaProducto;
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
    
   
    private DefaultTableModel tablaProductosModel = new DefaultTableModel(new Object[]{"Nombre","Cantidad","PrecioUnitario","SubTotal","Total"}, 0);
    private DefaultComboBoxModel ProveedorComboBoxModel = new DefaultComboBoxModel();
    private DefaultComboBoxModel ProductoComboBoxModel = new DefaultComboBoxModel();
    
    private String idCompra;
    private int idProveedor = -1;
    private int idProducto = -1;
    
    public ManageCompraModal(java.awt.Frame parent, boolean modal, String id) {
        super(parent, modal);
        
        Venta venta = VentaControlador.obtenerVenta(id);
        initComponents();
        
        jLabel1.setText("Editar Compra");
        this.idProveedor = venta.getIdCliente();
        
        crearVentaButton.setText("Guardar");
        cargarProveedores();
        cargarProductos();
        
        venta.getVentasProducto().forEach((ventaProducto) -> {
            tablaProductosModel.addRow(new Object[]{
                ventaProducto,
                ventaProducto.getCantidad(),
                ventaProducto.getPrecioUnitario(),
                ventaProducto.getSubTotal(),
                ventaProducto.calcularTotal(),
            });
        });
        
        cargarDatosFinales();
        
        this.idCompra = id;
        
    }
    
    public ManageCompraModal(java.awt.Frame parent, boolean modal, String id, boolean view) {
        super(parent, modal);
        
        Venta venta = VentaControlador.obtenerVenta(id);
        initComponents();
        
        jLabel1.setText("Detalle de la Venta");
        this.idProveedor = venta.getIdCliente();
        
        cargarProveedores();
        jLabel5.setText("Usuario");
        
        
        venta.getVentasProducto().forEach((ventaProducto) -> {
            tablaProductosModel.addRow(new Object[]{
                ventaProducto,
                ventaProducto.getCantidad(),
                ventaProducto.getPrecioUnitario(),
                ventaProducto.getSubTotal(),
                ventaProducto.calcularTotal(),
            });
        });
        
        cargarDatosFinales();
        
        ProveedoresComboBox.setEnabled(false);
        ProductoComboBoxModel.addElement(venta.getUsuario().getNombre());
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
                            .addComponent(BuscarProveedorInput, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(BuscarProductoInput, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(cantidadInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(25, 25, 25)
                                        .addComponent(AgregarProductoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 0, Short.MAX_VALUE))
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
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ProveedoresComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BuscarProveedorInput, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cantidadInput, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ProductosComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BuscarProductoInput, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AgregarProductoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
            VentaProducto ventaProducto = (VentaProducto) fila.firstElement();
            if(ventaProducto.getProducto().getId() == productoSeleccionado.getId()){
                productoAgregadoAnteriormente = true;
                break;
            }
        };
        
        if(productoAgregadoAnteriormente){
            JOptionPane.showMessageDialog(null, "El producto ya ha sido agregado con anterioridad");
            return;
        }
        
        Producto producto = ProductoControlador.obtenerProducto(String.valueOf(productoSeleccionado.getId()));
        
        int cantidadSolicitada;
        try {
            cantidadSolicitada = Integer.parseInt(cantidadInput.getValue());
            if(cantidadSolicitada <= 0) throw new NumberFormatException("Error");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "La cantidad debe ser un número entero mayor a 0");
            return;
        }
        
        if(cantidadSolicitada > producto.getStock()) {
            JOptionPane.showMessageDialog(null, "La cantidad solicitada supera el stock");
            return;
        }
        
        VentaProducto ventaProducto = new VentaProducto();
        ventaProducto.setProducto(producto);
        ventaProducto.setPrecioUnitario(producto.getPrecio());
        ventaProducto.setCantidad(cantidadSolicitada);
        
        tablaProductosModel.addRow(
            new Object[]{
                ventaProducto,
                ventaProducto.getCantidad(),
                ventaProducto.getPrecioUnitario(),
                ventaProducto.getSubTotal(),
                ventaProducto.calcularTotal(),
            }
        );
        
        cargarDatosFinales();
    }//GEN-LAST:event_AgregarProductoButtonActionPerformed

    private void cargarDatosFinales(){
        Vector<Vector> filas = tablaProductosModel.getDataVector();
        double total = 0;
        double subTotal = 0;
        double IGV = 0;
        
        for (Vector fila : filas) {
            VentaProducto ventaProducto = (VentaProducto) fila.firstElement();
            total += ventaProducto.getTotal();
        }
        
        TotalInput.setText(String.valueOf(total));
    }
    
    private void crearVentaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crearVentaButtonActionPerformed
        if(idCompra == null)
            crearNuevaVenta();
        else
            editarVenta();
    }//GEN-LAST:event_crearVentaButtonActionPerformed

    private void BuscarProveedorInputKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BuscarProveedorInputKeyTyped
        String nombreCliente = BuscarProveedorInput.getValue();
        List <Proveedor> clientesFiltrados = proveedores.stream()
                .filter(cliente -> cliente.toString().toLowerCase().contains(nombreCliente.toLowerCase()))
                .collect(Collectors.toList());
        
        ProveedorComboBoxModel.removeAllElements();
        ProveedorComboBoxModel.addElement("Seleccionar Cliente");
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

    private void crearNuevaVenta(){
        Venta venta = new Venta();
        Proveedor cliente;
        
        try {
            cliente = (Proveedor) ProveedoresComboBox.getSelectedItem();
        } catch (ClassCastException ex) {
            JOptionPane.showMessageDialog(null, "Elija un cliente para crear la venta");
            return;
        }
        
//        if(tablaProductosModel.getRowCount() == 0){
//            JOptionPane.showMessageDialog(null, "Agrege al menos un producto para crear la venta");
//            return;
//        };
//        
//        for(Vector fila : tablaProductosModel.getDataVector()){
//            VentaProducto ventaProducto = (VentaProducto) fila.firstElement();
//            venta.ventasProducto.add(ventaProducto);
//        };
//        
//        venta.setCliente(cliente);
//        
//        if(VentaControlador.crearVenta(venta)){
//            JOptionPane.showMessageDialog(null, "Registro guardado");
//            VentaPage.recagarTabla();
//            dispose();
//        } else {
//            JOptionPane.showMessageDialog(null, "Error al guardar");
//        }
    }
    
    private void editarVenta(){
        
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
