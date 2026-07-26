package com.novatech.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.novatech.config.Conexion;
import com.novatech.model.DetalleVenta;
import com.novatech.model.Producto;
import com.novatech.model.Venta;

public class VentaDAO {

    private final ProductoDAO productoDAO = new ProductoDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final EmpleadoDAO empleadoDAO = new EmpleadoDAO();

    public boolean registrarVenta(Venta venta) {

        String sqlVenta =
                "INSERT INTO ventas " +
                "(id_cliente, id_producto, id_empleado, fecha, cantidad, precio_unitario, descuento, medio_pago, canal) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String sqlStock = "UPDATE productos SET stock = stock - ? WHERE id_producto = ? AND stock >= ?";

        try (Connection conexion = Conexion.conectar()) {

            conexion.setAutoCommit(false);

            try (
                PreparedStatement psVenta = conexion.prepareStatement(sqlVenta);
                PreparedStatement psStock = conexion.prepareStatement(sqlStock);
            ) {

                for (DetalleVenta detalle : venta.getDetalles()) {

                    psVenta.setInt(1, venta.getCliente().getIdCliente());
                    psVenta.setInt(2, detalle.getProducto().getIdProducto());
                    psVenta.setInt(3, venta.getEmpleado().getIdEmpleado());
                    psVenta.setDate(4, Date.valueOf(venta.getFecha().toLocalDate()));
                    psVenta.setInt(5, detalle.getCantidad());
                    psVenta.setDouble(6, detalle.getPrecioUnitario());
                    psVenta.setDouble(7, venta.getDescuento());
                    psVenta.setString(8, venta.getMedioPago());
                    psVenta.setString(9, venta.getCanal());
                    psVenta.executeUpdate();

                    psStock.setInt(1, detalle.getCantidad());
                    psStock.setInt(2, detalle.getProducto().getIdProducto());
                    psStock.setInt(3, detalle.getCantidad());

                    int filasActualizadas = psStock.executeUpdate();

                    if (filasActualizadas == 0) {
                        conexion.rollback();
                        return false;
                    }

                }

                conexion.commit();
                return true;

            } catch (Exception e) {
                conexion.rollback();
                e.printStackTrace();
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public List<Venta> listarVentas() {
        return listarConFiltro("SELECT * FROM ventas", null);
    }

    public Venta buscarPorId(int id) {
        List<Venta> resultado = listarConFiltro(
                "SELECT * FROM ventas WHERE id_venta = ?", id);
        return resultado.isEmpty() ? null : resultado.get(0);
    }

    public List<Venta> buscarPorCliente(int idCliente) {
        return listarConFiltro(
                "SELECT * FROM ventas WHERE id_cliente = ?", idCliente);
    }

    public List<Venta> buscarPorEmpleado(int idEmpleado) {
        return listarConFiltro(
                "SELECT * FROM ventas WHERE id_empleado = ?", idEmpleado);
    }

    public List<Venta> buscarPorFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin) {

        List<Venta> ventas = new ArrayList<>();

        String sql = "SELECT * FROM ventas WHERE fecha BETWEEN ? AND ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            ps.setDate(1, Date.valueOf(fechaInicio.toLocalDate()));
            ps.setDate(2, Date.valueOf(fechaFin.toLocalDate()));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ventas.add(mapearFila(rs));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ventas;

    }

    public List<Venta> buscarPorCanal(String canal) {
        return listarConFiltro(
                "SELECT * FROM ventas WHERE canal = ?", canal);
    }

    public List<Venta> buscarPorMedioPago(String medioPago) {
        return listarConFiltro(
                "SELECT * FROM ventas WHERE medio_pago = ?", medioPago);
    }

    private List<Venta> listarConFiltro(String sql, Object parametro) {

        List<Venta> ventas = new ArrayList<>();

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            if (parametro instanceof Integer valor) {
                ps.setInt(1, valor);
            } else if (parametro instanceof String valor) {
                ps.setString(1, valor);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ventas.add(mapearFila(rs));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ventas;

    }

    private Venta mapearFila(ResultSet rs) throws Exception {

        Venta venta = new Venta();

        int cantidad = rs.getInt("cantidad");
        double precioUnitario = rs.getDouble("precio_unitario");
        double descuento = rs.getDouble("descuento");

        double subtotal = precioUnitario * cantidad;
        double totalConDescuento = subtotal * (1 - descuento / 100);

        Producto producto = productoDAO.buscarPorId(rs.getInt("id_producto"));

        DetalleVenta detalle = new DetalleVenta(producto, cantidad, precioUnitario, subtotal);

        venta.setIdVenta(rs.getInt("id_venta"));
        venta.setCliente(clienteDAO.buscarPorId(rs.getInt("id_cliente")));
        venta.setEmpleado(empleadoDAO.buscarPorId(rs.getInt("id_empleado")));
        venta.setFecha(rs.getDate("fecha").toLocalDate().atStartOfDay());
        venta.setMedioPago(rs.getString("medio_pago"));
        venta.setCanal(rs.getString("canal"));
        venta.setDescuento(descuento);
        venta.setTotal(totalConDescuento);
        venta.setDetalles(Collections.singletonList(detalle));

        return venta;

    }

}
