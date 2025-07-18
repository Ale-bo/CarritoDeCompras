package ec.edu.ups.vista.InicioDeSesion;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.excepciones.ValidacionException; // Asegúrate de que esta importación exista

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
    public JComboBox<String> comboPreguntasSeguridad1, comboPreguntasSeguridad2, comboPregomasSeguridad3;
    private JPanel panelPrincipal;
    private JPanel panel;
    private JComboBox<String> comboPreguntasSeguridad3;
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
        List<Integer> ids = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            ids.add(i);
        }

        List<String> textos = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            textos.add(mensajeHandler.get("preguntas.seguridad." + i));
        }

        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < 10; i++) indices.add(i);
        Collections.shuffle(indices);

        if (comboPreguntasSeguridad1 != null) comboPreguntasSeguridad1.removeAllItems();
        if (comboPreguntasSeguridad2 != null) comboPreguntasSeguridad2.removeAllItems();
        if (comboPreguntasSeguridad3 != null) comboPreguntasSeguridad3.removeAllItems();

        int idx1 = indices.get(0);
        int idx2 = indices.get(1);
        int idx3 = indices.get(2);

        if (comboPreguntasSeguridad1 != null) comboPreguntasSeguridad1.addItem(textos.get(idx1));
        if (comboPreguntasSeguridad2 != null) comboPreguntasSeguridad2.addItem(textos.get(idx2));
        if (comboPreguntasSeguridad3 != null) comboPreguntasSeguridad3.addItem(textos.get(idx3));

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

    // --- MÉTODO DE VALIDACIÓN MODIFICADO PARA LANZAR ValidacionException y mejorar fecha ---
    public boolean validarCampos() throws ValidacionException {
        // Validación de campos vacíos (todos los JTextFields y PasswordFields)
        if (textnombre.getText().trim().isEmpty() ||
                textcorreo.getText().trim().isEmpty() ||
                textcelular.getText().trim().isEmpty() ||
                textususario.getText().trim().isEmpty() ||
                new String(passwordcontrasena.getPassword()).isEmpty() ||
                new String(passwordconfcontrasena.getPassword()).isEmpty() ||
                textRespuesta1.getText().trim().isEmpty() ||
                textRespuesta2.getText().trim().isEmpty() ||
                textFRespuesta3.getText().trim().isEmpty()) {
            throw new ValidacionException("Todos los campos de texto y respuestas son obligatorios.");
        }

        // Validación de formato de correo electrónico
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!Pattern.matches(emailRegex, textcorreo.getText().trim())) {
            throw new ValidacionException("El formato del correo electrónico no es válido.");
        }

        // Validación de teléfono (solo números)
        if (!textcelular.getText().trim().matches("\\d+")) {
            throw new ValidacionException("El teléfono solo debe contener números.");
        }

        // Validación de contraseñas coincidentes
        String p1 = new String(passwordcontrasena.getPassword());
        String p2 = new String(passwordconfcontrasena.getPassword());
        if (!p1.equals(p2)) {
            throw new ValidacionException("Las contraseñas no coinciden.");
        }

        // Validación de campos de fecha de nacimiento (que no estén vacíos)
        if (textDia.getText().trim().isEmpty() || textMes.getText().trim().isEmpty() || textAnio.getText().trim().isEmpty()) {
            throw new ValidacionException("La fecha de nacimiento es obligatoria (Día, Mes, Año).");
        }

        // Validación de tipos de datos y rangos para la fecha de nacimiento
        try {
            int dia = Integer.parseInt(textDia.getText().trim());
            int mes = Integer.parseInt(textMes.getText().trim());
            int anio = Integer.parseInt(textAnio.getText().trim());

            if (dia < 1 || dia > 31) {
                throw new ValidacionException("El día de nacimiento debe ser un número entre 1 y 31.");
            }
            if (mes < 1 || mes > 12) {
                throw new ValidacionException("El mes de nacimiento debe ser un número entre 1 y 12.");
            }
            if (anio < 1900 || anio > 2025) { // Ajusta este rango de años según tu necesidad
                throw new ValidacionException("El año de nacimiento debe ser un número entre 1900 y 2025.");
            }

            // Validación de días según el mes (más robusta)
            int[] diasPorMes = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; // Índice 0 no usado, Febrero 28 por defecto

            // Ajuste para años bisiestos en Febrero
            if (mes == 2 && ((anio % 4 == 0 && anio % 100 != 0) || (anio % 400 == 0))) {
                diasPorMes[2] = 29; // Año bisiesto, Febrero tiene 29 días
            }

            if (dia > diasPorMes[mes]) {
                throw new ValidacionException("El día de nacimiento no es válido para el mes seleccionado.");
            }

        } catch (NumberFormatException e) {
            throw new ValidacionException("Día, Mes y Año de nacimiento deben ser números válidos.");
        }


        return true; // Si todo es correcto
    }

    // Getters (asegúrate de que los JComboBox de preguntas de seguridad estén correctamente enlazados)
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
        // Asegúrate de que los JComboBox de preguntas de seguridad se reinicien si es necesario
        cargarPreguntas();
    }
}