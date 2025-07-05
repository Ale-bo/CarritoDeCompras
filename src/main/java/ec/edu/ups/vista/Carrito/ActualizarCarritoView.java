package ec.edu.ups.vista.Carrito;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ActualizarCarritoView extends JInternalFrame {

    private final CarritoController carritoController;
    private final MensajeInternacionalizacionHandler mensajeHandler;

    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JTextField txtCantidad;
    private JButton btnBuscar;
    private JButton btnActualizar;
    private JButton btnCancelar;
    private JTable tblCarritos;
    private DefaultTableModel modelo;
    private JLabel lblCodigo;
    private JLabel lblCantidad;

    public ActualizarCarritoView(CarritoController carritoController, MensajeInternacionalizacionHandler mensajeHandler) {
        this.carritoController = carritoController;
        this.mensajeHandler = mensajeHandler;

        setContentPane(panelPrincipal);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 400);

        modelo = (DefaultTableModel) tblCarritos.getModel();

        actualizarIdioma();

        // Delegando la carga de carritos al controlador
        btnBuscar.addActionListener(e -> carritoController.cargarTablaMod());
        btnActualizar.addActionListener(e -> carritoController.actualizarCarrito());
        btnCancelar.addActionListener(e -> limpiarCampos());
    }

    public void actualizarIdioma() {
        setTitle(mensajeHandler.get("carrito.view.modificar.titulo"));
        lblCodigo.setText(mensajeHandler.get("carrito.view.modificar.codigo"));
        lblCantidad.setText(mensajeHandler.get("carrito.view.modificar.cantidad"));
        btnBuscar.setText(mensajeHandler.get("carrito.view.modificar.buscar"));
        btnActualizar.setText(mensajeHandler.get("carrito.view.modificar.actualizar"));
        btnCancelar.setText(mensajeHandler.get("carrito.view.modificar.cancelar"));

        modelo.setColumnIdentifiers(new Object[]{
                mensajeHandler.get("carrito.view.modificar.codigo"),
                mensajeHandler.get("carrito.view.modificar.nombre"),
                mensajeHandler.get("carrito.view.modificar.precio"),
                mensajeHandler.get("carrito.view.modificar.cantidad")
        });
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JTextField getTxtCantidad() {
        return txtCantidad;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public JTable getTableCarritos() {
        return tblCarritos;
    }

    public DefaultTableModel getTableModel() {
        return modelo;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
        txtCantidad.setText("");
    }
}


