package com.novatech.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.novatech.config.Conexion;
import com.novatech.model.Empleado;

public class EmpleadoDAO {

    public List<Empleado> listarEmpleados() {

        List<Empleado> empleados = new ArrayList<>();

        String sql = "SELECT * FROM empleados";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        ) {
            while (rs.next()) {

                Empleado empleado = new Empleado();

                empleado.setIdEmpleado(rs.getInt("id_empleado"));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setApellido(rs.getString("apellido"));
                empleado.setIdSucursal(rs.getInt("id_sucursal"));
                empleado.setFechaIngreso(rs.getDate("fecha_ingreso").toLocalDate());

                empleados.add(empleado);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return empleados;
    }

    public Empleado buscarPorId(int id) {

        String sql = "SELECT * FROM empleados WHERE id_empleado = ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Empleado empleado = new Empleado();

                empleado.setIdEmpleado(rs.getInt("id_empleado"));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setApellido(rs.getString("apellido"));
                empleado.setIdSucursal(rs.getInt("id_sucursal"));
                empleado.setFechaIngreso(rs.getDate("fecha_ingreso").toLocalDate());

                return empleado;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Empleado> buscarPorNombre(String nombre) {

        List<Empleado> empleados = new ArrayList<>();

        String sql = "SELECT * FROM empleados WHERE nombre LIKE ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            ps.setString(1, "%" + nombre + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Empleado empleado = new Empleado();

                empleado.setIdEmpleado(rs.getInt("id_empleado"));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setApellido(rs.getString("apellido"));
                empleado.setIdSucursal(rs.getInt("id_sucursal"));
                empleado.setFechaIngreso(rs.getDate("fecha_ingreso").toLocalDate());

                empleados.add(empleado);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return empleados;
    }

    public List<Empleado> buscarPorApellido(String apellido) {

        List<Empleado> empleados = new ArrayList<>();

        String sql = "SELECT * FROM empleados WHERE apellido LIKE ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            ps.setString(1, "%" + apellido + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Empleado empleado = new Empleado();

                empleado.setIdEmpleado(rs.getInt("id_empleado"));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setApellido(rs.getString("apellido"));
                empleado.setIdSucursal(rs.getInt("id_sucursal"));
                empleado.setFechaIngreso(rs.getDate("fecha_ingreso").toLocalDate());

                empleados.add(empleado);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return empleados;
    }

    public List<Empleado> buscarPorSucursal(int idSucursal) {

        List<Empleado> empleados = new ArrayList<>();

        String sql = "SELECT * FROM empleados WHERE id_sucursal = ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            ps.setInt(1, idSucursal);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Empleado empleado = new Empleado();

                empleado.setIdEmpleado(rs.getInt("id_empleado"));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setApellido(rs.getString("apellido"));
                empleado.setIdSucursal(rs.getInt("id_sucursal"));
                empleado.setFechaIngreso(rs.getDate("fecha_ingreso").toLocalDate());

                empleados.add(empleado);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return empleados;
    }

    public List<Empleado> buscarPorSucursalNombre(String nombreSucursal) {

        List<Empleado> empleados = new ArrayList<>();

        String sql = "SELECT e.* FROM empleados e " +
                "JOIN sucursales s ON e.id_sucursal = s.id_sucursal " +
                "WHERE s.nombre = ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            ps.setString(1, nombreSucursal);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Empleado empleado = new Empleado();

                    empleado.setIdEmpleado(rs.getInt("id_empleado"));
                    empleado.setNombre(rs.getString("nombre"));
                    empleado.setApellido(rs.getString("apellido"));
                    empleado.setIdSucursal(rs.getInt("id_sucursal"));
                    empleado.setFechaIngreso(rs.getDate("fecha_ingreso").toLocalDate());

                    empleados.add(empleado);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return empleados;
    }

    public List<String> obtenerNombresSucursales() {

        List<String> nombres = new ArrayList<>();

        String sql = "SELECT nombre FROM sucursales ORDER BY nombre";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        ) {

            while (rs.next()) {
                nombres.add(rs.getString("nombre"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return nombres;
    }

    public void insertar(Empleado empleado) {
        String sql = "INSERT INTO empleados (nombre, apellido, id_sucursal, fecha_ingreso) VALUES (?, ?, ?, ?)";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {
            ps.setString(1, empleado.getNombre());
            ps.setString(2, empleado.getApellido());
            ps.setInt(3, empleado.getIdSucursal());
            ps.setDate(4, java.sql.Date.valueOf(empleado.getFechaIngreso()));

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actualizar(Empleado empleado) {
        String sql = "UPDATE empleados SET nombre = ?, apellido = ?, id_sucursal = ?, fecha_ingreso = ? WHERE id_empleado = ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {
            ps.setString(1, empleado.getNombre());
            ps.setString(2, empleado.getApellido());
            ps.setInt(3, empleado.getIdSucursal());
            ps.setDate(4, java.sql.Date.valueOf(empleado.getFechaIngreso()));
            ps.setInt(5, empleado.getIdEmpleado());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar(int idEmpleado) {
        String sql = "DELETE FROM empleados WHERE id_empleado = ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {
            ps.setInt(1, idEmpleado);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
