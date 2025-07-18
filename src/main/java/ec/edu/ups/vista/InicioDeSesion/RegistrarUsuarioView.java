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
        // Genera una lista de IDs del 1 al 10
        List<Integer> ids = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            ids.add(i);
        }

        // Obtiene los textos de las preguntas de seguridad desde el manejador de mensajes
        List<String> textos = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            textos.add(mensajeHandler.get("preguntas.seguridad." + i));
        }

        // Crea una lista de índices para mezclar
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < 10; i++) indices.add(i);
        Collections.shuffle(indices); // Mezcla los índices para seleccionar preguntas aleatorias

        // Limpia los JComboBox antes de añadir nuevos ítems
        if (comboPreguntasSeguridad1 != null) comboPreguntasSeguridad1.removeAllItems();
        if (comboPreguntasSeguridad2 != null) comboPreguntasSeguridad2.removeAllItems();
        if (comboPreguntasSeguridad3 != null) comboPreguntasSeguridad3.removeAllItems();

        // Selecciona 3 preguntas aleatorias usando los índices mezclados
        int idx1 = indices.get(0);
        int idx2 = indices.get(1);
        int idx3 = indices.get(2);

        // Añade las preguntas seleccionadas a los JComboBox
        if (comboPreguntasSeguridad1 != null) comboPreguntasSeguridad1.addItem(textos.get(idx1));
        if (comboPreguntasSeguridad2 != null) comboPreguntasSeguridad2.addItem(textos.get(idx2));
        if (comboPreguntasSeguridad3 != null) comboPreguntasSeguridad3.addItem(textos.get(idx3));

        // Guarda los IDs de las preguntas seleccionadas
        preguntasIdsSeleccionadas.clear();
        preguntasIdsSeleccionadas.add(ids.get(idx1));
        preguntasIdsSeleccionadas.add(ids.get(idx2));
        preguntasIdsSeleccionadas.add(ids.get(idx3));
    }

    public List<Integer> getPreguntasIdsSeleccionadas() {
        return preguntasIdsSeleccionadas;
    }

    public void actualizarIdioma(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;
        setTitle(mensajeHandler.get("usuario.registro"));
        if (NombresCompletos != null) NombresCompletos.setText(mensajeHandler.get("usuario.nombreCompleto") + ":");
        if (FechadeNaciemiento != null) FechadeNaciemiento.setText(mensajeHandler.get("usuario.fechaNacimiento") + ":");
        if (CorreoElectronico != null) CorreoElectronico.setText(mensajeHandler.get("usuario.correo") + ":");
        if (Celular != null) Celular.setText(mensajeHandler.get("usuario.telefono") + ":");
        if (NombreUsusario != null) NombreUsusario.setText(mensajeHandler.get("usuario.username") + ":");
        if (Contrasena != null) Contrasena.setText(mensajeHandler.get("usuario.contrasenia"));
        if (confContrasena != null) confContrasena.setText(mensajeHandler.get("usuario.confirmar"));
        if (PreguntasdeSeguridad != null) PreguntasdeSeguridad.setText(mensajeHandler.get("usuario.preguntasSeguridad"));
        if (lblDia != null) lblDia.setText(mensajeHandler.get("usuario.dia") + ":");
        if (lblMes != null) lblMes.setText(mensajeHandler.get("usuario.mes") + ":");
        if (lblAño != null) lblAño.setText(mensajeHandler.get("usuario.anio") + ":");
        if (registrarButton != null) registrarButton.setText(mensajeHandler.get("usuario.registrar"));
        if (cancelarButton != null) cancelarButton.setText(mensajeHandler.get("usuario.cancelar"));
        cargarPreguntas();
        SwingUtilities.updateComponentTreeUI(this);
    }


    public boolean validarCampos() {
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
