package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProductoDAOTexto implements ProductoDAO {

    private String rutaArchivo; // Ruta donde se guardará el archivo de texto
    private List<Producto> productos; // Cache en memoria para operaciones

    public ProductoDAOTexto(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo + File.separator + "productos.txt"; // Define el nombre del archivo
        this.productos = new ArrayList<>();
        cargarProductosDesdeArchivo(); // Carga los productos existentes al inicializar
    }

    private void cargarProductosDesdeArchivo() {
        productos.clear(); // Limpiar la lista actual
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            return; // Si el archivo no existe, no hay productos que cargar
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            Producto producto = null;

            while ((linea = reader.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue; // Ignorar líneas vacías

                if (linea.equals("---")) { // Separador entre productos
                    if (producto != null) {
                        productos.add(producto);
                        producto = null; // Reiniciar para el siguiente producto
                    }
                } else {
                    // Aquí asumimos un orden estricto de lectura de atributos:
                    // codigo, nombre, precio
                    if (producto == null) {
                        producto = new Producto(0, "", 0.0); // Producto temporal para asignar atributos
                    }

                    if (producto.getCodigo() == 0) { // Si el código es el valor por defecto, es el primer atributo
                        try {
                            producto.setCodigo(Integer.parseInt(linea));
                        } catch (NumberFormatException e) {
                            System.err.println("Error de formato al cargar producto: código no numérico. Línea: " + linea);
                            producto = null; // Descartar este producto si hay error de formato
                        }
                    } else if (producto.getNombre().isEmpty()) { // Si el nombre está vacío, es el segundo atributo
                        producto.setNombre(linea);
                    } else if (producto.getPrecio() == 0.0) { // Si el precio es 0.0, es el tercer atributo
                        try {
                            producto.setPrecio(Double.parseDouble(linea));
                        } catch (NumberFormatException e) {
                            System.err.println("Error de formato al cargar producto: precio no numérico. Línea: " + linea);
                            producto = null; // Descartar este producto si hay error de formato
                        }
                    } else {
                        // Si llega aquí, significa que hemos leído todos los atributos de un producto
                        // Esto no debería suceder si el formato es estricto y hay un separador '---'
                        // antes de cada nuevo producto, pero como fallback, añadir el producto actual
                        productos.add(producto);
                        producto = new Producto(0, "", 0.0); // Reiniciar para la siguiente lectura
                        // Y tratar la línea actual como el primer atributo del nuevo producto
                        try {
                            producto.setCodigo(Integer.parseInt(linea));
                        } catch (NumberFormatException e) {
                            System.err.println("Error de formato al cargar producto: código no numérico. Línea: " + linea);
                            producto = null;
                        }
                    }
                }
            }
            // Añadir el último producto si el archivo no termina con un separador '---'
            if (producto != null && producto.getCodigo() != 0) { // Asegurar que es un producto válido y no solo el temporal inicializado
                productos.add(producto);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de productos: " + e.getMessage());
        }
    }

    private void guardarProductosEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Producto producto : productos) {
                writer.write(String.valueOf(producto.getCodigo()));
                writer.newLine();
                writer.write(producto.getNombre());
                writer.newLine();
                writer.write(String.valueOf(producto.getPrecio()));
                writer.newLine();
                writer.write("---"); // Separador entre productos
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de productos: " + e.getMessage());
        }
    }

    @Override
    public void crear(Producto producto) {
        cargarProductosDesdeArchivo(); // Asegurar que la caché esté actualizada
        productos.add(producto);
        guardarProductosEnArchivo();
    }

    @Override
    public Producto buscarPorCodigo(int codigo) {
        cargarProductosDesdeArchivo();
        for (Producto producto : productos) {
            if (producto.getCodigo() == codigo) {
                return producto;
            }
        }
        return null;
    }

    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        cargarProductosDesdeArchivo();
        List<Producto> productosEncontrados = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getNombre().toLowerCase().contains(nombre.toLowerCase())) { // Búsqueda más flexible
                productosEncontrados.add(producto);
            }
        }
        return productosEncontrados;
    }

    @Override
    public void actualizar(Producto producto) {
        cargarProductosDesdeArchivo();
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo() == producto.getCodigo()) {
                productos.set(i, producto);
                guardarProductosEnArchivo();
                return;
            }
        }
    }

    @Override
    public void eliminar(int codigo) {
        cargarProductosDesdeArchivo();
        Iterator<Producto> iterator = productos.iterator();
        while (iterator.hasNext()) {
            Producto p = iterator.next();
            if (p.getCodigo() == codigo) {
                iterator.remove();
                guardarProductosEnArchivo();
                return;
            }
        }
    }

    @Override
    public List<Producto> listarTodos() {
        cargarProductosDesdeArchivo();
        return new ArrayList<>(productos); // Devolver una copia para evitar modificación externa
    }
}
