package ec.edu.ups.vista.Producto;

import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EliminarProductoView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JComboBox<String> comboFiltro;
    private JTextField txtBusqueda;
    private JButton btnBuscar;
    private JTable tablaResultado;
    private JButton btnEliminar;

    private DefaultTableModel modelo;
    private ProductoController productoController;
    private final MensajeInternacionalizacionHandler mensajes;

    public EliminarProductoView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, true, true);
        this.mensajes = mensajes;

        setContentPane(panelPrincipal);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();

        modelo = (DefaultTableModel) tablaResultado.getModel();
        actualizarIdioma();
    }

    public void setProductoController(ProductoController controller) {
        this.productoController = controller;

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productoController.buscarProductoParaEliminar();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productoController.eliminarProductoSeleccionado();
            }
        });
    }

    public void actualizarIdioma() {
        setTitle(mensajes.get("producto.eliminar.titulo"));
        btnBuscar.setText(mensajes.get("producto.eliminar.btn.buscar"));
        btnEliminar.setText(mensajes.get("producto.eliminar.btn.eliminar"));

        comboFiltro.removeAllItems();
        comboFiltro.addItem(mensajes.get("producto.eliminar.filtro.nombre"));
        comboFiltro.addItem(mensajes.get("producto.eliminar.filtro.codigo"));

        modelo.setColumnIdentifiers(new Object[]{
                mensajes.get("producto.eliminar.tabla.column.codigo"),
                mensajes.get("producto.eliminar.tabla.column.nombre"),
                mensajes.get("producto.eliminar.tabla.column.precio")
        });
    }

    public JComboBox<String> getComboFiltro() {
        return comboFiltro;
    }

    public String getTxtBusqueda() {
        return txtBusqueda.getText();
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JTable getTablaResultado() {
        return tablaResultado;
    }

    public DefaultTableModel getModelResultado() {
        return modelo;
    }

    public void mostrarMensaje(String s) {
        JOptionPane.showMessageDialog(this, s);
    }

    public void limpiarCampos() {
        txtBusqueda.setText("");
        txtBusqueda.setEnabled(true);
        txtBusqueda.requestFocus();
        ((DefaultTableModel)tablaResultado.getModel()).setRowCount(0);
    }
}