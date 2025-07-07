package ec.edu.ups.vista.InicioDeSesion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RecuperarContraseñaView extends JFrame {

    private JPanel panelPrincipal;
    private JTextField textUsername;
    private JPasswordField passwordNuevaContraseña;
    private JPasswordField passwordConfirmarContraseña;
    private JButton btnRecuperar;
    private JButton btnCancelar;
    private JLabel lblPregunta1;
    private JLabel lblPregunta2;
    private JLabel lblPregunta3;
    private JTextField textRespuesta1;
    private JTextField textRespuesta2;
    private JTextField textRespuesta3;

    public RecuperarContraseñaView() {
        setTitle("Recuperar Contraseña");

        // Configuración básica de la ventana
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Inicializar el panel principal
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));

        // Crear y agregar componentes
        JLabel lblUsuario = new JLabel("Nombre de Usuario:");
        textUsername = new JTextField(20);

        lblPregunta1 = new JLabel("Pregunta 1:");
        textRespuesta1 = new JTextField(20);

        lblPregunta2 = new JLabel("Pregunta 2:");
        textRespuesta2 = new JTextField(20);

        lblPregunta3 = new JLabel("Pregunta 3:");
        textRespuesta3 = new JTextField(20);

        JLabel lblNuevaContraseña = new JLabel("Nueva Contraseña:");
        passwordNuevaContraseña = new JPasswordField(20);

        JLabel lblConfirmar = new JLabel("Confirmar Contraseña:");
        passwordConfirmarContraseña = new JPasswordField(20);

        btnRecuperar = new JButton("Recuperar Contraseña");
        btnCancelar = new JButton("Cancelar");

        // Agregar componentes al panel
        panelPrincipal.add(Box.createVerticalStrut(20));
        panelPrincipal.add(crearPanel(lblUsuario, textUsername));
        panelPrincipal.add(Box.createVerticalStrut(10));
        panelPrincipal.add(crearPanel(lblPregunta1, textRespuesta1));
        panelPrincipal.add(Box.createVerticalStrut(10));
        panelPrincipal.add(crearPanel(lblPregunta2, textRespuesta2));
        panelPrincipal.add(Box.createVerticalStrut(10));
        panelPrincipal.add(crearPanel(lblPregunta3, textRespuesta3));
        panelPrincipal.add(Box.createVerticalStrut(10));
        panelPrincipal.add(crearPanel(lblNuevaContraseña, passwordNuevaContraseña));
        panelPrincipal.add(Box.createVerticalStrut(10));
        panelPrincipal.add(crearPanel(lblConfirmar, passwordConfirmarContraseña));
        panelPrincipal.add(Box.createVerticalStrut(20));

        // Panel para botones
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnRecuperar);
        panelBotones.add(btnCancelar);
        panelPrincipal.add(panelBotones);

        // Agregar panel principal a la ventana
        add(panelPrincipal);
    }

    // Método auxiliar para crear paneles con label y campo
    private JPanel crearPanel(JLabel label, JComponent campo) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(label);
        panel.add(campo);
        return panel;
    }

    // Getters
    public String getUsername() {
        return textUsername.getText();
    }

    public String getRespuesta1() {
        return textRespuesta1.getText();
    }

    public String getRespuesta2() {
        return textRespuesta2.getText();
    }

    public String getRespuesta3() {
        return textRespuesta3.getText();
    }

    public String getNuevaContraseña() {
        return new String(passwordNuevaContraseña.getPassword());
    }

    public String getConfirmarContraseña() {
        return new String(passwordConfirmarContraseña.getPassword());
    }

    // Método para establecer las preguntas
    public void setPreguntasSeguridad(ArrayList<String> preguntas) {
        if (preguntas != null && preguntas.size() >= 3) {
            lblPregunta1.setText(preguntas.get(0));
            lblPregunta2.setText(preguntas.get(1));
            lblPregunta3.setText(preguntas.get(2));
        }
    }

    // Métodos de utilidad
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void limpiarCampos() {
        textUsername.setText("");
        textRespuesta1.setText("");
        textRespuesta2.setText("");
        textRespuesta3.setText("");
        passwordNuevaContraseña.setText("");
        passwordConfirmarContraseña.setText("");
    }

    // Action Listeners
    public void addRecuperarListener(ActionListener listener) {
        btnRecuperar.addActionListener(listener);
    }

    public void addCancelarListener(ActionListener listener) {
        btnCancelar.addActionListener(listener);
    }
}
