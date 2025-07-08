package ec.edu.ups.vista.InicioDeSesion;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RegistrarUsuarioView extends JFrame {

    private MensajeInternacionalizacionHandler mensajeHandler;


    private JLabel confContrasena;
    private JLabel Contrasena;
    private JLabel NombreUsusario;
    private JLabel Celular;
    private JLabel CorreoElectronico;
    private JLabel FechadeNaciemiento;
    private JLabel NombresCompletos;
    private JLabel PreguntasdeSeguridad;

    private JTextField textnombre;
    private JTextField textcorreo;
    private JTextField textcelular;
    private JTextField textususario;
    private JPasswordField passwordcontrasena;
    private JPasswordField passwordconfcontrasena;
    private JButton registrarButton;
    private JButton cancelarButton;

    private JTextField textDia;
    private JTextField textMes;
    private JTextField textAnio;
    private JLabel lblDia;
    private JLabel lblMes;
    private JLabel lblAño;
    private JTextField textRespuesta1;
    private JTextField textRespuesta2;
    private JTextField textFRespuesta3;
    public JComboBox<String> comboPreguntasSeguridad1;
    public JComboBox<String> comboPreguntasSeguridad2;
    public JComboBox<String> comboPreguntasSeguridad3;
    private JPanel panelPrincipal;
    private JPanel panel;
    private JComboBox<String> comboIdiomas;
    private static final String[] IDIOMAS = {"Español", "English", "Français"};

    private final List<Integer> preguntasIdsSeleccionadas = new ArrayList<>();

    public RegistrarUsuarioView() {
        this.mensajeHandler = new MensajeInternacionalizacionHandler("es", "ES");

        setTitle("Registro de Usuario");
        setContentPane(panelPrincipal);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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

        comboPreguntasSeguridad1.removeAllItems();
        comboPreguntasSeguridad2.removeAllItems();
        comboPreguntasSeguridad3.removeAllItems();

        int idx1 = indices.get(0);
        int idx2 = indices.get(1);
        int idx3 = indices.get(2);

        comboPreguntasSeguridad1.addItem(textos.get(idx1));
        comboPreguntasSeguridad2.addItem(textos.get(idx2));
        comboPreguntasSeguridad3.addItem(textos.get(idx3));

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

        NombresCompletos.setText(mensajeHandler.get("usuario.nombreCompleto"));
        FechadeNaciemiento.setText(mensajeHandler.get("usuario.fechaNacimiento"));
        CorreoElectronico.setText(mensajeHandler.get("usuario.correo"));
        Celular.setText(mensajeHandler.get("usuario.telefono"));
        NombreUsusario.setText(mensajeHandler.get("usuario.username"));
        Contrasena.setText(mensajeHandler.get("usuario.contrasenia"));
        confContrasena.setText(mensajeHandler.get("usuario.confirmar"));

        registrarButton.setText(mensajeHandler.get("usuario.registrar"));
        cancelarButton.setText(mensajeHandler.get("usuario.cancelar"));

        lblDia.setText(mensajeHandler.get("usuario.dia"));
        lblMes.setText(mensajeHandler.get("usuario.mes"));
        lblAño.setText(mensajeHandler.get("usuario.anio"));

        PreguntasdeSeguridad.setText(mensajeHandler.get("usuario.preguntasSeguridad"));

        cargarPreguntas();
    }

    // Métodos para acceder a los componentes de la vista
    public JButton getCancelarButton() {return cancelarButton;}
    public JButton getRegistrarButton() {return registrarButton;}
    public JPasswordField getPasswordconfcontrasena() {return passwordconfcontrasena;}
    public JPasswordField getPasswordcontrasena() {return passwordcontrasena;}
    public JTextField getTextcelular() {return textcelular;}
    public JTextField getTextcorreo() {return textcorreo;}
    public JTextField getTextnombre() {return textnombre;}
    public JTextField getTextususario() {return textususario;}
    public JTextField getTextAnio() {return textAnio;}
    public JTextField getTextDia() {return textDia;}
    public JTextField getTextMes() {return textMes;}
    public String getRespuesta1() {return textRespuesta1.getText();}
    public String getRespuesta2() {return textRespuesta2.getText();}
    public String getRespuesta3() {return textFRespuesta3.getText();}

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
    }
}
