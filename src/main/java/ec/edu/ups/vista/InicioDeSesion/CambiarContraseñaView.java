package ec.edu.ups.vista.InicioDeSesion;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;

public class CambiarContraseñaView extends JInternalFrame {

    private JTextField textField1; // Campo para el usuario
    private JPasswordField passwordField1; // Campo para la nueva contraseña
    private JPasswordField passwordField2; // Campo para confirmar la nueva contraseña
    private JButton guardarButton;  // Botón para guardar la nueva contraseña
    private JButton cancelarButton; // Botón para cancelar
    private JPanel panelPrincipal;

    public CambiarContraseñaView() {
        setContentPane(panelPrincipal);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 400);
    }

    // Métodos para obtener los datos de los campos
    public String getUsuario() {
        return textField1.getText();
    }

    public String getNuevaContraseña() {
        return new String(passwordField1.getPassword());
    }

    public String getConfirmarContraseña() {
        return new String(passwordField2.getPassword());
    }

    // Métodos para los botones
    public JButton getGuardarButton() {
        return guardarButton;
    }

    public JButton getCancelarButton() {
        return cancelarButton;
    }

    // Método para actualizar los textos de la vista según el idioma
    public void actualizarIdioma(MensajeInternacionalizacionHandler mensajeHandler) {
        setTitle(mensajeHandler.get("usuario.view.cambiarContraseña.titulo"));
        guardarButton.setText(mensajeHandler.get("usuario.view.cambiarContraseña.guardar"));
        cancelarButton.setText(mensajeHandler.get("usuario.view.cambiarContraseña.cancelar"));
    }

    // Método para mostrar mensajes en la vista
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    // Método para limpiar los campos de la vista
    public void limpiarCampos() {
        textField1.setText("");
        passwordField1.setText("");
        passwordField2.setText("");
    }
}
