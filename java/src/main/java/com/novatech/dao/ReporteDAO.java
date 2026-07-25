package com.novatech.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.novatech.config.Conexion;
import com.novatech.model.reporte.DashboardResumen;
import com.novatech.model.reporte.MedioPagoReporte;
import com.novatech.model.reporte.RankingEmpleado;
import com.novatech.model.reporte.TopProducto;
import com.novatech.model.reporte.VentaAnual;
import com.novatech.model.reporte.VentaCategoria;
import com.novatech.model.reporte.VentaMensual;
import com.novatech.model.reporte.VentaProvincia;

public class ReporteDAO {

    public double obtenerFacturacionTotal() {

        String sql = "SELECT SUM(precio_unitario * cantidad * (1-descuento/100)) from ventas";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        ) {

            if (rs.next()) {

                return rs.getDouble(1);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;

    }

    public int obtenerCantidadVentas() {

        String sql = "SELECT COUNT(*) FROM ventas";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        ) {

            if (rs.next()) {

                return rs.getInt(1);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;

    }

    public int obtenerClientesActivos() {

        String sql = "SELECT COUNT(DISTINCT id_cliente) FROM ventas";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        ) {

            if (rs.next()) {

                return rs.getInt(1);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;

    }

    public int obtenerProductosVendidos() {

        String sql = "SELECT SUM(cantidad) FROM ventas";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        ) {

            if (rs.next()) {

                return rs.getInt(1);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;

    }

    public List<VentaMensual> obtenerVentasMensuales() {

        List<VentaMensual> lista = new ArrayList<>();

        String sql = "SELECT MONTH(fecha) AS mes, SUM(precio_unitario*cantidad*(1-descuento/100)) AS total FROM ventas GROUP BY MONTH(fecha) ORDER BY MONTH(fecha)";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        ) {

            while (rs.next()) {

                lista.add(new VentaMensual(
                        String.valueOf(rs.getInt("mes")),
                        rs.getDouble("total")
                ));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;

    }

    public List<TopProducto> obtenerTopProductos() {

        List<TopProducto> lista = new ArrayList<>();

        String sql = "SELECT p.nombre, SUM(v.cantidad) AS total FROM ventas v JOIN productos p ON p.id_producto = v.id_producto GROUP BY p.nombre ORDER BY total DESC LIMIT 10";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        ) {

            while (rs.next()) {

                lista.add(new TopProducto(
                        rs.getString("nombre"),
                        rs.getInt("total")
                ));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;

    }

    public List<VentaCategoria> obtenerVentasPorCategoria() {

        List<VentaCategoria> lista = new ArrayList<>();

        String sql = "SELECT c.nombre_categoria, SUM(v.precio_unitario * v.cantidad * (1 - v.descuento / 100)) AS total FROM ventas v JOIN productos p ON v.id_producto = p.id_producto JOIN categorias c ON p.id_categoria = c.id_categoria GROUP BY c.nombre_categoria ORDER BY total DESC";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        ) {

            while (rs.next()) {

                lista.add(new VentaCategoria(
                        rs.getString("nombre_categoria"),
                        rs.getDouble("total")
                ));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;

    }

    public List<VentaProvincia> obtenerVentasPorProvincia() {

        List<VentaProvincia> lista = new ArrayList<>();

        String sql = "SELECT c.provincia, SUM(v.precio_unitario * v.cantidad * (1 - v.descuento / 100)) AS total FROM ventas v JOIN clientes c ON v.id_cliente = c.id_cliente GROUP BY c.provincia ORDER BY total DESC";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        ) {

            while (rs.next()) {

                lista.add(new VentaProvincia(
                        rs.getString("provincia"),
                        rs.getDouble("total")
                ));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;

    }

    public List<RankingEmpleado> obtenerRankingEmpleados() {

        List<RankingEmpleado> lista = new ArrayList<>();

        String sql = "SELECT CONCAT(e.nombre,' ',e.apellido) AS empleado, SUM(v.precio_unitario * v.cantidad * (1 - v.descuento / 100)) AS total FROM ventas v JOIN empleados e ON v.id_empleado = e.id_empleado GROUP BY e.id_empleado ORDER BY total DESC";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        ) {

            while (rs.next()) {

                lista.add(new RankingEmpleado(
                        rs.getString("empleado"),
                        rs.getDouble("total")
                ));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;

    }

    public List<MedioPagoReporte> obtenerMediosPago() {

        List<MedioPagoReporte> lista = new ArrayList<>();

        String sql = "SELECT medio_pago, COUNT(*) AS cantidad FROM ventas GROUP BY medio_pago ORDER BY cantidad DESC";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        ) {

            while (rs.next()) {

                lista.add(new MedioPagoReporte(
                        rs.getString("medio_pago"),
                        rs.getInt("cantidad")
                ));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;

    }

    public List<VentaAnual> obtenerComparacionAnual() {

        List<VentaAnual> lista = new ArrayList<>();

        String sql = "SELECT YEAR(fecha) AS anio, SUM(precio_unitario * cantidad * (1 - descuento / 100)) AS total FROM ventas GROUP BY YEAR(fecha) ORDER BY anio";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        ) {

            while (rs.next()) {

                lista.add(new VentaAnual(
                        rs.getInt("anio"),
                        rs.getDouble("total")
                ));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;

    }

    public DashboardResumen obtenerDashboardResumen() {

        String sql = "SELECT COALESCE(SUM(precio_unitario * cantidad * (1 - descuento / 100)), 0) AS facturacion, COUNT(*) AS ventas, COUNT(DISTINCT id_cliente) AS clientes, COALESCE(SUM(cantidad), 0) AS productos FROM ventas";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        ) {

            if (rs.next()) {

                return new DashboardResumen(
                        rs.getDouble("facturacion"),
                        rs.getInt("ventas"),
                        rs.getInt("clientes"),
                        rs.getInt("productos")
                );

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new DashboardResumen(0, 0, 0, 0);
    }

    public List<Integer> obtenerAnios() {

        List<Integer> anios = new ArrayList<>();

        String sql = "SELECT DISTINCT YEAR(fecha) AS anio FROM ventas ORDER BY anio";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        ) {

            while (rs.next()) {
                anios.add(rs.getInt("anio"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return anios;

    }

    public List<String> obtenerProvincias() {

        List<String> provincias = new ArrayList<>();

        String sql = "SELECT DISTINCT provincia FROM clientes ORDER BY provincia";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        ) {

            while (rs.next()) {
                provincias.add(rs.getString("provincia"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return provincias;
    }

    public List<String> obtenerCategorias() {

        List<String> categorias = new ArrayList<>();

        String sql = "SELECT nombre_categoria FROM categorias ORDER BY nombre_categoria";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        ) {

            while (rs.next()) {
                categorias.add(rs.getString("nombre_categoria"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categorias;

    }

    public List<String> obtenerSucursales() {

        List<String> sucursales = new ArrayList<>();

        String sql = "SELECT nombre_sucursal FROM sucursales ORDER BY nombre_sucursal";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        ) {

            while (rs.next()) {
                sucursales.add(rs.getString("nombre_sucursal"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sucursales;
    }
}
