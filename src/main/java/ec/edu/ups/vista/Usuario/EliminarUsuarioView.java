package ec.edu.ups.vista.Usuario;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

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
        pack();

        model = (DefaultTableModel) tblUsuarios.getModel();
        actualizarIdioma();
    }

    public void actualizarIdioma() {
        if (mensajeHandler == null) return;
        setTitle(mensajeHandler.get("usuario.view.eliminar.titulo"));
        btnBuscar.setText(mensajeHandler.get("usuario.view.eliminar.buscar"));
        btnEliminar.setText(mensajeHandler.get("usuario.view.eliminar.eliminar"));

        // Limpiar y añadir items internacionalizados
        if (cbxFiltro != null) {
            String selected = (String) cbxFiltro.getSelectedItem();
            cbxFiltro.removeAllItems();
            cbxFiltro.addItem(mensajeHandler.get("usuario.view.eliminar.filtro.username"));
            cbxFiltro.addItem(mensajeHandler.get("usuario.view.eliminar.filtro.rol"));
            cbxFiltro.setSelectedItem(selected);
        }

        model.setColumnIdentifiers(new Object[]{"Username", "Rol"});
    }

    public JTable getTableUsuarios() { return tblUsuarios; }
    public DefaultTableModel getTableModel() { return model; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public JComboBox<String> getCbxFiltro() { return cbxFiltro; }
    public JTextField getTxtBuscar() { return txtBuscar; }
    public JButton getBtnBuscar() { return btnBuscar; }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void limpiarCampos() {
        txtBuscar.setText("");
        if (cbxFiltro != null) cbxFiltro.setSelectedIndex(0);
    }
}