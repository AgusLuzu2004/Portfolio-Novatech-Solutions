package com.novatech.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.novatech.config.Conexion;
import com.novatech.model.reporte.DashboardResumen;
import com.novatech.model.reporte.FiltroReporte;
import com.novatech.model.reporte.MedioPagoReporte;
import com.novatech.model.reporte.RankingEmpleado;
import com.novatech.model.reporte.TopProducto;
import com.novatech.model.reporte.VentaAnual;
import com.novatech.model.reporte.VentaCategoria;
import com.novatech.model.reporte.VentaMensual;
import com.novatech.model.reporte.VentaProvincia;

public class ReporteDAO {

    private static final String FROM_JOINS =
            " FROM ventas v " +
            "JOIN productos p ON v.id_producto = p.id_producto " +
            "JOIN categorias c ON p.id_categoria = c.id_categoria " +
            "JOIN clientes cl ON v.id_cliente = cl.id_cliente " +
            "JOIN empleados e ON v.id_empleado = e.id_empleado " +
            "JOIN sucursales s ON e.id_sucursal = s.id_sucursal ";

    private static final String WHERE_FILTROS =
            "WHERE (? IS NULL OR YEAR(v.fecha) = ?) " +
            "AND (? IS NULL OR cl.provincia = ?) " +
            "AND (? IS NULL OR c.nombre_categoria = ?) " +
            "AND (? IS NULL OR s.nombre = ?)";

    private void setearFiltros(PreparedStatement ps, FiltroReporte filtro) throws SQLException {

        int i = 1;

        ps.setObject(i++, filtro.getAnio());
        ps.setObject(i++, filtro.getAnio());

        ps.setString(i++, filtro.getProvincia());
        ps.setString(i++, filtro.getProvincia());

        ps.setString(i++, filtro.getCategoria());
        ps.setString(i++, filtro.getCategoria());

        ps.setString(i++, filtro.getSucursal());
        ps.setString(i, filtro.getSucursal());

    }

    public double obtenerFacturacionTotal(FiltroReporte filtro) {

        String sql = "SELECT SUM(v.precio_unitario * v.cantidad * (1-v.descuento/100))" +
                FROM_JOINS + WHERE_FILTROS;

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            setearFiltros(ps, filtro);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;

    }

    public int obtenerCantidadVentas(FiltroReporte filtro) {

        String sql = "SELECT COUNT(*)" + FROM_JOINS + WHERE_FILTROS;

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            setearFiltros(ps, filtro);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;

    }

    public int obtenerClientesActivos(FiltroReporte filtro) {

        String sql = "SELECT COUNT(DISTINCT v.id_cliente)" + FROM_JOINS + WHERE_FILTROS;

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            setearFiltros(ps, filtro);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;

    }

    public int obtenerProductosVendidos(FiltroReporte filtro) {

        String sql = "SELECT SUM(v.cantidad)" + FROM_JOINS + WHERE_FILTROS;

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            setearFiltros(ps, filtro);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;

    }

    public List<VentaMensual> obtenerVentasMensuales(FiltroReporte filtro) {

        List<VentaMensual> lista = new ArrayList<>();

        String sql = "SELECT MONTH(v.fecha) AS mes, SUM(v.precio_unitario*v.cantidad*(1-v.descuento/100)) AS total" +
                FROM_JOINS + WHERE_FILTROS +
                " GROUP BY MONTH(v.fecha) ORDER BY MONTH(v.fecha)";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            setearFiltros(ps, filtro);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new VentaMensual(
                            String.valueOf(rs.getInt("mes")),
                            rs.getDouble("total")
                    ));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;

    }

    public List<TopProducto> obtenerTopProductos(FiltroReporte filtro) {

        List<TopProducto> lista = new ArrayList<>();

        String sql = "SELECT p.nombre, SUM(v.cantidad) AS total" +
                FROM_JOINS + WHERE_FILTROS +
                " GROUP BY p.nombre ORDER BY total DESC LIMIT 10";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            setearFiltros(ps, filtro);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new TopProducto(
                            rs.getString("nombre"),
                            rs.getInt("total")
                    ));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;

    }

    public List<VentaCategoria> obtenerVentasPorCategoria(FiltroReporte filtro) {

        List<VentaCategoria> lista = new ArrayList<>();

        String sql = "SELECT c.nombre_categoria, SUM(v.precio_unitario * v.cantidad * (1 - v.descuento / 100)) AS total" +
                FROM_JOINS + WHERE_FILTROS +
                " GROUP BY c.nombre_categoria ORDER BY total DESC";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            setearFiltros(ps, filtro);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new VentaCategoria(
                            rs.getString("nombre_categoria"),
                            rs.getDouble("total")
                    ));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;

    }

    public List<VentaProvincia> obtenerVentasPorProvincia(FiltroReporte filtro) {

        List<VentaProvincia> lista = new ArrayList<>();

        String sql = "SELECT cl.provincia, SUM(v.precio_unitario * v.cantidad * (1 - v.descuento / 100)) AS total" +
                FROM_JOINS + WHERE_FILTROS +
                " GROUP BY cl.provincia ORDER BY total DESC";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            setearFiltros(ps, filtro);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new VentaProvincia(
                            rs.getString("provincia"),
                            rs.getDouble("total")
                    ));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;

    }

    public List<RankingEmpleado> obtenerRankingEmpleados(FiltroReporte filtro) {

        List<RankingEmpleado> lista = new ArrayList<>();

        String sql = "SELECT CONCAT(e.nombre,' ',e.apellido) AS empleado, SUM(v.precio_unitario * v.cantidad * (1 - v.descuento / 100)) AS total" +
                FROM_JOINS + WHERE_FILTROS +
                " GROUP BY e.id_empleado ORDER BY total DESC";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            setearFiltros(ps, filtro);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new RankingEmpleado(
                            rs.getString("empleado"),
                            rs.getDouble("total")
                    ));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;

    }

    public List<MedioPagoReporte> obtenerMediosPago(FiltroReporte filtro) {

        List<MedioPagoReporte> lista = new ArrayList<>();

        String sql = "SELECT v.medio_pago, COUNT(*) AS cantidad" +
                FROM_JOINS + WHERE_FILTROS +
                " GROUP BY v.medio_pago ORDER BY cantidad DESC";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            setearFiltros(ps, filtro);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new MedioPagoReporte(
                            rs.getString("medio_pago"),
                            rs.getInt("cantidad")
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;

    }

    public List<VentaAnual> obtenerComparacionAnual(FiltroReporte filtro) {

        List<VentaAnual> lista = new ArrayList<>();

        String sql = "SELECT YEAR(v.fecha) AS anio, SUM(v.precio_unitario * v.cantidad * (1 - v.descuento / 100)) AS total" +
                FROM_JOINS + WHERE_FILTROS +
                " GROUP BY YEAR(v.fecha) ORDER BY anio";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            setearFiltros(ps, filtro);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new VentaAnual(
                            rs.getInt("anio"),
                            rs.getDouble("total")
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;

    }

    public DashboardResumen obtenerDashboardResumen(FiltroReporte filtro) {

        String sql = "SELECT COALESCE(SUM(v.precio_unitario * v.cantidad * (1 - v.descuento / 100)), 0) AS facturacion, " +
                "COUNT(*) AS ventas, COUNT(DISTINCT v.id_cliente) AS clientes, COALESCE(SUM(v.cantidad), 0) AS productos" +
                FROM_JOINS + WHERE_FILTROS;

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            setearFiltros(ps, filtro);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new DashboardResumen(
                            rs.getDouble("facturacion"),
                            rs.getInt("ventas"),
                            rs.getInt("clientes"),
                            rs.getInt("productos")
                    );
                }
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

        String sql = "SELECT nombre FROM sucursales ORDER BY nombre";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        ) {

            while (rs.next()) {
                sucursales.add(rs.getString("nombre"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sucursales;
    }
}
