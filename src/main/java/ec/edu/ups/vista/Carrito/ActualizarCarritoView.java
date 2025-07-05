package ec.edu.ups.vista.Carrito;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ActualizarCarritoView extends JInternalFrame {

    private final CarritoController carritoController;
    private final MensajeInternacionalizacionHandler mensajeHandler;

    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JButton btnCancelar;
    private JTextField txtTotal;
    private JButton btnActualizar;
    private JTable tabla;
    private DefaultTableModel modelo;

    public ActualizarCarritoView(CarritoController controller, MensajeInternacionalizacionHandler mensajeHandler) {
        this.carritoController = controller;
        this.mensajeHandler = mensajeHandler;

        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocation(30, 30);

        initComponents();
        setContentPane(panelPrincipal);
        actualizarIdioma();

        btnBuscar.addActionListener(e -> carritoController.buscarCarritoParaActualizar());
        btnActualizar.addActionListener(e -> carritoController.actualizarCarrito());
        btnCancelar.addActionListener(e -> carritoController.cancelarActualizacion());
    }

    private void initComponents() {
        panelPrincipal = new JPanel(new BorderLayout());

        JPanel panelSuperior = new JPanel(new FlowLayout());
        JLabel lblCodigo = new JLabel("Código:");
        txtCodigo = new JTextField(10);
        btnBuscar = new JButton();
        panelSuperior.add(lblCodigo);
        panelSuperior.add(txtCodigo);
        panelSuperior.add(btnBuscar);

        modelo = new DefaultTableModel(new Object[]{"Código", "Usuario", "Total"}, 0);
        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel lblTotal = new JLabel("Total:");
        txtTotal = new JTextField(10);
        txtTotal.setEditable(false);
        btnActualizar = new JButton();
        btnCancelar = new JButton();

        panelInferior.add(lblTotal);
        panelInferior.add(txtTotal);
        panelInferior.add(btnActualizar);
        panelInferior.add(btnCancelar);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(scroll, BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
    }

    public void actualizarIdioma() {
        setTitle(mensajeHandler.get("carrito.view.modificar.titulo"));
        btnBuscar.setText(mensajeHandler.get("carrito.view.modificar.buscar"));
        btnActualizar.setText(mensajeHandler.get("carrito.view.modificar.actualizar"));
        btnCancelar.setText(mensajeHandler.get("carrito.view.modificar.cancelar"));
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTable getTabla() {
        return tabla;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public JTextField getTxtTotal() {
        return txtTotal;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}

