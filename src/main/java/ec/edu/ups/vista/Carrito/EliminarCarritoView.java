package ec.edu.ups.vista.Carrito;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class EliminarCarritoView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JLabel lblUsuario;
    private JComboBox<String> comboFiltro;
    private JTextField txtBusqueda;
    private JButton btnBuscar;
    private JTable tablaResultado;
    private JButton btnEliminar;

    private DefaultTableModel modelo;
    private CarritoController carritoController;
    private final MensajeInternacionalizacionHandler mensajes;

    public EliminarCarritoView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, true, true);
        this.mensajes = mensajes;

        setContentPane(panelPrincipal);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 400);

        comboFiltro.addItem(mensajes.get("carrito.eliminar.filtro.codigo"));
        comboFiltro.addItem(mensajes.get("carrito.eliminar.filtro.nombre"));

        modelo = (DefaultTableModel) tablaResultado.getModel();
        modelo.setColumnIdentifiers(new Object[]{
                mensajes.get("carrito.eliminar.tabla.column.codigo"),
                mensajes.get("carrito.eliminar.tabla.column.nombre"),
                mensajes.get("carrito.eliminar.tabla.column.precio")
        });

        tablaResultado.setModel(modelo);
        actualizarIdioma();
    }

    public void setCarritoController(CarritoController carritoController) {
        this.carritoController = carritoController;
        btnBuscar.addActionListener(e -> this.carritoController.buscarCarritoParaEliminar());
        btnEliminar.addActionListener(e -> this.carritoController.eliminarCarritoSeleccionado());
    }

    public void actualizarIdioma() {
        setTitle(mensajes.get("carrito.eliminar.titulo"));
        btnBuscar.setText(mensajes.get("carrito.eliminar.btn.buscar"));
        btnEliminar.setText(mensajes.get("carrito.eliminar.btn.eliminar"));

        comboFiltro.removeAllItems();
        comboFiltro.addItem(mensajes.get("carrito.eliminar.filtro.codigo"));
        comboFiltro.addItem(mensajes.get("carrito.eliminar.filtro.nombre"));

        modelo.setColumnIdentifiers(new Object[]{
                mensajes.get("carrito.eliminar.tabla.column.codigo"),
                mensajes.get("carrito.eliminar.tabla.column.nombre"),
                mensajes.get("carrito.eliminar.tabla.column.precio")
        });
    }

    // Getters de componentes
    public JComboBox<String> getFiltro() {
        return comboFiltro;
    }

    public JTextField getTxtBusqueda() {
        return txtBusqueda;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTable getTablaResultado() {
        return tablaResultado;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public DefaultTableModel getModelResultado() {
        return modelo;
    }

    public void mostrarMensaje(String s) {
        JOptionPane.showMessageDialog(this, s);
    }
}
