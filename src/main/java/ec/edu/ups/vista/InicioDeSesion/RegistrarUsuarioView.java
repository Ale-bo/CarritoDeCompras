package ec.edu.ups.vista.InicioDeSesion;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;

public class RegistrarUsuarioView extends JFrame {

    private MensajeInternacionalizacionHandler mensajeHandler;

    private JPanel panelPrincipal;

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
    private JLabel lblPregunta1;
    private JTextField textRespuesta2;
    private JTextField textFRespuesta3;
    private JLabel lblPregunta2;
    private JLabel lblPregunta3;
    private JComboBox<String> comboPreguntasSeguridad1;
    private JComboBox<String> comboPreguntasSeguridad2;
    private JComboBox<String> comboPreguntasSeguridad3;
    private JTextField txtRespuesta1;
    private JTextField txtRespuesta2;
    private JTextField txtRespuesta3;
    private JComboBox<String> comboIdiomas;
    private static final String[] IDIOMAS = {"Español", "English", "Français"};

    public RegistrarUsuarioView() {
        this.mensajeHandler = new MensajeInternacionalizacionHandler("es", "ES");

        setTitle("Registro de Usuario");
        setContentPane(panelPrincipal);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Inicializar los componentes
        comboPreguntasSeguridad1 = new JComboBox<>();
        comboPreguntasSeguridad2 = new JComboBox<>();
        comboPreguntasSeguridad3 = new JComboBox<>();

        // Configurar el menú de idiomas
        configurarMenuIdiomas();

        // Cargar las preguntas iniciales
        cargarPreguntas();
        configurarComboBoxPreguntas();
    }

    private void configurarComboBoxPreguntas() {
        // Configurar los listeners para actualizar las etiquetas
        comboPreguntasSeguridad1.addActionListener(e -> {
            String preguntaSeleccionada = (String) comboPreguntasSeguridad1.getSelectedItem();
            lblPregunta1.setText(preguntaSeleccionada);
        });

        comboPreguntasSeguridad2.addActionListener(e -> {
            String preguntaSeleccionada = (String) comboPreguntasSeguridad2.getSelectedItem();
            lblPregunta2.setText(preguntaSeleccionada);
        });

        comboPreguntasSeguridad3.addActionListener(e -> {
            String preguntaSeleccionada = (String) comboPreguntasSeguridad3.getSelectedItem();
            lblPregunta3.setText(preguntaSeleccionada);
        });
    }


    private void configurarMenuIdiomas() {
        // Crear barra de menú
        JMenuBar menuBar = new JMenuBar();

        // Crear el combo box de idiomas
        comboIdiomas = new JComboBox<>(IDIOMAS);
        comboIdiomas.setMaximumSize(new Dimension(150, 30));

        // Agregar el combo box al menú
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(new JLabel("Idioma: "));
        menuBar.add(comboIdiomas);

        // Establecer la barra de menú
        setJMenuBar(menuBar);

        // Agregar el listener para cambios de idioma
        comboIdiomas.addActionListener(e -> cambiarIdioma());
    }

    private void cargarPreguntas() {
        // Primero limpiamos los ComboBox
        comboPreguntasSeguridad1.removeAllItems();
        comboPreguntasSeguridad2.removeAllItems();
        comboPreguntasSeguridad3.removeAllItems();

        // Agregamos las preguntas traducidas
        for (int i = 1; i <= 10; i++) {
            String pregunta = mensajeHandler.get("preguntas.seguridad." + i);
            System.out.println("Cargando pregunta: " + pregunta);
            comboPreguntasSeguridad1.addItem(pregunta);
            comboPreguntasSeguridad2.addItem(pregunta);
            comboPreguntasSeguridad3.addItem(pregunta);
        }
        if (comboPreguntasSeguridad1.getItemCount() > 0) {
            lblPregunta1.setText((String) comboPreguntasSeguridad1.getSelectedItem());
            lblPregunta2.setText((String) comboPreguntasSeguridad2.getSelectedItem());
            lblPregunta3.setText((String) comboPreguntasSeguridad3.getSelectedItem());
        }

    }




    private void cambiarIdioma() {
        if (mensajeHandler == null) {
            mensajeHandler = new MensajeInternacionalizacionHandler("es", "ES");
        }

        String idiomaSeleccionado = (String) comboIdiomas.getSelectedItem();
        switch (idiomaSeleccionado) {
            case "Español":
                mensajeHandler.setLenguaje("es", "ES");
                break;
            case "English":
                mensajeHandler.setLenguaje("en", "US");
                break;
            case "Français":
                mensajeHandler.setLenguaje("fr", "FR");
                break;
        }


        actualizarIdioma(mensajeHandler);
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
    public JButton getCancelarButton() {
        return cancelarButton;
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


    public JTextField getTextnombre() {
        return textnombre;
    }


    public JTextField getTextususario() {
        return textususario;
    }

    public JTextField getTextAnio() {
        return textAnio;
    }

    public JTextField getTextDia() {
        return textDia;
    }

    public JTextField getTextMes() {
        return textMes;
    }


    public void setPregunta1(String pregunta) {
        lblPregunta1.setText(pregunta);
    }

    public void setPregunta2(String pregunta) {
        lblPregunta2.setText(pregunta);
    }

    public void setPregunta3(String pregunta) {
        lblPregunta3.setText(pregunta);
    }

    // Métodos para obtener las respuestas
    public String getRespuesta1() {
        return textRespuesta1.getText();
    }

    public String getRespuesta2() {
        return textRespuesta2.getText();
    }

    public String getRespuesta3() {
        return textFRespuesta3.getText();
    }

    // Método para mostrar un mensaje en la vista
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    // Método para limpiar los campos de la vista
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


