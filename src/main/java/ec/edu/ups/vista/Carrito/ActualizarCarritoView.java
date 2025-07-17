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
        setSize(600, 400);

        // Asegurarse de que el modelo de la tabla esté inicializado
        if (tblCarritos.getModel() instanceof DefaultTableModel) {
            modelo = (DefaultTableModel) tblCarritos.getModel();
        } else {
            modelo = new DefaultTableModel();
            tblCarritos.setModel(modelo);
        }

        actualizarIdioma();
    }

    // ##### CORRECCIÓN AQUÍ: Se reemplazan las lambdas por clases anónimas #####
    public void setCarritoController(CarritoController carritoController) {
        this.carritoController = carritoController;

        // Listener para el botón Buscar
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Llama al método del controlador
                carritoController.cargarCarritoParaModificar();
            }
        });

        // Listener para el botón Actualizar
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Llama al método del controlador
                carritoController.actualizarItemCarrito();
            }
        });

        // Listener para el botón Cancelar
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Llama a un método local de la vista
                limpiarCampos();
            }
        });
    }

    public void actualizarIdioma() {
        setTitle(mensajeHandler.get("carrito.view.modificar.titulo"));
        btnBuscar.setText(mensajeHandler.get("carrito.view.modificar.buscar"));
        btnActualizar.setText(mensajeHandler.get("carrito.view.modificar.actualizar"));
        btnCancelar.setText(mensajeHandler.get("carrito.view.modificar.cancelar"));

        // Definir las columnas de la tabla
        modelo.setColumnIdentifiers(new Object[]{
                "Cod. Producto", "Nombre", "Cantidad", "Subtotal"
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