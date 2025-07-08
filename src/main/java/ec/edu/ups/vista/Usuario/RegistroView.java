package ec.edu.ups.vista.Usuario;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private JSpinner spnDia;
    private JSpinner spnMes;
    private JSpinner spnAño;

    // Campo para IDs de preguntas seleccionadas
    private final List<Integer> preguntasIdsSeleccionadas = new ArrayList<>();

    public RegistroView(MensajeInternacionalizacionHandler mensajeHandler) {
        super(mensajeHandler.get("usuario.view.registrar.titulo"));
        this.mensajeHandler = mensajeHandler;
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setResizable(false);
        actualizarIdioma();
        cargarPreguntas();
    }

    public void actualizarIdioma() {
        setTitle(mensajeHandler.get("usuario.view.registrar.titulo"));
        lblUsuario.setText(mensajeHandler.get("usuario.view.registrar.usuario"));
        lblPassword.setText(mensajeHandler.get("usuario.view.registrar.password"));
        lblPreguntas.setText(mensajeHandler.get("usuario.view.registrar.preguntas"));
        lblPregunta1.setText(mensajeHandler.get("usuario.view.registrar.pregunta1"));
        lblPregunta2.setText(mensajeHandler.get("usuario.view.registrar.pregunta2"));
        lblPregunta3.setText(mensajeHandler.get("usuario.view.registrar.pregunta3"));
        lblNombresComp.setText(mensajeHandler.get("usuario.view.registrar.nombres"));
        lblTelefono.setText(mensajeHandler.get("usuario.view.registrar.telefono"));
        lblCorreo.setText(mensajeHandler.get("usuario.view.registrar.correo"));
        lblDia.setText(mensajeHandler.get("usuario.view.registrar.dia"));
        lblMes.setText(mensajeHandler.get("usuario.view.registrar.mes"));
        lblAño.setText(mensajeHandler.get("usuario.view.registrar.ano"));
        btnCrear.setText(mensajeHandler.get("usuario.view.registrar.btn.crear"));
        btnCancelar.setText(mensajeHandler.get("usuario.view.registrar.btn.cancelar"));

        cargarPreguntas();
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

        cbxPregunta1.removeAllItems();
        cbxPregunta2.removeAllItems();
        cbxPregunta3.removeAllItems();

        int idx1 = indices.get(0);
        int idx2 = indices.get(1);
        int idx3 = indices.get(2);

        cbxPregunta1.addItem(textos.get(idx1));
        cbxPregunta2.addItem(textos.get(idx2));
        cbxPregunta3.addItem(textos.get(idx3));

        preguntasIdsSeleccionadas.clear();
        preguntasIdsSeleccionadas.add(ids.get(idx1));
        preguntasIdsSeleccionadas.add(ids.get(idx2));
        preguntasIdsSeleccionadas.add(ids.get(idx3));
    }

    public List<Integer> getPreguntasIdsSeleccionadas() {
        return preguntasIdsSeleccionadas;
    }

    // Métodos getters iguales
    public void setPreguntas(List<String> preguntas) {
        cbxPregunta1.removeAllItems();
        cbxPregunta2.removeAllItems();
        cbxPregunta3.removeAllItems();
        for (String pregunta : preguntas) {
            cbxPregunta1.addItem(pregunta);
            cbxPregunta2.addItem(pregunta);
            cbxPregunta3.addItem(pregunta);
        }
    }
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
    public void mostrarMensaje(String s) { JOptionPane.showMessageDialog(this, s); }
}
