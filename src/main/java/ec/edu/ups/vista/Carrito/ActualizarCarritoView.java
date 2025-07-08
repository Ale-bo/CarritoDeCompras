package ec.edu.ups.vista.Carrito;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ActualizarCarritoView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JTextField txtCantidad;
    private JButton btnBuscar;
    private JButton btnActualizar;
    private JButton btnCancelar;
    private JLabel lblCantidad;
    private JTable tblCarritos;
    private JLabel codigoLabel;
    private JLabel totalLabel;
    private JTextField txtTotal;
    private DefaultTableModel modelo;
    private final MensajeInternacionalizacionHandler mensajeHandler;
    private CarritoController carritoController;

    public ActualizarCarritoView(MensajeInternacionalizacionHandler mensajeHandler) {
        super("", true, true, true, true);
        this.mensajeHandler = mensajeHandler;

        setContentPane(panelPrincipal);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 400);

        modelo = (DefaultTableModel) tblCarritos.getModel();
        actualizarIdioma();
    }

    public void setCarritoController(CarritoController carritoController) {
        this.carritoController = carritoController;
        btnBuscar.addActionListener(e -> this.carritoController.cargarTablaMod());
        btnActualizar.addActionListener(e -> this.carritoController.actualizarCarrito());
        btnCancelar.addActionListener(e -> limpiarCampos());
    }

    public void actualizarIdioma() {
        setTitle(mensajeHandler.get("carrito.view.modificar.titulo"));
        // Ajustar textos...
        btnBuscar.setText(mensajeHandler.get("carrito.view.modificar.buscar"));
        btnActualizar.setText(mensajeHandler.get("carrito.view.modificar.actualizar"));
        btnCancelar.setText(mensajeHandler.get("carrito.view.modificar.cancelar"));

        modelo.setColumnIdentifiers(new Object[]{
                mensajeHandler.get("carrito.view.modificar.codigo"),
                mensajeHandler.get("carrito.view.modificar.cantidad")
        });
    }

    // Getters de componentes
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

    public JTable getTblCarritos() {
        return tblCarritos;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
        txtCantidad.setText("");
    }

    public DefaultTableModel getTableModel() {
        return modelo;
    }
}