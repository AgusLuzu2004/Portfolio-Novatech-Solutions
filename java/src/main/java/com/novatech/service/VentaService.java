package com.novatech.service;

import java.time.LocalDateTime;
import java.util.List;

import com.novatech.dao.ProductoDAO;
import com.novatech.dao.VentaDAO;
import com.novatech.model.DetalleVenta;
import com.novatech.model.Producto;
import com.novatech.model.Venta;

public class VentaService {
    private VentaDAO ventaDAO = new VentaDAO();
    private ProductoDAO productoDAO = new ProductoDAO();

    public boolean registrarVenta(Venta venta) {

        validarVenta(venta);

        calcularSubtotales(venta);

        calcularTotal(venta);

        verificarStock(venta);

        return ventaDAO.registrarVenta(venta);
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

    private void calcularTotal(Venta venta) {

        double total = 0;

        for (DetalleVenta detalle : venta.getDetalles()) {

            total += detalle.getSubtotal();

        }

        total *= (1 - venta.getDescuento() / 100);

        venta.setTotal(total);

    }

    private void verificarStock(Venta venta) {

        for (DetalleVenta detalle : venta.getDetalles()) {

            Producto producto = productoDAO.buscarPorId(detalle.getProducto().getIdProducto());

            if (producto == null) {
                throw new IllegalArgumentException("Producto no encontrado.");
            }

            if (detalle.getCantidad() > producto.getStock()) {
                throw new IllegalArgumentException(
                    "No hay suficiente stock para " + producto.getNombre());
            }

        }

    }

    public List<Venta> listarVentas() {

        return ventaDAO.listarVentas();

    }

}
