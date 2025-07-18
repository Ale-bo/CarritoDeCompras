package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CarritoDAOBinario implements CarritoDAO {

    private String rutaArchivo; // Ruta donde se guardará el archivo binario
    private List<Carrito> carritos; // Cache en memoria para operaciones

    public CarritoDAOBinario(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo + File.separator + "carritos.dat"; // Define el nombre del archivo binario
        this.carritos = new ArrayList<>();
        cargarCarritosDesdeArchivo(); // Carga los carritos existentes al inicializar
    }

    private void cargarCarritosDesdeArchivo() {
        carritos.clear(); // Limpiar la lista actual
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            return; // Si el archivo no existe, no hay carritos que cargar
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            carritos = (List<Carrito>) ois.readObject();
        } catch (IOException e) {
            System.err.println("Error de E/S al leer el archivo binario de carritos: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Error de clase no encontrada al leer el archivo binario de carritos: " + e.getMessage());
        }
    }

    private void guardarCarritosEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(carritos);
        } catch (IOException e) {
            System.err.println("Error de E/S al escribir en el archivo binario de carritos: " + e.getMessage());
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
