/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package dialogModals;

import components.ManageButtonCellRenderer;
import controlador.CategoriaControlador;
import javax.swing.JOptionPane;
import modelo.Categoria;
import vista.dashboard.CategoriaPage;

/**
 *
 * @author intel
 */
public class ManageCategoriaModal extends javax.swing.JDialog {

    /**
     * Creates new form AddCategoriaModal
     * @param parent
     * @param modal
     */
    public ManageCategoriaModal(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    private String idCategoria;
    
    public ManageCategoriaModal(java.awt.Frame parent, boolean modal, String id) {
        super(parent, modal);
        Categoria categoria = CategoriaControlador.obtenerCategoria(id);
        
        initComponents();
        
        jLabel1.setText("Editar Categoría");
        nameInput.setValue(categoria.getNombre());
        descripcionInput.setValue(categoria.getDescripcion());
        
        jButton1.setText("Guardar");
        
        this.idCategoria = id;
        CategoriaPage.recagarTabla();
        
    }
    
    public ManageCategoriaModal(java.awt.Frame parent, boolean modal, String id, boolean view) {
        super(parent, modal);
        Categoria categoria = CategoriaControlador.obtenerCategoria(id);
        
        initComponents();
        
        jLabel1.setText("Ver Categoría");
        nameInput.setValue(categoria.getNombre());
        descripcionInput.setValue(categoria.getDescripcion());
        
        jButton1.setVisible(false);
        
        nameInput.setEnabled(false);
        descripcionInput.setEnabled(false);
        
        this.idCategoria = id;
        CategoriaPage.recagarTabla();
        
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
        nameInput = new components.jInput("Ingrese el nombre");
        jLabel2 = new javax.swing.JLabel();
        descripcionInput = new components.jInput("Ingrese la descripción");
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new components.jButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Crear Categoria");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(nameInput, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(descripcionInput, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(27, Short.MAX_VALUE))
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addGap(21, 21, 21)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nameInput, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(descripcionInput, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(idCategoria != null)
            editarCategoria();
        else
            crearNuevaCategoria();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void crearNuevaCategoria(){
        Categoria categoria = new Categoria();
        
        String nombre = nameInput.getValue();
        String descripcion = descripcionInput.getValue();
        
        if(!nombre.isEmpty() && !descripcion.isEmpty()){
            
            categoria.setNombre(nombre);
            categoria.setDescripcion(descripcion);

            // Intenta crear una nueva categoría utilizando el controlador de categorías
            if(CategoriaControlador.crearCategoria(categoria)){
                // Si la categoría se crea con éxito, muestra un mensaje de éxito
                JOptionPane.showMessageDialog(null, "Registro guardado");
                CategoriaPage.recagarTabla();
                dispose();
            } else {
                // Si hay un error al crear la categoría, muestra un mensaje de error
                JOptionPane.showMessageDialog(null, "Error al guardar");
            }
        } else {
            // Si alguno de los campos está vacío, muestra un mensaje pidiendo completar todos los campos
            JOptionPane.showMessageDialog(null, "Complete todos los campos");
        }
    }
    
    private void editarCategoria(){
        Categoria categoria = new Categoria();

        String nombre = nameInput.getValue();
        String descripcion = descripcionInput.getValue();

        if(!nombre.isEmpty() && !descripcion.isEmpty()){
            categoria.setNombre(nombre);
            categoria.setDescripcion(descripcion);
            categoria.setId(Integer.parseInt(idCategoria));
            

            if(CategoriaControlador.actualizarCategoria(categoria)){
                JOptionPane.showMessageDialog(null, "Registro actualizado");
                CategoriaPage.recagarTabla();
                dispose();
            } else {
                // Si hay un error al crear la categoría, muestra un mensaje de error
                JOptionPane.showMessageDialog(null, "Error al guardar");
            }
        } else {
            // Si alguno de los campos está vacío, muestra un mensaje pidiendo completar todos los campos
            JOptionPane.showMessageDialog(null, "Complete todos los campos");
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ManageCategoriaModal dialog = new ManageCategoriaModal(new javax.swing.JFrame(), true);
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
    private components.jInput descripcionInput;
    private components.jButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private components.jInput nameInput;
    // End of variables declaration//GEN-END:variables
}
