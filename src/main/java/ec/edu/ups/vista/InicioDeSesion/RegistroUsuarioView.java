package ec.edu.ups.vista.InicioDeSesion;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;

public class RegistroUsuarioView extends JInternalFrame {

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

    public RegistroUsuarioView() {
        setTitle("Registro de Usuario");
        setContentPane(panelPrincipal);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setMaximizable(true);
        setSize(600, 500);
        setVisible(true);
    }

    public JButton getCancelarButton() {
        return cancelarButton;
    }

    public JLabel getCelular() {
        return Celular;
    }

    public JComboBox getComboBoxPreguntas() {
        return comboBoxPreguntas;
    }

    public JLabel getConfContrasena() {
        return confContrasena;
    }

    public JLabel getContrasena() {
        return Contrasena;
    }

    public JLabel getCorreoElectronico() {
        return CorreoElectronico;
    }

    public JLabel getFechadeNaciemiento() {
        return FechadeNaciemiento;
    }

    public MensajeInternacionalizacionHandler getMensajeHandler() {
        return mensajeHandler;
    }

    public JLabel getNombresCompletos() {
        return NombresCompletos;
    }

    public JLabel getNombreUsusario() {
        return NombreUsusario;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JPasswordField getPasswordconfcontrasena() {
        return passwordconfcontrasena;
    }

    public JPasswordField getPasswordcontrasena() {
        return passwordcontrasena;
    }

    public JLabel getPreguntasdeSeguridad() {
        return PreguntasdeSeguridad;
    }

    public JButton getRegistrarButton() {
        return registrarButton;
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
    public void setMensajeHandler(MensajeInternacionalizacionHandler mensajes) {
        setTitle(mensajes.get("usuario.registro"));

        ((JLabel) panelPrincipal.getComponent(0)).setText(mensajes.get("usuario.nombreCompleto"));
        ((JLabel) panelPrincipal.getComponent(1)).setText(mensajes.get("usuario.fechaNacimiento"));
        ((JLabel) panelPrincipal.getComponent(2)).setText(mensajes.get("usuario.correo"));
        ((JLabel) panelPrincipal.getComponent(3)).setText(mensajes.get("usuario.telefono"));
        ((JLabel) panelPrincipal.getComponent(4)).setText(mensajes.get("usuario.username"));
        ((JLabel) panelPrincipal.getComponent(5)).setText(mensajes.get("usuario.contrasenia"));
        ((JLabel) panelPrincipal.getComponent(6)).setText(mensajes.get("usuario.confirmar"));
        registrarButton.setText(mensajes.get("usuario.registrar"));
        cancelarButton.setText(mensajes.get("usuario.cancelar"));
    }

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

