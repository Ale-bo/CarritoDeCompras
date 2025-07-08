package ec.edu.ups.vista.InicioDeSesion;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;

public class CambiarContraseñaView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JTextField textUsuario;
    private JPasswordField passContraseña;
    private JPasswordField passCfmContraseña;
    private JButton guardarButton;
    private JButton cancelarButton;
    private JLabel lblUsuario;
    private JLabel lblContraseñaNueva;
    private JLabel lblCfmContraseña;
    private JLabel lblCondiciones;
    private JLabel lblContraseñaSegura;

    public CambiarContraseñaView() {
        setContentPane(panelPrincipal);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 400);
    }

    /** Carga el nombre de usuario en el campo */
    public void setUsuario(String usr) {
        textUsuario.setText(usr);
    }

    // Getters de datos
    public String getTxtUsuario() {
        return textUsuario.getText();
    }
    public String getNuevaContraseña() {
        return new String(passContraseña.getPassword());
    }
    public String getConfirmarContraseña() {
        return new String(passCfmContraseña.getPassword());
    }

    // Getters de componentes para el controlador
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
    public JTextField getTextUsuarioField() {
        return textUsuario;
    }
    public JPasswordField getPassContraseñaField() {
        return passContraseña;
    }
    public JPasswordField getPassCfmContraseñaField() {
        return passCfmContraseña;
    }
    public JButton getGuardarButton() {
        return guardarButton;
    }
    public JButton getCancelarButton() {
        return cancelarButton;
    }
    public JLabel getLblUsuario() {
        return lblUsuario;
    }
    public JLabel getLblContraseñaNueva() {
        return lblContraseñaNueva;
    }
    public JLabel getLblCfmContraseña() {
        return lblCfmContraseña;
    }
    public JLabel getLblCondiciones() {
        return lblCondiciones;
    }

    /** Actualiza textos según el idioma */
    public void actualizarIdioma(MensajeInternacionalizacionHandler mh) {
        setTitle(mh.get("usuario.view.cambiarContraseña.titulo"));
        lblUsuario.setText(mh.get("usuario.view.cambiarContraseña.usuario"));
        lblContraseñaNueva.setText(mh.get("usuario.view.cambiarContraseña.nueva"));
        lblCfmContraseña.setText(mh.get("usuario.view.cambiarContraseña.confirmar"));
        lblCondiciones.setText(mh.get("usuario.view.cambiarContraseña.condiciones"));
        guardarButton.setText(mh.get("usuario.view.cambiarContraseña.guardar"));
        cancelarButton.setText(mh.get("usuario.view.cambiarContraseña.cancelar"));
    }

    /** Muestra un diálogo de mensaje */
    public void mostrarMensaje(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    /** Limpia todos los campos */
    public void limpiarCampos() {
        textUsuario.setText("");
        passContraseña.setText("");
        passCfmContraseña.setText("");
    }
}
