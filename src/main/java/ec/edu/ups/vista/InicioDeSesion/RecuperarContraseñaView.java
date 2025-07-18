package ec.edu.ups.vista.InicioDeSesion;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

// CAMBIO: Ahora extiende JDialog en lugar de JInternalFrame
public class RecuperarContraseñaView extends JDialog {

    private JPanel panelPrincipal;
    private JTextField textUsername;
    private JTextField textCorreo;
    private JComboBox<String> comboPreguntas;
    private JTextField textRespuesta;
    private JButton btnRecuperar, btnCancelar;
    private JLabel lblUsuario, lblCorreo, lblPregunta, lblRespuesta; // lblRespuesta no está enlazado en el .form

    private int preguntaIdActual;

    // CAMBIO: El constructor ahora recibe el "dueño" (la ventana de Login)
    public RecuperarContraseñaView(Frame owner, MensajeInternacionalizacionHandler mh) {
        super(owner, mh.get("usuario.view.recuperar.titulo"), true); // true para hacerlo modal

        // Establece el panel principal que viene del diseñador de UI
        setContentPane(panelPrincipal);

        // Los componentes (textUsername, textCorreo, comboPreguntas, etc.)
        // ya están instanciados y añadidos al panelPrincipal por el diseñador de UI
        // y el código generado automáticamente.
        // NO DEBES instanciarlos manualmente aquí (ej. 'new JLabel()')
        // ni añadirlos manualmente a 'panelPrincipal' (ej. 'panelPrincipal.add()').
        // Si lo haces, causarás el 'NullPointerException' con 'GridLayoutManager'.

        // Aplica propiedades a los componentes que ya están enlazados desde el formulario
        if (textUsername != null) { // Siempre verifica que el componente no sea nulo antes de usarlo
            textUsername.setEditable(false);
        }
        if (textCorreo != null) { // Siempre verifica que el componente no sea nulo antes de usarlo
            textCorreo.setEditable(false);
        }
        if (comboPreguntas != null) { // Siempre verifica que el componente no sea nulo antes de usarlo
            comboPreguntas.setEnabled(false);
        }

        // Nota: el campo 'lblRespuesta' no parece estar enlazado en tu archivo .form.
        // Si es una etiqueta que necesitas, deberías añadirla explícitamente en el diseñador de UI.
        // Por ahora, el código la maneja con una verificación de nulo para evitar errores.

        actualizarIdioma(mh); // Actualiza los textos de la UI
        pack(); // Ajusta el tamaño de la ventana a sus componentes preferidos
        setLocationRelativeTo(owner); // Centra la ventana respecto a la ventana de Login
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Define el comportamiento al cerrar la ventana
    }

    public void actualizarIdioma(MensajeInternacionalizacionHandler mh) {
        setTitle(mh.get("usuario.view.recuperar.titulo"));
        if (lblUsuario != null) lblUsuario.setText(mh.get("login.label.usuario"));
        if (lblCorreo != null) lblCorreo.setText(mh.get("usuario.correo"));
        if (lblPregunta != null) lblPregunta.setText(mh.get("usuario.preguntasSeguridad"));
        // 'lblRespuesta' no está enlazado en el .form. Solo se establecerá si lo añades al formulario.
        if (lblRespuesta != null) lblRespuesta.setText(mh.get("usuario.recuperacion.respuesta"));
        if (btnRecuperar != null) btnRecuperar.setText(mh.get("usuario.view.recuperar.btn.recuperar"));
        if (btnCancelar != null) btnCancelar.setText(mh.get("usuario.view.recuperar.btn.cancelar"));
    }

    public void setPregunta(String textoPregunta, int preguntaId) {
        if (comboPreguntas != null) {
            comboPreguntas.removeAllItems();
            comboPreguntas.addItem(textoPregunta);
        }
        this.preguntaIdActual = preguntaId;
    }

    public int getPreguntaIdActual() {
        return this.preguntaIdActual;
    }

    public void setUsername(String u) {
        if (textUsername != null) textUsername.setText(u);
    }
    public void setCorreo(String c) {
        if (textCorreo != null) textCorreo.setText(c);
    }
    public String getUsername() {
        return (textUsername != null) ? textUsername.getText().trim() : "";
    }
    public String getRespuesta1() {
        return (textRespuesta != null) ? textRespuesta.getText().trim() : "";
    }
    public void addRecuperarListener(ActionListener l) {
        if (btnRecuperar != null) btnRecuperar.addActionListener(l);
    }
    public void addCancelarListener(ActionListener l) {
        if (btnCancelar != null) btnCancelar.addActionListener(l);
    }
    public void mostrarMensaje(String m) {
        JOptionPane.showMessageDialog(this, m);
    }
}