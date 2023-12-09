/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package dialogModals;

import controlador.RolControlador;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import modelo.Rol;
import controlador.CategoriaControlador;
import controlador.UsuarioControlador;
import modelo.Categoria;
import modelo.Usuario;
import utils.Validator;
import vista.dashboard.CategoriaPage;
import vista.dashboard.UsuarioPage;

/**
 *
 * @author intel
 */
public class ManageUsuarioModal extends javax.swing.JDialog {

    /**
     * Creates new form AddCategoriaModal
     */
    public ManageUsuarioModal(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        cargarRolesEnCombobox();
    }

    private DefaultComboBoxModel RolComboBoxModel = new DefaultComboBoxModel();

    private void cargarRolesEnCombobox() {
        RolComboBoxModel.addElement("Seleccionar Rol");
        List<Rol> roles = RolControlador.obtenerRoles();
        roles.forEach((rol) -> RolComboBoxModel.insertElementAt(rol, rol.getId()));
        RolComboBox.setModel(RolComboBoxModel);
    }

    private String idUsuario;

    public ManageUsuarioModal(java.awt.Frame parent, boolean modal, String id) {
        super(parent, modal);
        Usuario usuario = UsuarioControlador.obtenerUsuario(id);

        initComponents();
        cargarRolesEnCombobox();

        jLabel1.setText("Editar Usuario");
        nameInput.setValue(usuario.getNombre());
        apellidoInput.setValue(usuario.getApellido());
        dniInput.setValue(usuario.getDni());
        emailInput.setValue(usuario.getEmail());
        telefonoInput.setValue(usuario.getTelefono());

        if (!usuario.getEstado()) {
            InactivoRadioButton.setSelected(true);
        }

        RolComboBox.setSelectedIndex(usuario.getIdRol());

        jButton1.setText("Guardar");

        this.idUsuario = id;
        UsuarioPage.recagarTabla();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        estadoButtonGroup = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        nameInput = new components.jInput("Ingrese el nombre");
        jLabel2 = new javax.swing.JLabel();
        dniInput = new components.jInput("Ingrese el dni");
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new components.jButton();
        apellidoInput = new components.jInput("Ingrese el apellido");
        jLabel4 = new javax.swing.JLabel();
        emailInput = new components.jInput("Ingrese el email");
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        telefonoInput = new components.jInput("Ingrese el teléfono");
        jLabel7 = new javax.swing.JLabel();
        passwordInput = new components.jInput("Ingrese la contraseña");
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        RolComboBox = new javax.swing.JComboBox<>();
        jRadioButton1 = new javax.swing.JRadioButton();
        InactivoRadioButton = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Crear Usuario");

        nameInput.setText("Ingrese el nombre");

        jLabel2.setText("Nombre");

        dniInput.setText("Ingrese el dni");

        jLabel3.setText("Dni");

        jButton1.setText("Agregar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        apellidoInput.setText("Ingrese el apellido");

        jLabel4.setText("Apellido");

        emailInput.setText("Ingrese el email");

        jLabel5.setText("Email");

        jLabel6.setText("Estado");

        telefonoInput.setText("Ingrese el teléfono");

        jLabel7.setText("Teléfono");

        passwordInput.setText("Ingrese la contraseña");

        jLabel8.setText("Contraseña");

        jLabel9.setText("Rol");

        estadoButtonGroup.add(jRadioButton1);
        jRadioButton1.setActionCommand("Activo");
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Activo");

        estadoButtonGroup.add(InactivoRadioButton);
        InactivoRadioButton.setActionCommand("Inactivo");
        InactivoRadioButton.setText("Inactivo");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(nameInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dniInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8)
                            .addComponent(passwordInput, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jRadioButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(InactivoRadioButton)
                                .addGap(39, 39, 39)))
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9)
                            .addComponent(jLabel4)
                            .addComponent(apellidoInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5)
                            .addComponent(emailInput, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                            .addComponent(jLabel7)
                            .addComponent(telefonoInput, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                            .addComponent(RolComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(21, 21, 21)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameInput, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(apellidoInput, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dniInput, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(emailInput, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(telefonoInput, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(passwordInput, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(RolComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(InactivoRadioButton)))
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (idUsuario != null)
            editarUsuario();
        else
            crearNuevoUsuario();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void crearNuevoUsuario() {
        try {
            Usuario usuario = new Usuario();

            // Obtiene los valores ingresados en los campos de texto
            String nombre = nameInput.getValue();
            String apellido = apellidoInput.getValue();
            String dni = dniInput.getValue();
            String email = emailInput.getValue();
            String contraseña = passwordInput.getValue();
            String telefono = telefonoInput.getValue();
            boolean estado = estadoButtonGroup.getSelection().getActionCommand().equals("Activo");

            // Verifica si ambos campos de texto no están vacíos
            if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty() || email.isEmpty() || contraseña.isEmpty() || telefono.isEmpty()) {
                throw new Exception("Complete todos los campos");
            }

            Rol rol;

            try {
                rol = (Rol) RolComboBox.getSelectedItem();
            } catch (ClassCastException ex) {
                JOptionPane.showMessageDialog(null, "Elija un rol para el usuario");
                return;
            }

            Validator.DNI(dni);
            Validator.EMAIL(email);
            Validator.TELEFONO(telefono);

            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setDni(dni);
            usuario.setEmail(email);
            usuario.setPassword(contraseña);
            usuario.setTelefono(telefono);
            usuario.setEstado(estado);
            usuario.setRol(rol);

            // Intenta crear una nueva categoría utilizando el controlador de categorías
            if (UsuarioControlador.crearUsuario(usuario)) {
                // Si la categoría se crea con éxito, muestra un mensaje de éxito
                JOptionPane.showMessageDialog(null, "Registro guardado");
                UsuarioPage.recagarTabla();
                dispose();
            } else {
                // Si hay un error al crear la categoría, muestra un mensaje de error
                JOptionPane.showMessageDialog(null, "Error al guardar");
            }

        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }

    private void editarUsuario() {
        try {
            Usuario usuario = new Usuario();
            String nombre = nameInput.getValue();
            String apellido = apellidoInput.getValue();
            String dni = dniInput.getValue();
            String email = emailInput.getValue();
            String contraseña = passwordInput.getValue();
            String telefono = telefonoInput.getValue();
            boolean estado = estadoButtonGroup.getSelection().getActionCommand().equals("Activo");

            // Verifica si ambos campos de texto no están vacíos
            if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty() || email.isEmpty() || telefono.isEmpty()) {
                throw new Exception("Complete todos los campos");
            }

            Rol rol;

            try {
                rol = (Rol) RolComboBox.getSelectedItem();
            } catch (ClassCastException ex) {
                JOptionPane.showMessageDialog(null, "Elija un rol para el usuario");
                return;
            }

            usuario.setId(idUsuario);
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setDni(dni);
            usuario.setEmail(email);
            usuario.setPassword(contraseña);
            usuario.setTelefono(telefono);
            usuario.setEstado(estado);
            usuario.setRol(rol);

            // Intenta crear una nueva categoría utilizando el controlador de categorías
            if (UsuarioControlador.actualizarUsuario(usuario)) {
                // Si la categoría se crea con éxito, muestra un mensaje de éxito
                JOptionPane.showMessageDialog(null, "Registro guardado");
                UsuarioPage.recagarTabla();
                dispose();
            } else {
                // Si hay un error al crear la categoría, muestra un mensaje de error
                JOptionPane.showMessageDialog(null, "Error al guardar");
            }
        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ManageUsuarioModal dialog = new ManageUsuarioModal(new javax.swing.JFrame(), true);
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
    private javax.swing.JRadioButton InactivoRadioButton;
    private javax.swing.JComboBox<String> RolComboBox;
    private components.jInput apellidoInput;
    private components.jInput dniInput;
    private components.jInput emailInput;
    private javax.swing.ButtonGroup estadoButtonGroup;
    private components.jButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JRadioButton jRadioButton1;
    private components.jInput nameInput;
    private components.jInput passwordInput;
    private components.jInput telefonoInput;
    // End of variables declaration//GEN-END:variables
}
