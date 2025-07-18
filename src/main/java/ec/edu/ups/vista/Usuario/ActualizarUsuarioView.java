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

    public ActualizarUsuarioView(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;

        setContentPane(panelPrincipal);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();

        model = (DefaultTableModel) tblUsuarios.getModel();
        actualizarIdioma();
    }

    public void actualizarIdioma() {
        if (mensajeHandler == null) return;
        setTitle(mensajeHandler.get("usuario.view.modificar.titulo"));
        NombreDeUsuario.setText(mensajeHandler.get("usuario.view.cambiarContraseña.usuario"));
        Contraseña.setText(mensajeHandler.get("usuario.view.cambiarContraseña.nueva"));
        ConfContra.setText(mensajeHandler.get("usuario.view.cambiarContraseña.confirmar"));
        btnBuscar.setText(mensajeHandler.get("usuario.view.modificar.buscar"));
        btnActualizar.setText(mensajeHandler.get("usuario.view.modificar.actualizar"));
        btnCancelar.setText(mensajeHandler.get("usuario.view.modificar.cancelar"));

        model.setColumnIdentifiers(new Object[]{"Username", "Rol"});
    }

    public JTextField getTxtUsername() { return txtUsername; }
    public JPasswordField getTxtPassword() { return txtPassword; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnActualizar() { return btnActualizar; }
    public JButton getBtnCancelar() { return btnCancelar; }
    public JTable getTblUsuarios() { return tblUsuarios; }
    public DefaultTableModel getTableModel() { return model; }
    public JPasswordField getPasswordconfcontrasenia() { return passwordconfcontrasenia; }

    public void setTableModel(DefaultTableModel model) {
        this.model = model;
        tblUsuarios.setModel(model);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void limpiarCampos() {
        txtUsername.setText("");
        txtPassword.setText("");
        passwordconfcontrasenia.setText("");
        tblUsuarios.clearSelection();
    }
}
