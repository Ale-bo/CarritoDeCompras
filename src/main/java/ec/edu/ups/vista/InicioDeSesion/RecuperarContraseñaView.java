package ec.edu.ups.vista.InicioDeSesion;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RecuperarContraseñaView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JTextField textUsername;
    private JTextField textCorreo;
    private JComboBox<String> comboPreguntas;
    private JTextField textRespuesta;
    private JButton btnRecuperar, btnCancelar;
    private JLabel lblUsuario, lblCorreo, lblPregunta, lblRespuesta;
    private int preguntaIdActual;

    public RecuperarContraseñaView(MensajeInternacionalizacionHandler mh) {
        super(mh.get("usuario.view.recuperar.titulo"), true, true, true, true);
        panelPrincipal = new JPanel(new GridLayout(5, 2, 5, 5));

        lblUsuario   = new JLabel();
        textUsername = new JTextField();
        textUsername.setEditable(false);

        lblCorreo    = new JLabel();
        textCorreo   = new JTextField();
        textCorreo.setEditable(false);

        lblPregunta  = new JLabel();
        comboPreguntas = new JComboBox<>();

        lblRespuesta = new JLabel();
        textRespuesta = new JTextField();

        btnRecuperar = new JButton();
        btnCancelar  = new JButton();

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
        actualizarIdioma(mh); // Se llama para establecer los textos iniciales
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

    public void setPregunta(String textoPregunta, int preguntaId) {
        comboPreguntas.removeAllItems();
        comboPreguntas.addItem(textoPregunta);
        this.preguntaIdActual = preguntaId;
    }

    public int getPreguntaIdActual() {
        return this.preguntaIdActual;
    }

    public void setUsername(String u) { textUsername.setText(u); }
    public void setCorreo(String c) { textCorreo.setText(c); }
    public String getUsername() { return textUsername.getText().trim(); }
    public String getRespuesta1() { return textRespuesta.getText().trim(); }
    public void addRecuperarListener(ActionListener l) { btnRecuperar.addActionListener(l); }
    public void addCancelarListener(ActionListener l) { btnCancelar.addActionListener(l); }
    public void mostrarMensaje(String m) { JOptionPane.showMessageDialog(this, m); }
}
