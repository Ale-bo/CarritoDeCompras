package ec.edu.ups.vista.InicioDeSesion;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class RegistrarUsuarioView extends JFrame {

    private MensajeInternacionalizacionHandler mensajeHandler;
    private JLabel confContrasena, Contrasena, NombreUsusario, Celular, CorreoElectronico, FechadeNaciemiento, NombresCompletos, PreguntasdeSeguridad;
    private JTextField textnombre, textcorreo, textcelular, textususario;
    private JPasswordField passwordcontrasena, passwordconfcontrasena;
    private JButton registrarButton, cancelarButton;
    private JTextField textDia, textMes, textAnio;
    private JLabel lblDia, lblMes, lblAño;
    private JTextField textRespuesta1, textRespuesta2, textFRespuesta3;
    public JComboBox<String> comboPreguntasSeguridad1, comboPreguntasSeguridad2, comboPreguntasSeguridad3;
    private JPanel panelPrincipal;
    private JPanel panel;
    private final List<Integer> preguntasIdsSeleccionadas = new ArrayList<>();

    public RegistrarUsuarioView() {
        this.mensajeHandler = new MensajeInternacionalizacionHandler("es", "EC");
        setTitle("Registro de Usuario");
        setContentPane(panelPrincipal);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cargarPreguntas();
    }

    private void cargarPreguntas() {
        // ... (código sin cambios)
    }

    public List<Integer> getPreguntasIdsSeleccionadas() {
        return preguntasIdsSeleccionadas;
    }

    public void actualizarIdioma(MensajeInternacionalizacionHandler mensajeHandler) {
        // ... (código sin cambios)
    }

    // --- NUEVO MÉTODO DE VALIDACIÓN ---
    public boolean validarCampos() {
        // Validación de campos vacíos
        if (getTextnombre().getText().trim().isEmpty() ||
                getTextcorreo().getText().trim().isEmpty() ||
                getTextcelular().getText().trim().isEmpty() ||
                getTextususario().getText().trim().isEmpty() ||
                new String(getPasswordcontrasena().getPassword()).isEmpty() ||
                getRespuesta1().trim().isEmpty() || getRespuesta2().trim().isEmpty() || getRespuesta3().trim().isEmpty()) {
            mostrarMensaje("Todos los campos son obligatorios.");
            return false;
        }

        // Validación de formato de correo electrónico
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!Pattern.matches(emailRegex, getTextcorreo().getText().trim())) {
            mostrarMensaje("El formato del correo electrónico no es válido.");
            return false;
        }

        // Validación de teléfono (solo números)
        if (!getTextcelular().getText().trim().matches("\\d+")) {
            mostrarMensaje("El teléfono solo debe contener números.");
            return false;
        }

        // Validación de contraseñas coincidentes
        String p1 = new String(getPasswordcontrasena().getPassword());
        String p2 = new String(getPasswordconfcontrasena().getPassword());
        if (!p1.equals(p2)) {
            mostrarMensaje("Las contraseñas no coinciden.");
            return false;
        }

        return true; // Si todo es correcto
    }

    // Getters
    public JButton getCancelarButton() { return cancelarButton; }
    public JButton getRegistrarButton() { return registrarButton; }
    public JPasswordField getPasswordconfcontrasena() { return passwordconfcontrasena; }
    public JPasswordField getPasswordcontrasena() { return passwordcontrasena; }
    public JTextField getTextcelular() { return textcelular; }
    public JTextField getTextcorreo() { return textcorreo; }
    public JTextField getTextnombre() { return textnombre; }
    public JTextField getTextususario() { return textususario; }
    public JTextField getTextAnio() { return textAnio; }
    public JTextField getTextDia() { return textDia; }
    public JTextField getTextMes() { return textMes; }
    public String getRespuesta1() { return textRespuesta1.getText(); }
    public String getRespuesta2() { return textRespuesta2.getText(); }
    public String getRespuesta3() { return textFRespuesta3.getText(); }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void limpiarCampos() {
        textnombre.setText("");
        textcorreo.setText("");
        textcelular.setText("");
        textususario.setText("");
        passwordcontrasena.setText("");
        passwordconfcontrasena.setText("");
        textDia.setText("");
        textAnio.setText("");
        textMes.setText("");
        textRespuesta1.setText("");
        textRespuesta2.setText("");
        textFRespuesta3.setText("");
    }
}
