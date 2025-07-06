package ec.edu.ups.vista.InicioDeSesion;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;

public class RecuperarContraseñaView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JTextField textField1; // Respuesta a la primera pregunta de seguridad
    private JTextField textField2; // Respuesta a la segunda pregunta de seguridad
    private JTextField textField3; // Respuesta a la tercera pregunta de seguridad
    private JTextField textField4; // Respuesta a la cuarta pregunta de seguridad
    private JTextField textField5; // Respuesta a la quinta pregunta de seguridad
    private JButton aceptarButton;  // Botón para aceptar y verificar las respuestas
    private JButton cancelarButton; // Botón para cancelar y salir
    private JPasswordField passwordField1;
    private JButton cambiarButton;
    private JButton cancelarButton1;
    private JLabel NuevaContra;

    // Constructor
    public RecuperarContraseñaView() {
        // Inicializar el panelPrincipal manualmente
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));  // O el layout que desees

        // Ahora puedes inicializar los otros componentes y agregarlos a panelPrincipal
        // Ejemplo: panelPrincipal.add(aceptarButton);

        setContentPane(panelPrincipal);  // Establecer el panel principal como el contenido de la ventana
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 400);
    }

    // Métodos para acceder a los campos de texto (respuestas)
    public JTextField getTextField1() {
        return textField1;
    }

    public JTextField getTextField2() {
        return textField2;
    }

    public JTextField getTextField3() {
        return textField3;
    }

    public JTextField getTextField4() {
        return textField4;
    }

    public JTextField getTextField5() {
        return textField5;
    }

    // Métodos para los botones
    public JButton getAceptarButton() {
        return aceptarButton;
    }

    public JButton getCancelarButton() {
        return cancelarButton;
    }

    public JButton getCambiarButton() {
        return cambiarButton;
    }

    public JButton getCancelarButton1() {
        return cancelarButton1;
    }
    public JPasswordField getPasswordField1() {
        return passwordField1;
    }
    public JLabel getNuevaContra() {
        return NuevaContra;
    }


    // Método para actualizar el idioma de la vista
    public void actualizarIdioma(MensajeInternacionalizacionHandler mensajeHandler) {
        setTitle(mensajeHandler.get("usuario.view.recuperar.titulo"));
        aceptarButton.setText(mensajeHandler.get("usuario.view.recuperar.aceptar"));
        cancelarButton.setText(mensajeHandler.get("usuario.view.recuperar.cancelar"));
    }

    // Método para mostrar un mensaje en la vista
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    // Método para limpiar los campos de respuesta
    public void limpiarCampos() {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        textField5.setText("");
    }
}

