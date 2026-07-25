package com.novatech.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.novatech.config.Conexion;
import com.novatech.model.Venta;

public class VentaDAO {

    public List<Venta> listarVentas() {

        List<Venta> ventas = new ArrayList<>();

        String sql = "SELECT * FROM ventas";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        ) {
            while (rs.next()) {

                Venta venta = new Venta();

                venta.setIdVenta(rs.getInt("id_venta"));
                venta.setCliente(new ClienteDAO().buscarPorId(rs.getInt("id_cliente")));
                venta.setEmpleado(new EmpleadoDAO().buscarPorId(rs.getInt("id_empleado")));
                venta.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
                venta.setMedioPago(rs.getString("medio_pago"));
                venta.setCanal(rs.getString("canal"));
                venta.setDescuento(rs.getDouble("descuento"));
                venta.setTotal(rs.getDouble("total"));

                ventas.add(venta);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ventas;

    }

    public Venta buscarPorId(int id) {

        String sql = "SELECT * FROM ventas WHERE id_venta = ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Venta venta = new Venta();

                venta.setIdVenta(rs.getInt("id_venta"));
                venta.setCliente(new ClienteDAO().buscarPorId(rs.getInt("id_cliente")));
                venta.setEmpleado(new EmpleadoDAO().buscarPorId(rs.getInt("id_empleado")));
                venta.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
                venta.setMedioPago(rs.getString("medio_pago"));
                venta.setCanal(rs.getString("canal"));
                venta.setDescuento(rs.getDouble("descuento"));
                venta.setTotal(rs.getDouble("total"));

                return venta;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean registrarVenta(Venta venta) {
        String sql = "INSERT INTO ventas (id_cliente, id_empleado, fecha, medio_pago, canal, descuento, total) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {
            ps.setInt(1, venta.getCliente().getIdCliente());
            ps.setInt(2, venta.getEmpleado().getIdEmpleado());
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(venta.getFecha()));
            ps.setString(4, venta.getMedioPago());
            ps.setString(5, venta.getCanal());
            ps.setDouble(6, venta.getDescuento());
            ps.setDouble(7, venta.getTotal());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (Exception e) {
            e.printStackTrace();

        }

        return false;
    }

    public List<Venta> buscarPorCliente(int idCliente) {
        List<Venta> ventas = new ArrayList<>();

        String sql = "SELECT * FROM ventas WHERE id_cliente = ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            ps.setInt(1, idCliente);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Venta venta = new Venta();

                venta.setIdVenta(rs.getInt("id_venta"));
                venta.setCliente(new ClienteDAO().buscarPorId(rs.getInt("id_cliente")));
                venta.setEmpleado(new EmpleadoDAO().buscarPorId(rs.getInt("id_empleado")));
                venta.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
                venta.setMedioPago(rs.getString("medio_pago"));
                venta.setCanal(rs.getString("canal"));
                venta.setDescuento(rs.getDouble("descuento"));
                venta.setTotal(rs.getDouble("total"));

                ventas.add(venta);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ventas;
    }

    public List<Venta> buscarPorEmpleado(int idEmpleado) {
        List<Venta> ventas = new ArrayList<>();

        String sql = "SELECT * FROM ventas WHERE id_empleado = ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            ps.setInt(1, idEmpleado);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Venta venta = new Venta();

                venta.setIdVenta(rs.getInt("id_venta"));
                venta.setCliente(new ClienteDAO().buscarPorId(rs.getInt("id_cliente")));
                venta.setEmpleado(new EmpleadoDAO().buscarPorId(rs.getInt("id_empleado")));
                venta.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
                venta.setMedioPago(rs.getString("medio_pago"));
                venta.setCanal(rs.getString("canal"));
                venta.setDescuento(rs.getDouble("descuento"));
                venta.setTotal(rs.getDouble("total"));

                ventas.add(venta);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ventas;
    }

    public List<Venta> buscarPorFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        List<Venta> ventas = new ArrayList<>();

        String sql = "SELECT * FROM ventas WHERE fecha BETWEEN ? AND ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            ps.setTimestamp(1, java.sql.Timestamp.valueOf(fechaInicio));
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(fechaFin));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Venta venta = new Venta();

                venta.setIdVenta(rs.getInt("id_venta"));
                venta.setCliente(new ClienteDAO().buscarPorId(rs.getInt("id_cliente")));
                venta.setEmpleado(new EmpleadoDAO().buscarPorId(rs.getInt("id_empleado")));
                venta.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
                venta.setMedioPago(rs.getString("medio_pago"));
                venta.setCanal(rs.getString("canal"));
                venta.setDescuento(rs.getDouble("descuento"));
                venta.setTotal(rs.getDouble("total"));

                ventas.add(venta);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ventas;

    }

    public List<Venta> buscarPorCanal(String canal) {
        List<Venta> ventas = new ArrayList<>();

        String sql = "SELECT * FROM ventas WHERE canal = ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            ps.setString(1, canal);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Venta venta = new Venta();

                venta.setIdVenta(rs.getInt("id_venta"));
                venta.setCliente(new ClienteDAO().buscarPorId(rs.getInt("id_cliente")));
                venta.setEmpleado(new EmpleadoDAO().buscarPorId(rs.getInt("id_empleado")));
                venta.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
                venta.setMedioPago(rs.getString("medio_pago"));
                venta.setCanal(rs.getString("canal"));
                venta.setDescuento(rs.getDouble("descuento"));
                venta.setTotal(rs.getDouble("total"));

                ventas.add(venta);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ventas;
    }

    public List<Venta> buscarPorMedioPago(String medioPago) {
        List<Venta> ventas = new ArrayList<>();

        String sql = "SELECT * FROM ventas WHERE medio_pago = ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            ps.setString(1, medioPago);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Venta venta = new Venta();

                venta.setIdVenta(rs.getInt("id_venta"));
                venta.setCliente(new ClienteDAO().buscarPorId(rs.getInt("id_cliente")));
                venta.setEmpleado(new EmpleadoDAO().buscarPorId(rs.getInt("id_empleado")));
                venta.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
                venta.setMedioPago(rs.getString("medio_pago"));
                venta.setCanal(rs.getString("canal"));
                venta.setDescuento(rs.getDouble("descuento"));
                venta.setTotal(rs.getDouble("total"));

                ventas.add(venta);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ventas;

    }

}
