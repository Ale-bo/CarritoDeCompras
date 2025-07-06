package ec.edu.ups.vista.Usuario;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.MissingResourceException;

public class ListarUsuarioView extends JInternalFrame {

    private MensajeInternacionalizacionHandler mensajeHandler;

    private JPanel panelPrincipal;
    private JTable tblUsuario;
    private DefaultTableModel model;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JButton btnRefrescar;
    private JLabel Usuario;
    private JComboBox<String> cbxFiltro;

    public ListarUsuarioView(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;

        // Inicializar el comboBox
        cbxFiltro = new JComboBox<>(); // Asegúrate de inicializarlo aquí

        setContentPane(panelPrincipal);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 400);

        model = new DefaultTableModel(new Object[]{"Username", "Rol"}, 0);
        tblUsuario.setModel(model);

        actualizarIdioma(); // Actualizar los textos y etiquetas según el idioma seleccionado
    }


    // Método para actualizar los textos y etiquetas de la interfaz con los valores en el idioma seleccionado
    public void actualizarIdioma() {
        if (mensajeHandler == null) return;

        try {
            setTitle(mensajeHandler.get("usuario.view.listar.titulo"));
            btnBuscar.setText(mensajeHandler.get("usuario.view.listar.buscar"));
            btnRefrescar.setText(mensajeHandler.get("usuario.view.listar.refrescar"));

            cbxFiltro.removeAllItems();
            cbxFiltro.addItem(mensajeHandler.get("usuario.view.eliminar.filtro.username"));
            cbxFiltro.addItem(mensajeHandler.get("usuario.view.eliminar.filtro.rol"));
        } catch (MissingResourceException e) {
            // Detalles de la clave faltante
            System.err.println("Error: La clave '" + e.getKey() + "' no fue encontrada en el archivo de propiedades.");
            // Retorna los textos predeterminados si no se encuentran las claves
            setTitle("Listar Usuarios");
            btnBuscar.setText("Buscar Usuario");
            btnRefrescar.setText("Refrescar Lista");
            cbxFiltro.addItem("Username");
            cbxFiltro.addItem("Role");
        }
    }

    // Métodos para acceder a los datos de la vista
    public JComboBox<String> getComboFiltro() {
        return cbxFiltro;
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnRefrescar() {
        return btnRefrescar;
    }

    public JTable getTableUsuarios() {
        return tblUsuario;
    }

    public DefaultTableModel getTableModel() {
        return model;
    }

    // Método para mostrar un mensaje en la vista
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    // Método para limpiar los campos de búsqueda
    public void limpiarCampos() {
        txtBuscar.setText("");
        cbxFiltro.setSelectedIndex(0);  // Resetear la selección del filtro
    }

    // Método para actualizar los datos de la tabla desde el controlador
    public void setTableModel(DefaultTableModel model) {
        this.model = model;
        tblUsuario.setModel(model);
    }
}



