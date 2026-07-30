package com.novatech.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.novatech.dao.ProductoDAO;
import com.novatech.dao.VentaDAO;
import com.novatech.model.DetalleVenta;
import com.novatech.model.Producto;
import com.novatech.model.Venta;

public class VentaService {
    private VentaDAO ventaDAO;
    private ProductoDAO productoDAO;

    private static final Logger logger =
        LoggerFactory.getLogger(VentaService.class);

    public VentaService() {
        this.ventaDAO = new VentaDAO();
        this.productoDAO = new ProductoDAO();
    }

    public VentaService(VentaDAO ventaDAO, ProductoDAO productoDAO) {
        this.ventaDAO = ventaDAO;
        this.productoDAO = productoDAO;
    }

    public boolean registrarVenta(Venta venta) {

        try {

            logger.info("Iniciando registro de una nueva venta.");

            validarVenta(venta);

            logger.info(
                    "Cliente: {} | Empleado: {}",
                    venta.getCliente().getNombre(),
                    venta.getEmpleado().getNombre()
            );

            calcularSubtotales(venta);

            calcularTotal(venta);

            logger.info(
                    "Total calculado: {}",
                    venta.getTotal()
            );

            verificarStock(venta);

            boolean registrada = ventaDAO.registrarVenta(venta);

            if (registrada) {

                logger.info(
                        "Venta registrada correctamente. Total: {} | Productos: {}",
                        venta.getTotal(),
                        venta.getDetalles().size()
                );

            } else {

                logger.warn("La venta no pudo registrarse.");

            }

            return registrada;

        } catch (IllegalArgumentException e) {

            logger.warn(
                    "Error de validación al registrar venta: {}",
                    e.getMessage()
            );

            throw e;

        } catch (Exception e) {

            logger.error(
                    "Error inesperado al registrar la venta.",
                    e
            );

            throw e;
        }

    }

    private void validarVenta(Venta venta) {
        if (venta.getCliente() == null) {
            throw new IllegalArgumentException("El cliente es obligatorio.");
        }

        if (venta.getEmpleado() == null) {
            throw new IllegalArgumentException("El empleado es obligatorio.");
        }

        if (venta.getFecha() == null || venta.getFecha().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha de la venta es inválida.");
        }

        if (venta.getMedioPago() == null || venta.getMedioPago().isBlank()) {
            throw new IllegalArgumentException("El medio de pago es obligatorio.");
        }

        if (venta.getCanal() == null || venta.getCanal().isBlank()) {
            throw new IllegalArgumentException("El canal de venta es obligatorio.");
        }

        if (venta.getDescuento() < 0) {
            throw new IllegalArgumentException("El descuento no puede ser negativo.");
        }

        if (venta.getDetalles() == null || venta.getDetalles().isEmpty()) {
            throw new IllegalArgumentException("Debe agregar al menos un producto a la venta.");
        }
    }

    private void calcularSubtotales(Venta venta) {

        for (DetalleVenta detalle : venta.getDetalles()) {

            double subtotal =
                    detalle.getPrecioUnitario() *
                    detalle.getCantidad();

            detalle.setSubtotal(subtotal);
        }

    }

    double calcularTotal(Venta venta) {

        double total = 0;

        for (DetalleVenta detalle : venta.getDetalles()) {

            double subtotal = detalle.getPrecioUnitario() * detalle.getCantidad();
            detalle.setSubtotal(subtotal);

            total += subtotal;
        }

        total *= (1 - venta.getDescuento() / 100);

        venta.setTotal(total);

        return total;

    }

    private void verificarStock(Venta venta) {

        for (DetalleVenta detalle : venta.getDetalles()) {

            Producto producto = productoDAO.buscarPorId(detalle.getProducto().getIdProducto());

            if (producto == null) {

                logger.warn(
                        "Producto inexistente. ID: {}",
                        detalle.getProducto().getIdProducto()
                );

                throw new IllegalArgumentException("Producto no encontrado.");
            }

            if (detalle.getCantidad() > producto.getStock()) {

                logger.warn(
                        "Stock insuficiente para el producto {}. Disponible: {} - Solicitado: {}",
                        producto.getNombre(),
                        producto.getStock(),
                        detalle.getCantidad()
                );

                throw new IllegalArgumentException(
                        "No hay suficiente stock para " + producto.getNombre());
            }

        }

    }

    public List<Venta> listarVentas() {

        return ventaDAO.listarVentas();

    }

}
