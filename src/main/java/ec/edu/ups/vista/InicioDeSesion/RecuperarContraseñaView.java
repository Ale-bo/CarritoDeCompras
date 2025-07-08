package ec.edu.ups.vista.InicioDeSesion;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class RecuperarContraseñaView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JTextField textUsername;
    private JTextField textCorreo;
    private JComboBox<String> comboPreguntas;
    private JTextField textRespuesta;
    private JButton btnRecuperar, btnCancelar;
    private JLabel lblUsuario, lblCorreo, lblPregunta, lblRespuesta, correoElectronicoLabel;

    public RecuperarContraseñaView(MensajeInternacionalizacionHandler mh) {
        super(mh.get("usuario.view.recuperar.titulo"), true, true, true, true);

        panelPrincipal = new JPanel(new GridLayout(5, 2, 5, 5));

        lblUsuario   = new JLabel(mh.get("login.label.usuario"));
        textUsername = new JTextField();
        textUsername.setEditable(false);

        lblCorreo    = new JLabel(mh.get("usuario.correo"));
        textCorreo   = new JTextField();
        textCorreo.setEditable(false);

        lblPregunta  = new JLabel(mh.get("usuario.preguntasSeguridad"));
        comboPreguntas = new JComboBox<>();

        lblRespuesta = new JLabel(mh.get("usuario.recuperacion.respuesta"));
        textRespuesta = new JTextField();

        btnRecuperar = new JButton(mh.get("usuario.view.recuperar.btn.recuperar"));
        btnCancelar  = new JButton(mh.get("usuario.view.recuperar.btn.cancelar"));

        panelPrincipal.add(lblUsuario);
        panelPrincipal.add(textUsername);
        panelPrincipal.add(lblCorreo);
        panelPrincipal.add(textCorreo);
        panelPrincipal.add(lblPregunta);
        panelPrincipal.add(comboPreguntas);
        panelPrincipal.add(lblRespuesta);
        panelPrincipal.add(textRespuesta);
        panelPrincipal.add(btnRecuperar);
        panelPrincipal.add(btnCancelar);

        setContentPane(panelPrincipal);
        pack();
    }

    public void actualizarIdioma(MensajeInternacionalizacionHandler mh) {
        setTitle(mh.get("usuario.view.recuperar.titulo"));
        lblUsuario.setText(mh.get("login.label.usuario"));
        lblCorreo.setText(mh.get("usuario.correo"));
        lblPregunta.setText(mh.get("usuario.preguntasSeguridad"));
        lblRespuesta.setText(mh.get("usuario.recuperacion.respuesta"));
        btnRecuperar.setText(mh.get("usuario.view.recuperar.btn.recuperar"));
        btnCancelar.setText(mh.get("usuario.view.recuperar.btn.cancelar"));
    }

    // Para que el controller precargue datos:
    public void setUsername(String u) { textUsername.setText(u); }
    public void setCorreo(String c)   { textCorreo.setText(c); }

    // El controller usará esto para inyectar la pregunta concreta:
    public void setPregunta(String pregunta) {
        comboPreguntas.removeAllItems();
        comboPreguntas.addItem(pregunta);
    }

    // Si en algún caso quieres dar todo el banco de preguntas:
    public void setPreguntasUsuario(List<String> preguntas) {
        comboPreguntas.removeAllItems();
        for (String p : preguntas) {
            comboPreguntas.addItem(p);
        }
    }

    // Getters que tu controller invoca:
    public String getUsername() {
        return textUsername.getText().trim();
    }
    public String getRespuesta1() {
        return textRespuesta.getText().trim();
    }

    // Listeners para los botones:
    public void addRecuperarListener(ActionListener l) {
        btnRecuperar.addActionListener(l);
    }
    public void addCancelarListener(ActionListener l) {
        btnCancelar.addActionListener(l);
    }

    public void mostrarMensaje(String m) {
        JOptionPane.showMessageDialog(this, m);
    }
}
