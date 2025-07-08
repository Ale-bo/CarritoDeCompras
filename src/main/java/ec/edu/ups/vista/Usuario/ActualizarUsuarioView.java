package ec.edu.ups.vista.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ActualizarUsuarioView extends JInternalFrame {

    private MensajeInternacionalizacionHandler mensajeHandler;

    private JPanel panelPrincipal;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cbxRol;
    private JButton btnBuscar;
    private JButton btnActualizar;
    private JButton btnCancelar;
    private JTable tblUsuarios;
    private DefaultTableModel model;
    private JScrollPane scrollPane;
    private JLabel NombreDeUsuario;
    private JLabel Contraseña;
    private JPasswordField passwordconfcontrasenia;
    private JLabel ConfContra;
    private JLabel lblContraseña;
    private JLabel lblNombreDeUsuario;

    public ActualizarUsuarioView(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;

        setContentPane(panelPrincipal);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 400);

        model = new DefaultTableModel(new Object[]{"Username", "Rol"}, 0);
        tblUsuarios.setModel(model);
    }

    // Método para actualizar los textos y etiquetas de la interfaz con los valores en el idioma seleccionado
    public void actualizarIdioma() {
        if (mensajeHandler == null) return;

        setTitle(mensajeHandler.get("usuario.view.modificar.titulo"));

        if (btnBuscar != null) btnBuscar.setText(mensajeHandler.get("usuario.view.modificar.buscar"));
        if (btnActualizar != null) btnActualizar.setText(mensajeHandler.get("usuario.view.modificar.actualizar"));
        if (btnCancelar != null) btnCancelar.setText(mensajeHandler.get("usuario.view.modificar.cancelar"));

        if (cbxRol != null) {
            cbxRol.removeAllItems();
            cbxRol.addItem(mensajeHandler.get("usuario.view.modificar.rol.admin"));
            cbxRol.addItem(mensajeHandler.get("usuario.view.modificar.rol.usuario"));
        }
    }

    // Métodos para obtener los valores de los campos
    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public JPasswordField getTxtPassword() {
        return txtPassword;
    }

    public JComboBox<String> getCbxRol() {
        return cbxRol;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public JTable getTblUsuarios() {
        return tblUsuarios;
    }

    public DefaultTableModel getTableModel() {
        return model;
    }

    public JPasswordField getPasswordconfcontrasenia() {
        return passwordconfcontrasenia;
    }


    public void setTableModel(DefaultTableModel model) {
        this.model = model;
        tblUsuarios.setModel(model);
    }

    // Método para mostrar un mensaje en la vista
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    // Método para limpiar los campos de la vista
    public void limpiarCampos() {
        txtUsername.setText("");
        txtPassword.setText("");
        cbxRol.setSelectedIndex(0);
    }
}
