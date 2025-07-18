package ec.edu.ups;

import ec.edu.ups.controlador.*;
import ec.edu.ups.dao.*;
import ec.edu.ups.dao.impl.*;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.Carrito.*;
import ec.edu.ups.vista.LoginView;
import ec.edu.ups.vista.InicioDeSesion.*;
import ec.edu.ups.vista.Producto.*;
import ec.edu.ups.vista.Usuario.*;
import javax.swing.*;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.MessageFormat;
import java.io.File;

/**
 * Clase que representa un Usuario en el sistema de carrito de compras.
 * Implementa Serializable para permitir el almacenamiento binario.
 * @author Ivanna Alexandra Nievecela Pérez
 */
public class Main {
    private static UsuarioController usuarioCtrl;

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            MensajeInternacionalizacionHandler msgH = new MensajeInternacionalizacionHandler("es", "EC");

            // Vistas Principales y de Login
            MiJDesktopPane desktop = new MiJDesktopPane();
            MenuPrincipalView principal = new MenuPrincipalView(msgH, desktop);
            LoginView loginView = new LoginView(msgH);
            RegistrarUsuarioView registrarFrameView = new RegistrarUsuarioView(); // JFrame para registro inicial

            // Vistas Internas (JInternalFrame)
            RegistroView registrarInternalView = new RegistroView(msgH);
            ListarUsuarioView listarUserView = new ListarUsuarioView(msgH);
            EliminarUsuarioView eliminarUserView = new EliminarUsuarioView(msgH);
            ActualizarUsuarioView modUserView = new ActualizarUsuarioView(msgH);
            AnadirProductoView addProdView = new AnadirProductoView(msgH);
            ListarProductoView listProdView = new ListarProductoView(msgH);
            ActualizarProductoView modProdView = new ActualizarProductoView(msgH);
            EliminarProductoView delProdView = new EliminarProductoView(msgH);
            AnadirCarritoView addCartView = new AnadirCarritoView(msgH);
            ListarCarritoView listCartView = new ListarCarritoView(msgH);
            EliminarCarritoView delCartView = new EliminarCarritoView(msgH);
            ActualizarCarritoView modCartView = new ActualizarCarritoView(msgH);

            // --- Instancia INICIAL del controlador de usuario (con DAOs en memoria por defecto) ---
            // Esto es para que loginView.addWindowListener tenga un controlador válido desde el principio
            UsuarioDAO usuarioDAOInicial = new UsuarioDAOMemoria();
            ProductoDAO productoDAOInicial = new ProductoDAOMemoria(); // Necesario para CarritoController inicial si se usa
            CarritoDAO carritoDAOInicial = new CarritoDAOMemoria(); // Necesario para CarritoController inicial si se usa

            usuarioCtrl = new UsuarioController(usuarioDAOInicial, loginView, registrarFrameView, listarUserView, eliminarUserView, modUserView, principal, msgH, registrarInternalView);

            // --- ENLACE DE VISTAS CON SUS CONTROLADORES INICIALES (solo si es necesario antes del login) ---
            loginView.setUsuarioController(usuarioCtrl);
            // Otros controladores (productoCtrl, carritoCtrl) no necesitan ser inicializados y enlazados aquí
            // porque solo se usan DENTRO de la ventana principal, después del login.
            // Se instanciarán y enlazarán después de la selección del tipo de almacenamiento.


            // --- LÓGICA DE FLUJO DE LA APLICACIÓN (DESPUÉS DEL LOGIN) ---
            loginView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    // Ahora usuarioCtrl es accesible aquí sin error
                    Usuario u = usuarioCtrl.getUsuarioActual();
                    if (u == null) { // Si se cierra la ventana de login sin autenticar
                        System.exit(0);
                        return;
                    }

                    // --- Configuración de DAOs basada en la selección del usuario ---
                    UsuarioDAO usuarioDAO;
                    ProductoDAO productoDAO;
                    CarritoDAO carritoDAO;

                    String tipoAlmacenamiento = (String) loginView.getComboTipoAlmacenamiento().getSelectedItem();
                    String rutaAlmacenamiento = loginView.getTxtRutaAlmacenamiento().getText();

                    try {
                        if (tipoAlmacenamiento.equals(msgH.get("login.almacenamiento.memoria"))) {
                            usuarioDAO = new UsuarioDAOMemoria();
                            productoDAO = new ProductoDAOMemoria();
                            carritoDAO = new CarritoDAOMemoria();
                            System.out.println("Almacenamiento: En Memoria");
                        } else {
                            // Validar la ruta de almacenamiento si se eligió archivo
                            if (rutaAlmacenamiento.isEmpty()) {
                                JOptionPane.showMessageDialog(loginView, msgH.get("login.label.ruta") + " no puede estar vacía si el almacenamiento es por archivo.");
                                main(null); // Reinicia la aplicación para volver al login
                                return;
                            }
                            File rutaDir = new File(rutaAlmacenamiento);
                            // Intenta crear el directorio si no existe. Mkdirs crea directorios padres si no existen.
                            if (!rutaDir.exists() && !rutaDir.mkdirs()) {
                                JOptionPane.showMessageDialog(loginView, "No se pudo crear o acceder a la ruta de almacenamiento: " + rutaAlmacenamiento + ". Verifique permisos.");
                                main(null); // Reinicia
                                return;
                            }
                            if (!rutaDir.isDirectory() || !rutaDir.canWrite()) {
                                JOptionPane.showMessageDialog(loginView, "La ruta seleccionada no es un directorio válido o no tiene permisos de escritura.");
                                main(null); // Reinicia
                                return;
                            }

                            if (tipoAlmacenamiento.equals(msgH.get("login.almacenamiento.texto"))) {
                                usuarioDAO = new UsuarioDAOTexto(rutaAlmacenamiento);
                                productoDAO = new ProductoDAOTexto(rutaAlmacenamiento);
                                carritoDAO = new CarritoDAOTexto(rutaAlmacenamiento);
                                System.out.println("Almacenamiento: Archivos de Texto en " + rutaAlmacenamiento);
                            } else if (tipoAlmacenamiento.equals(msgH.get("login.almacenamiento.binario"))) {
                                usuarioDAO = new UsuarioDAOBinario(rutaAlmacenamiento);
                                productoDAO = new ProductoDAOBinario(rutaAlmacenamiento);
                                carritoDAO = new CarritoDAOBinario(rutaAlmacenamiento);
                                System.out.println("Almacenamiento: Archivos Binarios en " + rutaAlmacenamiento);
                            } else {
                                // Fallback a memoria si hay una selección inesperada
                                usuarioDAO = new UsuarioDAOMemoria();
                                productoDAO = new ProductoDAOMemoria();
                                carritoDAO = new CarritoDAOMemoria();
                                JOptionPane.showMessageDialog(loginView, "Tipo de almacenamiento desconocido. Usando memoria.");
                                System.out.println("Almacenamiento: Fallback a Memoria");
                            }
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(loginView, "Error al configurar el almacenamiento: " + ex.getMessage() + "\nReiniciando.");
                        System.err.println("Error al configurar almacenamiento: " + ex.getMessage());
                        main(null); // Reinicia la aplicación en caso de error crítico de configuración
                        return;
                    }

                    // --- RE-CREACIÓN DE CONTROLADORES con los DAOs seleccionados ---
                    // Ahora, re-instanciamos usuarioCtrl (el campo estático)
                    // y los otros controladores con los DAOs que el usuario eligió.
                    usuarioCtrl = new UsuarioController(usuarioDAO, loginView, registrarFrameView, listarUserView, eliminarUserView, modUserView, principal, msgH, registrarInternalView);
                    ProductoController productoCtrl = new ProductoController(productoDAO, msgH, addProdView, listProdView, delProdView, modProdView);
                    CarritoController carritoCtrl = new CarritoController(carritoDAO, productoDAO, msgH, addCartView, listCartView, delCartView, modCartView);

                    // --- RE-ENLACE DE VISTAS CON SUS CONTROLADORES ACTUALIZADOS ---
                    // Esto es CRUCIAL, ya que los DAOs dentro de los controladores han cambiado.
                    loginView.setUsuarioController(usuarioCtrl);
                    addProdView.setProductoController(productoCtrl);
                    listProdView.setProductoController(productoCtrl);
                    delProdView.setProductoController(productoCtrl);
                    modProdView.setProductoController(productoCtrl);
                    addCartView.setCarritoController(carritoCtrl);
                    listCartView.setCarritoController(carritoCtrl);
                    delCartView.setCarritoController(carritoCtrl);
                    modCartView.setCarritoController(carritoCtrl);


                    // --- LÓGICA POST-LOGIN ---
                    principal.setVisible(true);
                    principal.mostrarMensaje(MessageFormat.format(msgH.get("info.bienvenida"), u.getUsername()));

                    if (u.getRol() == Rol.USUARIO) {
                        principal.deshabilitarMenusAdministrador();
                    }

                    // --- CONFIGURACIÓN DE LOS MENÚS (Mantienen los mismos listeners, pero operan sobre los nuevos controladores) ---
                    principal.getMenuItemCrearProducto().addActionListener(e1 -> mostrarVentana(principal, addProdView));
                    principal.getMenuItemBuscarProducto().addActionListener(e1 -> mostrarVentana(principal, listProdView));
                    principal.getMenuItemActualizarProducto().addActionListener(e1 -> mostrarVentana(principal, modProdView));
                    principal.getMenuItemEliminarProducto().addActionListener(e1 -> mostrarVentana(principal, delProdView));

                    principal.getMenuItemCrearCarrito().addActionListener(e1 -> mostrarVentana(principal, addCartView));
                    principal.getMenuItemListarCarritos().addActionListener(e1 -> mostrarVentana(principal, listCartView));
                    principal.getMenuItemModificarCarrito().addActionListener(e1 -> mostrarVentana(principal, modCartView));
                    principal.getMenuItemEliminarCarrito().addActionListener(e1 -> mostrarVentana(principal, delCartView));

                    principal.getMenuItemRegistrarUsuario().addActionListener(e1 -> mostrarVentana(principal, registrarInternalView));
                    principal.getMenuItemListarUsuarios().addActionListener(e1 -> mostrarVentana(principal, listarUserView));
                    principal.getMenuItemModificarUsuario().addActionListener(e1 -> mostrarVentana(principal, modUserView));
                    principal.getMenuItemEliminarUsuario().addActionListener(e1 -> mostrarVentana(principal, eliminarUserView));

                    principal.getMenuItemCerrarSesion().addActionListener(e1 -> {
                        principal.dispose();
                        main(null); // Reinicia la aplicación para volver al login
                    });
                }
            });

            // Inicia la aplicación mostrando la ventana de login
            loginView.setVisible(true);
        });
    }

    /**
     * Método auxiliar para mostrar una ventana interna (JInternalFrame).
     * Si la ventana no es visible, la añade al panel principal y la muestra.
     * Si ya es visible, simplemente la trae al frente.
     */
    private static void mostrarVentana(MenuPrincipalView p, JInternalFrame v) {
        if (!v.isVisible()) {
            p.getDesktopPane().add(v);
            v.setVisible(true);
        }
        try {
            v.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            System.err.println("Error al seleccionar la ventana: " + e.getMessage());
        }
        v.toFront();
    }
}