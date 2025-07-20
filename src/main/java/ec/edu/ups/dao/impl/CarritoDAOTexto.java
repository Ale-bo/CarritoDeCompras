package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

/**
 * Implementación de la interfaz {@code CarritoDAO} para el almacenamiento de datos en archivos de texto plano.
 * Los carritos se guardan y cargan de un archivo de texto, con cada atributo en una línea separada y los carritos separados por "---".
 *
 * @author Ivanna Alexandra Nievecela Pérez
 * @version 1.0
 */
public class CarritoDAOTexto implements CarritoDAO {

    private String rutaArchivo; // Ruta donde se guardará el archivo de texto
    private List<Carrito> carritos; // Cache en memoria para operaciones

    /**
     * Constructor para inicializar el DAO de carritos para archivos de texto.
     * Carga los carritos existentes desde el archivo especificado al inicializar.
     * @param rutaBase La ruta base del directorio donde se almacenarán los archivos.
     */
    public CarritoDAOTexto(String rutaBase) {
        this.rutaArchivo = rutaBase + File.separator + "carritos.txt"; // Define el nombre del archivo
        this.carritos = new ArrayList<>();
        cargarCarritosDesdeArchivo(); // Carga los carritos existentes al inicializar
    }

    /**
     * Carga la lista de carritos desde el archivo de texto.
     * Limpia la caché actual y lee cada carrito línea por línea.
     */
    private void cargarCarritosDesdeArchivo() {
        carritos.clear(); // Limpiar la lista actual
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            return; // Si el archivo no existe, no hay carritos que cargar
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            Carrito carrito = null;
            List<ItemCarrito> itemsTemp = new ArrayList<>();
            boolean enItems = false;
            int atributoIndex = 0; // 0: codigo, 1: fechaCreacion (timestamp)

            while ((linea = reader.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue; // Ignorar líneas vacías

                if (linea.equals("---")) { // Separador entre carritos
                    if (carrito != null) {
                        carrito.obtenerItems().addAll(itemsTemp); // Añadir items al carrito
                        carritos.add(carrito);
                        carrito = null; // Reiniciar para el siguiente carrito
                        itemsTemp = new ArrayList<>();
                        enItems = false;
                        atributoIndex = 0; // Reiniciar índice de atributo para el siguiente carrito
                    }
                } else if (linea.equals("#ITEMS#")) {
                    enItems = true;
                } else if (linea.equals("#ENDITEMS#")) {
                    enItems = false;
                } else if (enItems) {
                    // Si estamos en la sección de items, leemos los detalles del producto y la cantidad
                    try {
                        int codigoProducto = Integer.parseInt(linea);
                        String nombreProducto = reader.readLine();
                        double precioProducto = Double.parseDouble(reader.readLine());
                        int cantidad = Integer.parseInt(reader.readLine());

                        Producto p = new Producto(codigoProducto, nombreProducto, precioProducto);
                        itemsTemp.add(new ItemCarrito(p, cantidad));

                    } catch (NumberFormatException | IOException e) {
                        System.err.println("Error de formato al cargar items del carrito: " + e.getMessage());
                        // Podríamos descartar el carrito si un item tiene formato inválido
                    }
                } else {
                    // Si no estamos en items y no es un separador, son los atributos del carrito principal
                    if (carrito == null) {
                        carrito = new Carrito(); // Usamos el constructor por defecto
                    }

                    // Lógica de lectura de atributos de Carrito por índice (orden estricto)
                    switch (atributoIndex) {
                        case 0: // Código del Carrito
                            try {
                                carrito.setCodigo(Integer.parseInt(linea));
                            } catch (NumberFormatException e) {
                                System.err.println("Error de formato al cargar carrito: código no numérico. Línea: " + linea);
                                carrito = null; // Descartar este carrito si hay error de formato
                            }
                            break;
                        case 1: // Fecha de Creación (timestamp)
                            try {
                                long timestamp = Long.parseLong(linea);
                                GregorianCalendar gc = new GregorianCalendar();
                                gc.setTimeInMillis(timestamp);
                                carrito.setFechaCreacion(gc);
                            } catch (NumberFormatException e) {
                                System.err.println("Error de formato al cargar carrito: timestamp no numérico. Línea: " + linea);
                                carrito = null; // Descartar si el timestamp no es válido
                            }
                            break;
                        // Si hubiera más atributos del carrito principal, se añadirían más 'case' aquí
                    }
                    atributoIndex++; // Avanzar al siguiente atributo
                }
            }
            // Añadir el último carrito si el archivo no termina con un separador '---'
            if (carrito != null && carrito.getCodigo() != 0) { // Asegurar que es un carrito válido y no solo el temporal inicializado
                carrito.obtenerItems().addAll(itemsTemp);
                carritos.add(carrito);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de carritos: " + e.getMessage());
        }
    }

    /**
     * Guarda la lista actual de carritos en el archivo de texto.
     */
    private void guardarCarritosEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Carrito carrito : carritos) {
                writer.write(String.valueOf(carrito.getCodigo()));
                writer.newLine();
                writer.write(String.valueOf(carrito.getFechaCreacion().getTimeInMillis())); // Guardar como timestamp
                writer.newLine();

                writer.write("#ITEMS#");
                writer.newLine();
                if (carrito.obtenerItems() != null) {
                    for (ItemCarrito item : carrito.obtenerItems()) {
                        writer.write(String.valueOf(item.getProducto().getCodigo()));
                        writer.newLine();
                        writer.write(item.getProducto().getNombre());
                        writer.newLine();
                        writer.write(String.valueOf(item.getProducto().getPrecio()));
                        writer.newLine();
                        writer.write(String.valueOf(item.getCantidad()));
                        writer.newLine();
                    }
                }
                writer.write("#ENDITEMS#");
                writer.newLine();
                writer.write("---"); // Separador entre carritos
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de carritos: " + e.getMessage());
        }
    }

    @Override
    public void crear(Carrito carrito) {
        cargarCarritosDesdeArchivo(); // Asegurar que la caché esté actualizada
        carritos.add(carrito);
        guardarCarritosEnArchivo();
    }

    @Override
    public Carrito buscarPorCodigo(int codigo) {
        cargarCarritosDesdeArchivo();
        for (Carrito carrito : carritos) {
            if (carrito.getCodigo() == codigo) {
                return carrito;
            }
        }
        return null;
    }

    @Override
    public void actualizar(Carrito carrito) {
        cargarCarritosDesdeArchivo();
        for (int i = 0; i < carritos.size(); i++) {
            if (carritos.get(i).getCodigo() == carrito.getCodigo()) {
                carritos.set(i, carrito);
                guardarCarritosEnArchivo();
                return;
            }
        }
    }

    @Override
    public void eliminar(int codigo) {
        cargarCarritosDesdeArchivo();
        Iterator<Carrito> iterator = carritos.iterator();
        while (iterator.hasNext()) {
            Carrito c = iterator.next();
            if (c.getCodigo() == codigo) {
                iterator.remove();
                guardarCarritosEnArchivo();
                return;
            }
        }
    }

    @Override
    public List<Carrito> listarTodos() {
        cargarCarritosDesdeArchivo();
        return new ArrayList<>(carritos);
    }
}
