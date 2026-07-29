package com.novatech.service;

import com.novatech.dao.ProductoDAO;
import com.novatech.dao.VentaDAO;
import com.novatech.model.Cliente;
import com.novatech.model.DetalleVenta;
import com.novatech.model.Empleado;
import com.novatech.model.Producto;
import com.novatech.model.Venta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class VentaServiceTest {

    private VentaDAO ventaDAO;
    private ProductoDAO productoDAO;
    private VentaService ventaService;

    @BeforeEach
    void setUp() {

        ventaDAO = Mockito.mock(VentaDAO.class);
        productoDAO = Mockito.mock(ProductoDAO.class);

        ventaService = new VentaService(
                ventaDAO,
                productoDAO
        );
    }

    private Venta crearVentaValida() {

        Producto producto = new Producto();

        producto.setIdProducto(1);
        producto.setNombre("Notebook");
        producto.setPrecio(800000);
        producto.setStock(20);

        DetalleVenta detalle = new DetalleVenta();

        detalle.setProducto(producto);
        detalle.setCantidad(2);
        detalle.setPrecioUnitario(800000);

        Venta venta = new Venta();

        venta.setCliente(new Cliente());
        venta.setEmpleado(new Empleado());
        venta.setDetalles(List.of(detalle));
        venta.setDescuento(10);

        return venta;

    }

    @Test
    void deberiaRegistrarVentaCorrectamente() {

        Venta venta = crearVentaValida();

        when(productoDAO.buscarPorId(1))
                .thenReturn(
                        venta.getDetalles().get(0).getProducto()
                );

        assertDoesNotThrow(
                () -> ventaService.registrarVenta(venta)
        );

        verify(ventaDAO).registrarVenta(venta);

    }

    @Test
    void deberiaLanzarExcepcionSiNoHayCliente() {

        Venta venta = crearVentaValida();

        venta.setCliente(null);

        assertThrows(
                IllegalArgumentException.class,
                () -> ventaService.registrarVenta(venta)
        );

    }

    @Test
    void deberiaLanzarExcepcionSiNoHayEmpleado() {

        Venta venta = crearVentaValida();

        venta.setEmpleado(null);

        assertThrows(
                IllegalArgumentException.class,
                () -> ventaService.registrarVenta(venta)
        );

    }

    @Test
    void deberiaLanzarExcepcionSiNoHayProductos() {

        Venta venta = crearVentaValida();

        venta.setDetalles(List.of());

        assertThrows(
                IllegalArgumentException.class,
                () -> ventaService.registrarVenta(venta)
        );

    }

    @Test
    void deberiaLanzarExcepcionSiCantidadEsCero() {

        Venta venta = crearVentaValida();

        venta.getDetalles().get(0).setCantidad(0);

        assertThrows(
                IllegalArgumentException.class,
                () -> ventaService.registrarVenta(venta)
        );

    }

    @Test
    void deberiaLanzarExcepcionSiNoHayStock() {

        Venta venta = crearVentaValida();

        venta.getDetalles().get(0)
                .getProducto()
                .setStock(1);

        venta.getDetalles().get(0)
                .setCantidad(5);

        assertThrows(
                IllegalArgumentException.class,
                () -> ventaService.registrarVenta(venta)
        );

    }

    @Test
    void deberiaLanzarExcepcionSiDescuentoMayorA30() {

        Venta venta = crearVentaValida();

        venta.setDescuento(35);

        assertThrows(
                IllegalArgumentException.class,
                () -> ventaService.registrarVenta(venta)
        );

    }

    @Test
    void deberiaCalcularTotalCorrectamente() {

        Venta venta = crearVentaValida();

        double total = ventaService.calcularTotal(venta);

        assertEquals(
                1440000,
                total,
                0.01
        );

    }

    @Test
    void deberiaActualizarStock() {

        Venta venta = crearVentaValida();

        when(productoDAO.buscarPorId(1))
                .thenReturn(
                        venta.getDetalles().get(0).getProducto()
                );

        ventaService.registrarVenta(venta);

        verify(productoDAO)
                .actualizar(any());

    }

}
