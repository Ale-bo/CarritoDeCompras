package ec.edu.ups.vista.Carrito;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class EliminarCarritoView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JLabel lblUsuario;
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
        pack();

        modelo = (DefaultTableModel) tablaResultado.getModel();
        actualizarIdioma();
    }

    public void setCarritoController(CarritoController carritoController) {
        this.carritoController = carritoController;
    }

    public void actualizarIdioma() {
        setTitle(mensajes.get("carrito.eliminar.titulo"));
        lblUsuario.setText(mensajes.get("carrito.eliminar.filtro.codigo"));
        btnBuscar.setText(mensajes.get("carrito.eliminar.btn.buscar"));
        btnEliminar.setText(mensajes.get("carrito.eliminar.btn.eliminar"));

        modelo.setColumnIdentifiers(new Object[]{
                mensajes.get("carrito.listar.tabla.column.codigo"),
                mensajes.get("carrito.listar.tabla.column.fecha"),
                mensajes.get("carrito.listar.tabla.column.total")
        });
    }

    public JTextField getTxtBusqueda() { return txtBusqueda; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JTable getTablaResultado() { return tablaResultado; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public DefaultTableModel getModelResultado() { return modelo; }

    public void mostrarMensaje(String s) {
        JOptionPane.showMessageDialog(this, s);
    }
}
