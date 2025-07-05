package ec.edu.ups.vista.Carrito;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ListarCarritoView extends JInternalFrame {

    private final CarritoController carritoController;
    private final MensajeInternacionalizacionHandler mensajeHandler;

    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JButton btnListar;
    private JTable tablaCarrito;
    private JTextField txtTotal;
    private DefaultTableModel modelo;

    public ListarCarritoView(CarritoController controller, MensajeInternacionalizacionHandler mensajeHandler) {
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

        btnListar.addActionListener(e -> carritoController.listarCarritos());
    }

    private void initComponents() {
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel(new FlowLayout());
        txtCodigo = new JTextField(10);
        btnListar = new JButton();
        panelSuperior.add(new JLabel("Código:"));
        panelSuperior.add(txtCodigo);
        panelSuperior.add(btnListar);

        modelo = new DefaultTableModel(new Object[]{"Código", "Usuario", "Total"}, 0);
        tablaCarrito = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tablaCarrito);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        txtTotal = new JTextField(10);
        txtTotal.setEditable(false);
        panelInferior.add(new JLabel("Total:"));
        panelInferior.add(txtTotal);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(scroll, BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
    }

    public void actualizarIdioma() {
        setTitle(mensajeHandler.get("carrito.view.listar.titulo"));
        btnListar.setText(mensajeHandler.get("carrito.view.listar.listar"));
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public JTable getTablaCarrito() {
        return tablaCarrito;
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


