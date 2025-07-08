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

        // Asegúrate de inicializar el panel y los componentes si no usas diseñador
        if (panelPrincipal == null) {
            panelPrincipal = new JPanel(new BorderLayout());

            // Panel superior de búsqueda
            JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
            lblBuscar = new JLabel("Buscar:");
            txtBuscar = new JTextField(15);
            btnBuscar = new JButton("Buscar");
            btnListar = new JButton("Listar");
            panelTop.add(lblBuscar);
            panelTop.add(txtBuscar);
            panelTop.add(btnBuscar);
            panelTop.add(btnListar);

            panelPrincipal.add(panelTop, BorderLayout.NORTH);

            // Tabla
            modelo = new DefaultTableModel();
            tablaCarritos = new JTable(modelo);
            JScrollPane scrollPane = new JScrollPane(tablaCarritos);
            panelPrincipal.add(scrollPane, BorderLayout.CENTER);

            // Panel inferior de total
            JPanel panelBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JLabel lblTotal = new JLabel("Total Carrito:");
            textTotal = new JTextField(10);
            panelBottom.add(lblTotal);
            panelBottom.add(textTotal);

            panelPrincipal.add(panelBottom, BorderLayout.SOUTH);
        } else {
            // Si usas diseñador, asegúrate de inicializar el modelo y asignarlo
            if (tablaCarritos == null) {
                // Busca la tabla en el diseñador con otro nombre (ejemplo: table1)
                // Si no la encuentras, crea una por defecto
                modelo = new DefaultTableModel();
                tablaCarritos = new JTable(modelo);
                JScrollPane scrollPane = new JScrollPane(tablaCarritos);
                panelPrincipal.add(scrollPane, BorderLayout.CENTER);
            } else {
                modelo = (DefaultTableModel) tablaCarritos.getModel();
            }
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
                mensajes.get("carrito.listar.tabla.column.nombre"),
                mensajes.get("carrito.listar.tabla.column.cantidad"),
                mensajes.get("carrito.listar.tabla.column.precio")
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
}
