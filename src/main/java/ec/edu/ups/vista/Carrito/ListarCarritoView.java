package ec.edu.ups.vista.Carrito;

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
    private JTextField textTotal;
    private JTable tablaCarritos;

    private DefaultTableModel modelo;
    private final MensajeInternacionalizacionHandler mensajes;

    public ListarCarritoView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, true, true);
        this.mensajes = mensajes;

        // Se asume que los componentes se inicializan desde el archivo .form
        // Si no usas el diseñador, aquí iría la creación manual de componentes.
        if (tablaCarritos != null) {
            modelo = (DefaultTableModel) tablaCarritos.getModel();
        } else {
            // Fallback en caso de que el JTable no se enlace desde el .form
            modelo = new DefaultTableModel();
            tablaCarritos = new JTable(modelo);
        }

        setContentPane(panelPrincipal);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        actualizarIdioma();
        pack();
    }

    public void actualizarIdioma() {
        setTitle(mensajes.get("carrito.listar.titulo"));
        lblBuscar.setText(mensajes.get("carrito.listar.lbl.buscar"));
        btnBuscar.setText(mensajes.get("carrito.listar.btn.buscar"));
        btnListar.setText(mensajes.get("carrito.listar.btn.listar"));

        modelo.setColumnIdentifiers(new Object[]{
                mensajes.get("carrito.listar.tabla.column.codigo"),
                "Fecha", // Ajustar si tienes clave de internacionalización
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

    // ##### MÉTODO AÑADIDO PARA SOLUCIONAR EL ERROR #####
    /**
     * Muestra un cuadro de diálogo con un mensaje para el usuario.
     * @param mensaje El texto que se mostrará en el diálogo.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}