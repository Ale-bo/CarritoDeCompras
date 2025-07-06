package ec.edu.ups.vista.InicioDeSesion;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;

public class RegistrarUsuarioView extends JFrame {

    private MensajeInternacionalizacionHandler mensajeHandler;

    private JPanel panelPrincipal;
    private JTextField textnombre;
    private JTextField textnacimiento;
    private JTextField textcorreo;
    private JTextField textcelular;
    private JTextField textususario;
    private JPasswordField passwordcontrasena;
    private JPasswordField passwordconfcontrasena;
    private JComboBox comboBoxPreguntas;
    private JTextField textrespuesta;
    private JButton registrarButton;
    private JButton cancelarButton;
    private JLabel confContrasena;
    private JLabel Contrasena;
    private JLabel NombreUsusario;
    private JLabel Celular;
    private JLabel CorreoElectronico;
    private JLabel FechadeNaciemiento;
    private JLabel NombresCompletos;
    private JLabel PreguntasdeSeguridad;

    public RegistrarUsuarioView() {
        setTitle("Registro de Usuario");
        setContentPane(panelPrincipal);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    // Métodos para acceder a los componentes de la vista
    public JButton getCancelarButton() {
        return cancelarButton;
    }

    public JComboBox getComboBoxPreguntas() {
        return comboBoxPreguntas;
    }

    public JButton getRegistrarButton() {
        return registrarButton;
    }

    public JPasswordField getPasswordconfcontrasena() {
        return passwordconfcontrasena;
    }

    public JPasswordField getPasswordcontrasena() {
        return passwordcontrasena;
    }

    public JTextField getTextcelular() {
        return textcelular;
    }

    public JTextField getTextcorreo() {
        return textcorreo;
    }

    public JTextField getTextnacimiento() {
        return textnacimiento;
    }

    public JTextField getTextnombre() {
        return textnombre;
    }

    public JTextField getTextrespuesta() {
        return textrespuesta;
    }

    public JTextField getTextususario() {
        return textususario;
    }

    // Método para actualizar los textos de la vista según el idioma
    public void actualizarIdioma(MensajeInternacionalizacionHandler mensajeHandler) {
        setTitle(mensajeHandler.get("usuario.registro"));

        ((JLabel) panelPrincipal.getComponent(0)).setText(mensajeHandler.get("usuario.nombreCompleto"));
        ((JLabel) panelPrincipal.getComponent(1)).setText(mensajeHandler.get("usuario.fechaNacimiento"));
        ((JLabel) panelPrincipal.getComponent(2)).setText(mensajeHandler.get("usuario.correo"));
        ((JLabel) panelPrincipal.getComponent(3)).setText(mensajeHandler.get("usuario.telefono"));
        ((JLabel) panelPrincipal.getComponent(4)).setText(mensajeHandler.get("usuario.username"));
        ((JLabel) panelPrincipal.getComponent(5)).setText(mensajeHandler.get("usuario.contrasenia"));
        ((JLabel) panelPrincipal.getComponent(6)).setText(mensajeHandler.get("usuario.confirmar"));
        registrarButton.setText(mensajeHandler.get("usuario.registrar"));
        cancelarButton.setText(mensajeHandler.get("usuario.cancelar"));
    }

    // Método para mostrar un mensaje en la vista
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    // Método para limpiar los campos de la vista
    public void limpiarCampos() {
        textnombre.setText("");
        textnacimiento.setText("");
        textcorreo.setText("");
        textcelular.setText("");
        textususario.setText("");
        passwordcontrasena.setText("");
        passwordconfcontrasena.setText("");
    }
}


