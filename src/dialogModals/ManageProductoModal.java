/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package dialogModals;

import controlador.CategoriaControlador;
import controlador.ProductoControlador;
import javax.swing.JOptionPane;
import modelo.Categoria;
import vista.dashboard.CategoriaPage;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import modelo.Producto;
import vista.dashboard.ProductoPage;

/**
 *
 * @author intel
 */
public class ManageProductoModal extends javax.swing.JDialog {

    /**
     * Creates new form AddCategoriaModal
     */
    public ManageProductoModal(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        cargarCategoriasEnCombobox();
    }
    
    private String idProducto;
    private int idCategoria = -1;
    
    public ManageProductoModal(java.awt.Frame parent, boolean modal, String id) {
        super(parent, modal);
        Producto producto = ProductoControlador.obtenerProducto(id);
        
        this.idProducto = id;
        this.idCategoria = producto.getCategoria().getId();
        
        initComponents();
        cargarCategoriasEnCombobox();
        
        jLabel1.setText("Editar Producto");
        nameInput.setValue(producto.getNombre());
        descripcionInput.setValue(producto.getDescripcion());
        marcaInput.setValue(producto.getMarca());
        stockInput.setValue(String.valueOf(producto.getStock()));
        precioInput.setValue(String.valueOf(producto.getPrecio()));
        
        jButton1.setText("Guardar");
        
    }

    private void cargarCategoriasEnCombobox() {
        CategoriaComboBoxModel.addElement("Seleccionar Categoría");
        List<Categoria> categorias = CategoriaControlador.obtenerCategorias();
        int indexSelected = 0;
        for (int i = 0; i < categorias.size(); i++) {
            CategoriaComboBoxModel.addElement(categorias.get(i));
            if(this.idCategoria == categorias.get(i).getId()){
                System.out.println(idCategoria + "  ->  "+categorias.get(i).getId());
                indexSelected = i+1; 
            }
        }
        CategoriaComboBox.setModel(CategoriaComboBoxModel);
        if(indexSelected >= 1){
            CategoriaComboBox.setSelectedIndex(indexSelected);
        }
            
    }
    
    private DefaultComboBoxModel CategoriaComboBoxModel = new DefaultComboBoxModel();
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        nameInput = new components.jInput();
        jLabel2 = new javax.swing.JLabel();
        descripcionInput = new components.jInput();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new components.jButton();
        jLabel4 = new javax.swing.JLabel();
        stockInput = new components.jInput();
        jLabel5 = new javax.swing.JLabel();
        marcaInput = new components.jInput();
        precioInput = new components.jInput();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        CategoriaComboBox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Crear Producto");

        nameInput.setText("Ingrese el nombre");

        jLabel2.setText("Nombre");

        descripcionInput.setText("Ingrese la descripción");

        jLabel3.setText("Descripcion");

        jButton1.setText("Agregar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setText("Stock");

        stockInput.setText("Ingrese el stock inicial");

        jLabel5.setText("Marca");

        marcaInput.setText("Ingrese la marca");

        precioInput.setText("Ingrese el precio");

        jLabel6.setText("Precio");

        jLabel7.setText("Categoria");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(nameInput, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)
                                    .addComponent(precioInput, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(38, 38, 38))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(descripcionInput, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7)
                            .addComponent(jLabel4)
                            .addComponent(stockInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5)
                            .addComponent(marcaInput, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                            .addComponent(CategoriaComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(29, Short.MAX_VALUE))
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameInput, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stockInput, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(43, 43, 43))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(marcaInput, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(descripcionInput, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(precioInput, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CategoriaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(43, 43, 43)))
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(idProducto != null)
            editarProducto();
        else
            crearNuevoProducto();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void crearNuevoProducto(){
        Producto producto = new Producto();

        String nombre = nameInput.getValue();
        String descripcion = descripcionInput.getValue();
        String marca = marcaInput.getValue();
        double precio;
        int stock;
        Categoria categoria;
        
        if(nombre.isEmpty() || descripcion.isEmpty()){
            JOptionPane.showMessageDialog(null, "Complete todos los campos");
            return;
        }

        try {
            precio = Double.parseDouble(precioInput.getValue());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "El campo precio debe ser un número");
            return;
        }
        
        try {
            stock = Integer.parseInt(stockInput.getValue());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "El campo stock debe ser un número entero");
            return;
        }
        
        try {
            categoria = (Categoria) CategoriaComboBox.getSelectedItem();
        } catch (ClassCastException ex) {
            JOptionPane.showMessageDialog(null, "Elija una categoria para el producto");
            return;
        }
        
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setMarca(marca);
        producto.setPrecio(precio);
        producto.setStock(stock);
        producto.setCategoria(categoria);

        if(ProductoControlador.crearProducto(producto)){
            JOptionPane.showMessageDialog(null, "Registro guardado");
            ProductoPage.recagarTabla();
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Error al guardar");
        }
    }
    
    private void editarProducto(){
        Producto producto = new Producto();

        String nombre = nameInput.getValue();
        String descripcion = descripcionInput.getValue();
        String marca = marcaInput.getValue();
        double precio;
        int stock;
        Categoria categoria;
        
        if(nombre.isEmpty() || descripcion.isEmpty()){
            JOptionPane.showMessageDialog(null, "Complete todos los campos");
            return;
        }

        try {
            precio = Double.parseDouble(precioInput.getValue());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "El campo precio debe ser un número");
            return;
        }
        
        try {
            stock = Integer.parseInt(stockInput.getValue());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "El campo stock debe ser un número entero");
            return;
        }
        
        try {
            categoria = (Categoria) CategoriaComboBox.getSelectedItem();
        } catch (ClassCastException ex) {
            JOptionPane.showMessageDialog(null, "Elija una categoria para el producto");
            return;
        }
        
        producto.setId(Integer.parseInt(idProducto));
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setMarca(marca);
        producto.setPrecio(precio);
        producto.setStock(stock);
        producto.setCategoria(categoria);

        if(ProductoControlador.actualizarProducto(producto)){
            JOptionPane.showMessageDialog(null, "Registro guardado");
            ProductoPage.recagarTabla();
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
                ManageProductoModal dialog = new ManageProductoModal(new javax.swing.JFrame(), true);
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
    private javax.swing.JComboBox<String> CategoriaComboBox;
    private components.jInput descripcionInput;
    private components.jButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private components.jInput marcaInput;
    private components.jInput nameInput;
    private components.jInput precioInput;
    private components.jInput stockInput;
    // End of variables declaration//GEN-END:variables
}