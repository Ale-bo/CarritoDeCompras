package ec.edu.ups.vista.Usuario;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.excepciones.ValidacionException; // Importar la nueva excepción

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern; // Necesario para la validación de correo electrónico

public class RegistroView extends JInternalFrame {

    private final MensajeInternacionalizacionHandler mensajeHandler;

    private JPanel panelPrincipal;
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JLabel lblUsuario;
    private JLabel lblPassword;
    private JButton btnCrear;
    private JButton btnCancelar;
    private JComboBox<String> cbxPregunta1;
    private JComboBox<String> cbxPregunta2;
    private JComboBox<String> cbxPregunta3;
    private JTextField txtNombresComp;
    private JTextField txtTelefono;
    private JTextField txtCorreo;
    private JTextField txtRespuesta1;
    private JTextField txtRespuesta2;
    private JTextField txtRespuesta3;
    private JLabel lblPregunta1;
    private JLabel lblPregunta2;
    private JLabel lblPregunta3;
    private JLabel lblPreguntas;
    private JLabel lblNombresComp;
    private JLabel lblTelefono;
    private JLabel lblCorreo;
    private JLabel lblDia;
    private JLabel lblMes;
    private JLabel lblAño;
    private JSpinner spnDia; // Asumiendo que es un JSpinner
    private JSpinner spnMes; // Asumiendo que es un JSpinner
    private JSpinner spnAño; // Asumiendo que es un JSpinner
    private JPasswordField txtConfirmarPassword; // Añadir este campo si tienes un password de confirmación en el .form

    // Campo para IDs de preguntas seleccionadas
    private final List<Integer> preguntasIdsSeleccionadas = new ArrayList<>();

    public RegistroView(MensajeInternacionalizacionHandler mensajeHandler) {
        super(mensajeHandler.get("usuario.view.registrar.titulo"));
        this.mensajeHandler = mensajeHandler;
        setSize(900, 900);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setResizable(false);
        setClosable(true); // Para que aparezca la 'X' de cerrar
        setIconifiable(true);
        setResizable(true);

        // Inicializar Spinners con modelos si no lo haces en el .form
        if (spnDia != null) {
            spnDia.setModel(new SpinnerNumberModel(1, 1, 31, 1));
        }
        if (spnMes != null) {
            spnMes.setModel(new SpinnerNumberModel(1, 1, 12, 1));
        }
        if (spnAño != null) {
            spnAño.setModel(new SpinnerNumberModel(2000, 1900, 2025, 1)); // Ajusta los años según necesidad
        }

        actualizarIdioma();
        cargarPreguntas();
    }

    public void actualizarIdioma() {
        setTitle(mensajeHandler.get("usuario.view.registrar.titulo"));
        if (lblUsuario != null) lblUsuario.setText(mensajeHandler.get("usuario.view.registrar.usuario") + ":");
        if (lblPassword != null) lblPassword.setText(mensajeHandler.get("usuario.view.registrar.password") + ":");
        if (lblPreguntas != null) lblPreguntas.setText(mensajeHandler.get("usuario.view.registrar.preguntas"));
        if (lblPregunta1 != null) lblPregunta1.setText(mensajeHandler.get("usuario.view.registrar.pregunta1"));
        if (lblPregunta2 != null) lblPregunta2.setText(mensajeHandler.get("usuario.view.registrar.pregunta2"));
        if (lblPregunta3 != null) lblPregunta3.setText(mensajeHandler.get("usuario.view.registrar.pregunta3"));
        if (lblNombresComp != null) lblNombresComp.setText(mensajeHandler.get("usuario.view.registrar.nombres") + ":");
        if (lblTelefono != null) lblTelefono.setText(mensajeHandler.get("usuario.view.registrar.telefono") + ":");
        if (lblCorreo != null) lblCorreo.setText(mensajeHandler.get("usuario.view.registrar.correo") + ":");
        if (lblDia != null) lblDia.setText(mensajeHandler.get("usuario.view.registrar.dia") + ":");
        if (lblMes != null) lblMes.setText(mensajeHandler.get("usuario.view.registrar.mes") + ":");
        if (lblAño != null) lblAño.setText(mensajeHandler.get("usuario.view.registrar.ano") + ":");
        if (btnCrear != null) btnCrear.setText(mensajeHandler.get("usuario.view.registrar.btn.crear"));
        if (btnCancelar != null) btnCancelar.setText(mensajeHandler.get("usuario.view.registrar.btn.cancelar"));

        cargarPreguntas(); // Vuelve a cargar las preguntas con el nuevo idioma
        SwingUtilities.updateComponentTreeUI(this); // Fuerza la actualización de la UI
    }

    private void cargarPreguntas() {
        List<Integer> ids = new ArrayList<>();
        List<String> textos = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            ids.add(i);
            textos.add(mensajeHandler.get("preguntas.seguridad." + i));
        }
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < 10; i++) indices.add(i);
        Collections.shuffle(indices);

        if (cbxPregunta1 != null) cbxPregunta1.removeAllItems();
        if (cbxPregunta2 != null) cbxPregunta2.removeAllItems();
        if (cbxPregunta3 != null) cbxPregunta3.removeAllItems();

        int idx1 = indices.get(0);
        int idx2 = indices.get(1);
        int idx3 = indices.get(2);

        if (cbxPregunta1 != null) cbxPregunta1.addItem(textos.get(idx1));
        if (cbxPregunta2 != null) cbxPregunta2.addItem(textos.get(idx2));
        if (cbxPregunta3 != null) cbxPregunta3.addItem(textos.get(idx3));

        preguntasIdsSeleccionadas.clear();
        preguntasIdsSeleccionadas.add(ids.get(idx1));
        preguntasIdsSeleccionadas.add(ids.get(idx2));
        preguntasIdsSeleccionadas.add(ids.get(idx3));
    }

    public List<Integer> getPreguntasIdsSeleccionadas() {
        return preguntasIdsSeleccionadas;
    }

    // --- MÉTODO DE VALIDACIÓN MODIFICADO PARA LANZAR ValidacionException ---
    public boolean validarCampos() throws ValidacionException {
        // Validación de campos vacíos
        if (txtNombresComp.getText().trim().isEmpty() ||
                txtCorreo.getText().trim().isEmpty() ||
                txtTelefono.getText().trim().isEmpty() ||
                txtUsuario.getText().trim().isEmpty() ||
                new String(txtPassword.getPassword()).isEmpty() ||
                (txtConfirmarPassword != null && new String(txtConfirmarPassword.getPassword()).isEmpty()) || // Considerar si txtConfirmarPassword es null
                txtRespuesta1.getText().trim().isEmpty() ||
                txtRespuesta2.getText().trim().isEmpty() ||
                txtRespuesta3.getText().trim().isEmpty()) {
            throw new ValidacionException("Todos los campos son obligatorios.");
        }

        // Validación de formato de correo electrónico
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!Pattern.matches(emailRegex, txtCorreo.getText().trim())) {
            throw new ValidacionException("El formato del correo electrónico no es válido.");
        }

        // Validación de teléfono (solo números)
        if (!txtTelefono.getText().trim().matches("\\d+")) {
            throw new ValidacionException("El teléfono solo debe contener números.");
        }

        // Validación de contraseñas coincidentes
        String p1 = new String(txtPassword.getPassword());
        String p2 = (txtConfirmarPassword != null) ? new String(txtConfirmarPassword.getPassword()) : "";
        if (!p1.equals(p2)) {
            throw new ValidacionException("Las contraseñas no coinciden.");
        }

        // Validación de fecha de nacimiento (simplificada, solo que no estén vacíos los spinners)
        if (spnDia.getValue() == null || spnMes.getValue() == null || spnAño.getValue() == null) {
            throw new ValidacionException("La fecha de nacimiento es obligatoria.");
        }

        // Puedes añadir aquí validaciones de rango de fechas si lo necesitas
        try {
            int dia = (Integer) spnDia.getValue();
            int mes = (Integer) spnMes.getValue();
            int anio = (Integer) spnAño.getValue();
            // Comprobación básica de fecha (ej. no día 31 en febrero)
            if (mes == 2 && dia > 29) { // Febrero tiene máximo 29 días en bisiesto
                throw new ValidacionException("Fecha de nacimiento inválida para Febrero.");
            } else if ((mes == 4 || mes == 6 || mes == 9 || mes == 11) && dia > 30) { // Meses de 30 días
                throw new ValidacionException("Fecha de nacimiento inválida para el mes seleccionado.");
            }
            // Puedes añadir más lógica para años bisiestos si es muy crítico.

        } catch (ClassCastException | NullPointerException e) {
            throw new ValidacionException("Formato de fecha de nacimiento incorrecto.");
        }


        return true; // Si todo es correcto
    }

    // Métodos getters
    public String getUsuario() { return txtUsuario.getText(); }
    public String getPassword() { return new String(txtPassword.getPassword()); }
    public String getRespuesta1() { return txtRespuesta1.getText(); }
    public MensajeInternacionalizacionHandler getMensajeHandler() { return mensajeHandler; }
    public JTextField getTxtUsuario() { return txtUsuario; }
    public JPasswordField getTxtPassword() { return txtPassword; }
    public JButton getBtnCrear() { return btnCrear; }
    public JButton getBtnCancelar() { return btnCancelar; }
    public JComboBox<String> getCbxPregunta1() { return cbxPregunta1; }
    public JComboBox<String> getCbxPregunta2() { return cbxPregunta2; }
    public JComboBox<String> getCbxPregunta3() { return cbxPregunta3; }
    public JTextField getTxtRespuesta1() { return txtRespuesta1; }
    public JTextField getTxtRespuesta2() { return txtRespuesta2; }
    public JTextField getTxtRespuesta3() { return txtRespuesta3; }
    public JTextField getTxtCorreo() { return txtCorreo; }
    public JTextField getTxtTelefono() { return txtTelefono; }
    public JTextField getTxtNombresComp() { return txtNombresComp; }
    public JSpinner getSpnDia() { return spnDia; }
    public JSpinner getSpnMes() { return spnMes; }
    public JSpinner getSpnAño() { return spnAño; }
    // Asumiendo que txtConfirmarPassword es el JPasswordField para confirmar contraseña
    public JPasswordField getTxtConfirmarPassword() { return txtConfirmarPassword; }


    public void mostrarMensaje(String s) { JOptionPane.showMessageDialog(this, s); }

    // --- MÉTODO LIMPIAR CAMPOS CORREGIDO Y COMPLETO ---
    public void limpiarCampos() {
        if (txtNombresComp != null) txtNombresComp.setText("");
        if (txtCorreo != null) txtCorreo.setText("");
        if (txtTelefono != null) txtTelefono.setText("");
        if (txtUsuario != null) txtUsuario.setText("");
        if (txtPassword != null) txtPassword.setText("");
        if (txtConfirmarPassword != null) txtConfirmarPassword.setText(""); // Limpiar campo de confirmación
        if (txtRespuesta1 != null) txtRespuesta1.setText("");
        if (txtRespuesta2 != null) txtRespuesta2.setText("");
        if (txtRespuesta3 != null) txtRespuesta3.setText("");

        // Reiniciar spinners a un valor por defecto si es necesario, o al valor mínimo/máximo
        if (spnDia != null) spnDia.setValue(1);
        if (spnMes != null) spnMes.setValue(1);
        if (spnAño != null) spnAño.setValue(2000); // O el año por defecto que prefieras

        cargarPreguntas(); // Vuelve a cargar las preguntas aleatorias para restablecer
    }
}