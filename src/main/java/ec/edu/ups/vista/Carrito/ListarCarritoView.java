package ec.edu.ups.vista.Carrito;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ListarCarritoView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JLabel lblBuscar;
    private JTextField txtBuscar;
    private JButton btnListar;
    private JButton btnBuscar;
    private JPanel panel1;
    private JTable tablaCarritos;

    private DefaultTableModel modelo;
    private final MensajeInternacionalizacionHandler mensajes;
    private CarritoController carritoController;

    public ListarCarritoView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, true, true);
        this.mensajes = mensajes;

        setContentPane(panelPrincipal);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();

        modelo = (DefaultTableModel) tablaCarritos.getModel();
        actualizarIdioma();
    }

    public void setCarritoController(CarritoController carritoController){
        this.carritoController = carritoController;
    }

    public void actualizarIdioma() {
        setTitle(mensajes.get("carrito.listar.titulo"));
        lblBuscar.setText(mensajes.get("carrito.listar.lbl.buscar"));
        btnBuscar.setText(mensajes.get("carrito.listar.btn.buscar"));
        btnListar.setText(mensajes.get("carrito.listar.btn.listar"));

        modelo.setColumnIdentifiers(new Object[]{
                mensajes.get("carrito.listar.tabla.column.codigo"),
                mensajes.get("carrito.listar.tabla.column.fecha"),
                mensajes.get("carrito.listar.tabla.column.total")
        });
    }

    public String getTxtBuscar() {
        return txtBuscar.getText();
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public JTable getTablaCarritos() {
        return tablaCarritos;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}