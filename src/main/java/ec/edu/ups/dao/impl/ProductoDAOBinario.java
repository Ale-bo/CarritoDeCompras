package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProductoDAOBinario implements ProductoDAO {

    private String rutaArchivo; // Ruta donde se guardará el archivo binario
    private List<Producto> productos; // Cache en memoria para operaciones

    public ProductoDAOBinario(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo + File.separator + "productos.dat"; // Define el nombre del archivo binario
        this.productos = new ArrayList<>();
        cargarProductosDesdeArchivo(); // Carga los productos existentes al inicializar
    }

    private void cargarProductosDesdeArchivo() {
        productos.clear(); // Limpiar la lista actual
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            return; // Si el archivo no existe, no hay productos que cargar
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            productos = (List<Producto>) ois.readObject();
        } catch (IOException e) {
            System.err.println("Error de E/S al leer el archivo binario de productos: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Error de clase no encontrada al leer el archivo binario de productos: " + e.getMessage());
        }
    }

    private void guardarProductosEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(productos);
        } catch (IOException e) {
            System.err.println("Error de E/S al escribir en el archivo binario de productos: " + e.getMessage());
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
            if (producto.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
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
        return new ArrayList<>(productos);
    }
}
