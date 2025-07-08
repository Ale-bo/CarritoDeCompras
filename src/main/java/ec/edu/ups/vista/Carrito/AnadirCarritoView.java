package ec.edu.ups.vista.Carrito;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;

public class AnadirCarritoView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JTextField txtCantidad;
    private JButton btnAceptar;
    private JButton btnLimpiar;
    private JLabel DatosDelProducto;
    private JLabel Codigo;
    private JLabel Precio;
    private JLabel Cantidad;
    private JButton btnBuscar;
    private JButton btnAñadir;
    private JButton btnGuardar;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTable tableCarrito;
    private JTextField txtTotal;
    private JTextField txtIva;
    private JTextField txtSubtotal;
    private JLabel Nombre;
    private JLabel lblCodigo;
    private JLabel lblCantidad;

    private CarritoController carritoController;
    private final MensajeInternacionalizacionHandler mensajes;

    public AnadirCarritoView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, true, true);
        this.mensajes = mensajes;

        initComponents(); // PRIMERO INICIALIZA COMPONENTES

        setContentPane(panelPrincipal);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        actualizarIdioma();
        pack();
    }

    // Inicializa TODOS los componentes
    private void initComponents() {
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(null); // Puedes cambiar por otro layout si prefieres

        // Etiquetas y campos de producto
        DatosDelProducto = new JLabel("Datos del Producto");
        DatosDelProducto.setBounds(20, 10, 200, 25);
        panelPrincipal.add(DatosDelProducto);

        Codigo = new JLabel("Código:");
        Codigo.setBounds(20, 45, 80, 25);
        panelPrincipal.add(Codigo);

        txtCodigo = new JTextField();
        txtCodigo.setBounds(100, 45, 100, 25);
        panelPrincipal.add(txtCodigo);

        Nombre = new JLabel("Nombre:");
        Nombre.setBounds(220, 45, 80, 25);
        panelPrincipal.add(Nombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(300, 45, 120, 25);
        panelPrincipal.add(txtNombre);

        Precio = new JLabel("Precio:");
        Precio.setBounds(20, 80, 80, 25);
        panelPrincipal.add(Precio);

        txtPrecio = new JTextField();
        txtPrecio.setBounds(100, 80, 100, 25);
        panelPrincipal.add(txtPrecio);

        Cantidad = new JLabel("Cantidad:");
        Cantidad.setBounds(220, 80, 80, 25);
        panelPrincipal.add(Cantidad);

        txtCantidad = new JTextField();
        txtCantidad.setBounds(300, 80, 120, 25);
        panelPrincipal.add(txtCantidad);

        // Botones principales
        btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(450, 45, 90, 25);
        panelPrincipal.add(btnBuscar);

        btnAñadir = new JButton("Añadir");
        btnAñadir.setBounds(450, 80, 90, 25);
        panelPrincipal.add(btnAñadir);

        btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(20, 120, 100, 30);
        panelPrincipal.add(btnAceptar);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(130, 120, 100, 30);
        panelPrincipal.add(btnLimpiar);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(240, 120, 100, 30);
        panelPrincipal.add(btnGuardar);

        // Tabla del carrito
        tableCarrito = new JTable();
        JScrollPane tableScroll = new JScrollPane(tableCarrito);
        tableScroll.setBounds(20, 170, 520, 120);
        panelPrincipal.add(tableScroll);

        // Subtotales, IVA y Total
        JLabel lblSubtotal = new JLabel("Subtotal:");
        lblSubtotal.setBounds(350, 300, 60, 25);
        panelPrincipal.add(lblSubtotal);

        txtSubtotal = new JTextField();
        txtSubtotal.setBounds(420, 300, 120, 25);
        panelPrincipal.add(txtSubtotal);

        JLabel lblIva = new JLabel("IVA:");
        lblIva.setBounds(350, 330, 60, 25);
        panelPrincipal.add(lblIva);

        txtIva = new JTextField();
        txtIva.setBounds(420, 330, 120, 25);
        panelPrincipal.add(txtIva);

        JLabel lblTotal = new JLabel("Total:");
        lblTotal.setBounds(350, 360, 60, 25);
        panelPrincipal.add(lblTotal);

        txtTotal = new JTextField();
        txtTotal.setBounds(420, 360, 120, 25);
        panelPrincipal.add(txtTotal);

        // Etiquetas internacionales para cambiar idioma
        lblCodigo = Codigo;        // Asocia a la label principal de código
        lblCantidad = Cantidad;    // Asocia a la label principal de cantidad
    }

    public void setCarritoController(CarritoController carritoController) {
        this.carritoController = carritoController;
        btnAceptar.addActionListener(e -> this.carritoController.crearCarrito());
        btnLimpiar.addActionListener(e -> limpiarCampos());
    }

    public void actualizarIdioma() {
        setTitle(mensajes.get("carrito.anadir.titulo"));
        lblCodigo.setText(mensajes.get("carrito.anadir.lbl.codigo"));
        lblCantidad.setText(mensajes.get("carrito.anadir.lbl.cantidad"));
        btnAceptar.setText(mensajes.get("carrito.anadir.btn.aceptar"));
        btnLimpiar.setText(mensajes.get("carrito.anadir.btn.limpiar"));
    }

    public String getCodigo() {
        return txtCodigo.getText();
    }

    public String getCantidad() {
        return txtCantidad.getText();
    }

    public JButton getBtnAceptar() {
        return btnAceptar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JTextField getTxtCantidad() {
        return txtCantidad;
    }

    public void mostrarMensaje(String clave) {
        JOptionPane.showMessageDialog(this, mensajes.get(clave));
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
        txtCantidad.setText("");
    }
}
