package ec.edu.ups.vista.Carrito;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnadirCarritoView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JTextField txtCodigo; // Este es el código del producto, no del carrito
    private JTextField txtCantidad;
    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JLabel DatosDelProducto;
    private JLabel Codigo;
    private JLabel Precio;
    private JLabel Cantidad;
    private JButton btnBuscar;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTable tableCarrito;
    private JTextField txtTotal;
    private JTextField txtIva;
    private JTextField txtSubtotal;
    private JLabel Nombre;
    private JButton btnAñadir;
    private JLabel lblSubtotal;
    private JLabel lblIva;
    private JLabel lblTotal;

    private JTextField txtCodigoCarrito;
    private JTextField txtFecha;
    private JLabel lblFecha;
    private JLabel lblCodigoCarro;


    private CarritoController carritoController;
    private final MensajeInternacionalizacionHandler mensajes;
    private DefaultTableModel tableModel;

    public AnadirCarritoView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, true, true);
        this.mensajes = mensajes;

        setContentPane(panelPrincipal);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();

        tableModel = (DefaultTableModel) tableCarrito.getModel();
        actualizarIdioma();
    }

    public void setCarritoController(CarritoController carritoController) {
        this.carritoController = carritoController;
    }

    public void actualizarIdioma() {
        setTitle(mensajes.get("carrito.anadir.titulo"));

        // --- TEXTOS PARA CÓDIGO Y FECHA DEL CARRITO ---
        if (lblCodigoCarro != null) lblCodigoCarro.setText(mensajes.get("carrito.anadir.lbl.codigocarrito") + ":");
        if (lblFecha != null) lblFecha.setText(mensajes.get("carrito.anadir.lbl.fecha") + ":"); // <-- NUEVO: Texto para la etiqueta de fecha
        // --- FIN TEXTOS NUEVOS ---

        DatosDelProducto.setText(mensajes.get("producto.view.anadir.titulo"));
        Codigo.setText(mensajes.get("producto.view.anadir.codigo"));
        Nombre.setText(mensajes.get("producto.view.anadir.nombre"));
        Precio.setText(mensajes.get("producto.view.anadir.precio"));
        Cantidad.setText(mensajes.get("carrito.anadir.lbl.cantidad"));
        btnBuscar.setText(mensajes.get("producto.view.modificar.buscar"));
        btnAñadir.setText(mensajes.get("carrito.anadir.btn.aceptar"));
        btnGuardar.setText(mensajes.get("usuario.view.cambiarContraseña.guardar"));
        btnLimpiar.setText(mensajes.get("producto.view.anadir.limpiar"));
        lblSubtotal.setText(mensajes.get("carrito.subtotal"));
        lblIva.setText(mensajes.get("carrito.iva"));
        lblTotal.setText(mensajes.get("carrito.listar.tabla.column.total"));


        tableModel.setColumnIdentifiers(new Object[]{
                mensajes.get("producto.listar.tabla.column.codigo"),
                mensajes.get("producto.listar.tabla.column.nombre"),
                mensajes.get("carrito.listar.tabla.column.cantidad"),
                "Subtotal"
        });
    }


    public JTextField getTxtCodigoCarrito() {
        return txtCodigoCarrito;
    }
    public JTextField getTxtFecha() { // <-- NUEVO: Getter para el campo de fecha
        return txtFecha;
    }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnAnadir() { return btnAñadir; }
    public JButton getBtnGuardar() { return btnGuardar; }
    public JButton getBtnLimpiar() { return btnLimpiar; }
    public JTextField getTxtCantidad() { return txtCantidad; }

    public String getCodigo() { return txtCodigo.getText().trim(); }
    public JTextField getTxtCodigo() { return txtCodigo; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtPrecio() { return txtPrecio; }
    public JTextField getTxtSubtotal() { return txtSubtotal; }
    public JTextField getTxtIva() { return txtIva; }
    public JTextField getTxtTotal() { return txtTotal; }
    public DefaultTableModel getTableModel() { return tableModel; }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }


    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        txtCantidad.setText("");
        txtSubtotal.setText("");
        txtIva.setText("");
        txtTotal.setText("");

        if (txtCodigoCarrito != null) {
            txtCodigoCarrito.setText("");
        }
        if (txtFecha != null) {
            txtFecha.setText("");
        }

        if (tableModel != null) {
            tableModel.setRowCount(0);
        }
    }
}