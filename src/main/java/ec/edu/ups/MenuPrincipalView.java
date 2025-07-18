package ec.edu.ups;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;

public class MenuPrincipalView extends JFrame {

    private final MensajeInternacionalizacionHandler mensajeHandler;
    private final MiJDesktopPane desktopPane;

    private JMenuBar menuBar;
    private JMenu menuProducto, menuCarrito, menuUsuario, menuIdioma;
    private JMenuItem menuItemCrearProducto, menuItemEliminarProducto, menuItemActualizarProducto, menuItemBuscarProducto;
    private JMenuItem menuItemCrearCarrito, menuItemListarCarritos, menuItemModificarCarrito, menuItemEliminarCarrito;
    private JMenuItem menuItemRegistrarUsuario, menuItemListarUsuarios, menuItemModificarUsuario, menuItemEliminarUsuario, menuItemCerrarSesion;
    private JMenuItem menuItemIdiomaEspanol, menuItemIdiomaIngles, menuItemIdiomaFrances;

    public MenuPrincipalView(MensajeInternacionalizacionHandler mh, MiJDesktopPane desktop) {
        this.mensajeHandler = mh;
        this.desktopPane = desktop;

        // El orden es crucial: Primero inicializa componentes, luego añade listeners.
        initComponents();
        attachIdiomaListeners();

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initComponents() {
        setContentPane(desktopPane);
        menuBar = new JMenuBar();

        // --- Creación Manual del Menú Producto ---
        menuProducto = new JMenu();
        menuItemCrearProducto = new JMenuItem();
        menuItemActualizarProducto = new JMenuItem();
        menuItemEliminarProducto = new JMenuItem();
        menuItemBuscarProducto = new JMenuItem();
        menuProducto.add(menuItemCrearProducto);
        menuProducto.add(menuItemActualizarProducto);
        menuProducto.add(menuItemEliminarProducto);
        menuProducto.add(menuItemBuscarProducto);
        menuBar.add(menuProducto);

        // --- Creación Manual del Menú Carrito ---
        menuCarrito = new JMenu();
        menuItemCrearCarrito = new JMenuItem();
        menuItemListarCarritos = new JMenuItem();
        menuItemModificarCarrito = new JMenuItem();
        menuItemEliminarCarrito = new JMenuItem();
        menuCarrito.add(menuItemCrearCarrito);
        menuCarrito.add(menuItemListarCarritos);
        menuCarrito.add(menuItemModificarCarrito);
        menuCarrito.add(menuItemEliminarCarrito);
        menuBar.add(menuCarrito);

        // --- Creación Manual del Menú Usuario ---
        menuUsuario = new JMenu();
        menuItemRegistrarUsuario = new JMenuItem();
        menuItemListarUsuarios = new JMenuItem();
        menuItemModificarUsuario = new JMenuItem();
        menuItemEliminarUsuario = new JMenuItem();
        menuItemCerrarSesion = new JMenuItem();
        menuUsuario.add(menuItemRegistrarUsuario);
        menuUsuario.add(menuItemListarUsuarios);
        menuUsuario.add(menuItemModificarUsuario);
        menuUsuario.add(menuItemEliminarUsuario);
        menuUsuario.addSeparator();
        menuUsuario.add(menuItemCerrarSesion);
        menuBar.add(menuUsuario);

        // --- Creación Manual del Menú Idioma ---
        menuIdioma = new JMenu();
        menuItemIdiomaEspanol = new JMenuItem();
        menuItemIdiomaIngles = new JMenuItem();
        menuItemIdiomaFrances = new JMenuItem();
        menuIdioma.add(menuItemIdiomaEspanol);
        menuIdioma.add(menuItemIdiomaIngles);
        menuIdioma.add(menuItemIdiomaFrances);
        menuBar.add(menuIdioma);

        setJMenuBar(menuBar);
        actualizarTextos(); // Llama para establecer los textos por primera vez
    }

    private void attachIdiomaListeners() {
        menuItemIdiomaEspanol.addActionListener(e -> cambiarIdioma("es", "EC"));
        menuItemIdiomaIngles.addActionListener(e -> cambiarIdioma("en", "US"));
        menuItemIdiomaFrances.addActionListener(e -> cambiarIdioma("fr", "FR"));
    }

    public void cambiarIdioma(String lang, String country) {
        mensajeHandler.setLenguaje(lang, country);
        actualizarTextos();

        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            try {
                Method m = frame.getClass().getMethod("actualizarIdioma");
                m.invoke(frame);
            } catch (Exception ignored) {}
        }
    }

    private void actualizarTextos() {
        setTitle(mensajeHandler.get("app.titulo"));
        menuProducto.setText(mensajeHandler.get("menu.producto"));
        menuCarrito.setText(mensajeHandler.get("menu.carrito"));
        menuUsuario.setText(mensajeHandler.get("menu.usuario"));
        menuIdioma.setText(mensajeHandler.get("menu.idioma"));

        menuItemCrearProducto.setText(mensajeHandler.get("producto.crear"));
        menuItemActualizarProducto.setText(mensajeHandler.get("producto.actualizar"));
        menuItemEliminarProducto.setText(mensajeHandler.get("producto.eliminar"));
        menuItemBuscarProducto.setText(mensajeHandler.get("producto.buscar"));

        menuItemCrearCarrito.setText(mensajeHandler.get("carrito.crear"));
        menuItemListarCarritos.setText(mensajeHandler.get("carrito.buscar"));
        menuItemModificarCarrito.setText(mensajeHandler.get("carrito.modificar"));
        menuItemEliminarCarrito.setText(mensajeHandler.get("carrito.eliminar"));

        menuItemRegistrarUsuario.setText(mensajeHandler.get("usuario.registro.titulo"));
        menuItemListarUsuarios.setText(mensajeHandler.get("usuario.listar"));
        menuItemModificarUsuario.setText(mensajeHandler.get("usuario.modificar"));
        menuItemEliminarUsuario.setText(mensajeHandler.get("usuario.eliminar"));
        menuItemCerrarSesion.setText(mensajeHandler.get("usuario.cerrarSesion"));

        menuItemIdiomaEspanol.setText(mensajeHandler.get("menu.idioma.es"));
        menuItemIdiomaIngles.setText(mensajeHandler.get("menu.idioma.en"));
        menuItemIdiomaFrances.setText(mensajeHandler.get("menu.idioma.fr"));
    }

    public MiJDesktopPane getDesktopPane() {
        return desktopPane;
    }

    public void mostrarMensaje(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public void deshabilitarMenusAdministrador() {
        // En lugar de ocultar todo el menú, es mejor deshabilitar las opciones de admin
        menuProducto.setVisible(false); // Oculta gestión de productos para usuarios normales
        menuItemRegistrarUsuario.setVisible(false);
        menuItemListarUsuarios.setVisible(false);
        menuItemModificarUsuario.setVisible(false);
        menuItemEliminarUsuario.setVisible(false);
    }

    // --- GETTERS COMPLETOS PARA TODOS LOS ITEMS DEL MENÚ ---
    public JMenuItem getMenuItemCrearProducto()   { return menuItemCrearProducto; }
    public JMenuItem getMenuItemBuscarProducto()  { return menuItemBuscarProducto; }
    public JMenuItem getMenuItemActualizarProducto() { return menuItemActualizarProducto; }
    public JMenuItem getMenuItemEliminarProducto()  { return menuItemEliminarProducto; }
    public JMenuItem getMenuItemCrearCarrito()    { return menuItemCrearCarrito; }
    public JMenuItem getMenuItemListarCarritos()  { return menuItemListarCarritos; }
    public JMenuItem getMenuItemModificarCarrito() { return menuItemModificarCarrito; }
    public JMenuItem getMenuItemEliminarCarrito() { return menuItemEliminarCarrito; }
    public JMenuItem getMenuItemRegistrarUsuario(){ return menuItemRegistrarUsuario; }
    public JMenuItem getMenuItemListarUsuarios()  { return menuItemListarUsuarios; }
    public JMenuItem getMenuItemModificarUsuario(){ return menuItemModificarUsuario; }
    public JMenuItem getMenuItemEliminarUsuario() { return menuItemEliminarUsuario; }
    public JMenuItem getMenuItemCerrarSesion()    { return menuItemCerrarSesion; }
}