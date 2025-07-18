package ec.edu.ups.vista.Carrito;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        pack();

        modelo = (DefaultTableModel) tblCarritos.getModel();
        actualizarIdioma();
    }

    public void setCarritoController(CarritoController carritoController) {
        this.carritoController = carritoController;
    }

    public void actualizarIdioma() {
        setTitle(mensajeHandler.get("carrito.view.modificar.titulo"));
        codigoLabel.setText(mensajeHandler.get("carrito.view.modificar.codigo"));
        lblCantidad.setText(mensajeHandler.get("carrito.view.modificar.cantidad"));
        btnBuscar.setText(mensajeHandler.get("carrito.view.modificar.buscar"));
        btnActualizar.setText(mensajeHandler.get("carrito.view.modificar.actualizar"));
        btnCancelar.setText(mensajeHandler.get("carrito.view.modificar.cancelar"));
        totalLabel.setText(mensajeHandler.get("carrito.listar.tabla.column.total"));

        modelo.setColumnIdentifiers(new Object[]{
                "Cod. Producto", "Nombre", "Cantidad", "Subtotal"
        });
    }

    public JTextField getTxtCodigo() { return txtCodigo; }
    public JTextField getTxtCantidad() { return txtCantidad; }
    public JTextField getTxtTotal() { return txtTotal; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnActualizar() { return btnActualizar; }
    public JButton getBtnCancelar() { return btnCancelar; }
    public JTable getTblCarritos() { return tblCarritos; }
    public DefaultTableModel getModelo() { return modelo; }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
        txtCantidad.setText("");
        txtTotal.setText("");
        modelo.setRowCount(0);
    }
}