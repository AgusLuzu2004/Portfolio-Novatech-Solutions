package com.novatech.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.novatech.dao.ProductoDAO;
import com.novatech.model.Producto;

public class ProductoService {

    private ProductoDAO productoDAO;

    private static final Logger logger =
            LoggerFactory.getLogger(ProductoService.class);

    public ProductoService() {
        this.productoDAO = new ProductoDAO();
    }

    public ProductoService(ProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
    }

    public void guardarProducto(Producto producto) {

        try {

            validarProducto(producto);

            logger.info(
                    "Intentando guardar producto: {}, Marca: {}",
                    producto.getNombre(),
                    producto.getMarca());

            productoDAO.insertar(producto);

            logger.info(
                    "Producto guardado correctamente. ID: {}",
                    producto.getIdProducto());

        } catch (IllegalArgumentException e) {

            logger.warn(
                    "Error de validación al guardar producto: {}",
                    e.getMessage());

            throw e;

        } catch (Exception e) {

            logger.error("Error inesperado al guardar producto.", e);

            throw e;
        }
    }

    public void actualizarProducto(Producto producto) {

        try {

            validarProducto(producto);

            logger.info(
                    "Intentando actualizar producto ID {}: {}",
                    producto.getIdProducto(),
                    producto.getNombre());

            productoDAO.actualizar(producto);

            logger.info(
                    "Producto actualizado correctamente. ID: {}",
                    producto.getIdProducto());

        } catch (IllegalArgumentException e) {

            logger.warn(
                    "Error de validación al actualizar producto: {}",
                    e.getMessage());

            throw e;

        } catch (Exception e) {

            logger.error("Error inesperado al actualizar producto.", e);

            throw e;
        }
    }

    public void eliminarProducto(int id) {

        try {

            logger.info("Intentando eliminar producto con ID: {}", id);

            productoDAO.eliminar(id);

            logger.info("Producto eliminado correctamente. ID: {}", id);

        } catch (Exception e) {

            logger.error(
                    "Error al eliminar el producto con ID: {}",
                    id,
                    e);

            throw e;
        }
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

        if (producto.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a cero.");
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

    public List<Producto> buscarPorCategoria(String nombreCategoria) {
        return productoDAO.buscarPorCategoriaNombre(nombreCategoria);
    }

    public List<String> obtenerCategorias() {
        return productoDAO.obtenerNombresCategorias();
    }
}
