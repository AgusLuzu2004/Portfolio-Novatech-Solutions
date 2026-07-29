package com.novatech.service;

import com.novatech.dao.ProductoDAO;
import com.novatech.model.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductoServiceTest {

    private ProductoDAO productoDAO;
    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        productoDAO = Mockito.mock(ProductoDAO.class);
        productoService = new ProductoService(productoDAO);
    }

    private Producto crearProductoValido() {

        Producto producto = new Producto();

        producto.setNombre("Notebook HP");
        producto.setMarca("HP");
        producto.setIdCategoria(1);
        producto.setPrecio(850000);
        producto.setStock(25);

        return producto;
    }

    @Test
    void deberiaGuardarProductoValido() {

        Producto producto = crearProductoValido();

        assertDoesNotThrow(() -> productoService.guardarProducto(producto));

        verify(productoDAO, times(1)).insertar(producto);
    }

    @Test
    void deberiaLanzarExcepcionSiNombreEstaVacio() {

        Producto producto = crearProductoValido();

        producto.setNombre("");

        assertThrows(
                IllegalArgumentException.class,
                () -> productoService.guardarProducto(producto)
        );

        verify(productoDAO, never()).insertar(any());
    }

    @Test
    void deberiaLanzarExcepcionSiMarcaEstaVacia() {

        Producto producto = crearProductoValido();

        producto.setMarca("");

        assertThrows(
                IllegalArgumentException.class,
                () -> productoService.guardarProducto(producto)
        );

        verify(productoDAO, never()).insertar(any());
    }

    @Test
    void deberiaLanzarExcepcionSiCategoriaEsInvalida() {

        Producto producto = crearProductoValido();

        producto.setIdCategoria(0);

        assertThrows(
                IllegalArgumentException.class,
                () -> productoService.guardarProducto(producto)
        );

        verify(productoDAO, never()).insertar(any());
    }

    @Test
    void deberiaLanzarExcepcionSiPrecioEsCero() {

        Producto producto = crearProductoValido();

        producto.setPrecio(0);

        assertThrows(
                IllegalArgumentException.class,
                () -> productoService.guardarProducto(producto)
        );

        verify(productoDAO, never()).insertar(any());
    }

    @Test
    void deberiaLanzarExcepcionSiPrecioEsNegativo() {

        Producto producto = crearProductoValido();

        producto.setPrecio(-100);

        assertThrows(
                IllegalArgumentException.class,
                () -> productoService.guardarProducto(producto)
        );

        verify(productoDAO, never()).insertar(any());
    }

    @Test
    void deberiaLanzarExcepcionSiStockEsNegativo() {

        Producto producto = crearProductoValido();

        producto.setStock(-5);

        assertThrows(
                IllegalArgumentException.class,
                () -> productoService.guardarProducto(producto)
        );

        verify(productoDAO, never()).insertar(any());
    }
}
