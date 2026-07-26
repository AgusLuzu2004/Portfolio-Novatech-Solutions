package com.novatech.service;

import java.util.List;

import com.novatech.dao.ProductoDAO;
import com.novatech.model.Producto;

public class ProductoService {

    private ProductoDAO productoDAO = new ProductoDAO();

    public void guardarProducto(Producto producto) {

        validarProducto(producto);

        productoDAO.insertar(producto);

    }

    public void actualizarProducto(Producto producto) {

        validarProducto(producto);

        productoDAO.actualizar(producto);

    }

    public void eliminarProducto(int id) {

        productoDAO.eliminar(id);

    }

    private void validarProducto(Producto producto) {

        if (producto.getNombre() == null || producto.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }

        if (producto.getMarca() == null || producto.getMarca().isBlank()) {
            throw new IllegalArgumentException("La marca es obligatoria.");
        }

        if (producto.getIdCategoria() <= 0) {
            throw new IllegalArgumentException("Debe seleccionar una categoría válida.");
        }

        if (producto.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }

        if (producto.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }
    }

    public List<Producto> obtenerTodos() {
        return productoDAO.listarProductos();
    }

    public List<Producto> buscar(String texto) {
        return productoDAO.buscarPorNombre(texto);
    }

    public List<Producto> buscarPorMarca(String marca) {
        return productoDAO.buscarPorMarca(marca);
    }

    public List<String> obtenerMarcas() {
        return productoDAO.obtenerMarcas();
    }
}
