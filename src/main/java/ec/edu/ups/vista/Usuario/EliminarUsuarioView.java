package ec.edu.ups.vista.Usuario;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EliminarUsuarioView extends JInternalFrame {

    private MensajeInternacionalizacionHandler mensajeHandler;

    private JPanel panelPrincipal;
    private JComboBox<String> cbxFiltro;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JTable tblUsuarios;
    private JButton btnEliminar;
    private DefaultTableModel model;

    public EliminarUsuarioView(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;

        setContentPane(panelPrincipal);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 400);

        model = new DefaultTableModel(new Object[]{"Username", "Rol"}, 0);
        tblUsuarios.setModel(model);

        actualizarIdioma(); // Actualiza los textos de la vista
    }

    // Método para actualizar los textos y etiquetas de la interfaz con los valores en el idioma seleccionado
    public void actualizarIdioma() {
        if (mensajeHandler == null) return;

        setTitle(mensajeHandler.get("usuario.view.eliminar.titulo"));
        btnBuscar.setText(mensajeHandler.get("usuario.view.eliminar.buscar"));
        btnEliminar.setText(mensajeHandler.get("usuario.view.eliminar.eliminar"));

        cbxFiltro.removeAllItems();
        cbxFiltro.addItem(mensajeHandler.get("usuario.view.eliminar.filtro.username"));
        cbxFiltro.addItem(mensajeHandler.get("usuario.view.eliminar.filtro.rol"));
    }

    // Métodos para acceder a los datos de la vista
    public JTable getTableUsuarios() {
        return tblUsuarios;
    }

    public DefaultTableModel getTableModel() {
        return model;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JComboBox<String> getCbxFiltro() {
        return cbxFiltro;
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
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
}
