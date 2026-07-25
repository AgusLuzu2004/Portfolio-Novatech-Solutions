package com.novatech.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.novatech.config.Conexion;
import com.novatech.model.Cliente;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public List<Cliente> listarClientes() {

        List<Cliente> clientes = new ArrayList<>();

        String sql = "SELECT * FROM clientes";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        ) {

            while (rs.next()) {

                Cliente cliente = new Cliente();

                cliente.setIdCliente(rs.getInt("id_cliente"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setEdad(rs.getInt("edad"));
                cliente.setSexo(rs.getString("sexo"));
                cliente.setProvincia(rs.getString("provincia"));
                cliente.setCiudad(rs.getString("ciudad"));
                cliente.setFechaAlta(rs.getDate("fecha_alta").toLocalDate());

                clientes.add(cliente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }

    public Cliente buscarPorId(int id) {

        String sql = "SELECT * FROM clientes WHERE id_cliente = ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Cliente cliente = new Cliente();

                cliente.setIdCliente(rs.getInt("id_cliente"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setEdad(rs.getInt("edad"));
                cliente.setSexo(rs.getString("sexo"));
                cliente.setProvincia(rs.getString("provincia"));
                cliente.setCiudad(rs.getString("ciudad"));
                cliente.setFechaAlta(rs.getDate("fecha_alta").toLocalDate());

                return cliente;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Cliente> buscarPorNombre(String texto) {

        List<Cliente> clientes = new ArrayList<>();

        String sql = "SELECT * FROM clientes WHERE nombre LIKE ? OR apellido LIKE ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            ps.setString(1, "%" + texto + "%");
            ps.setString(2, "%" + texto + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Cliente cliente = new Cliente();

                cliente.setIdCliente(rs.getInt("id_cliente"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setEdad(rs.getInt("edad"));
                cliente.setSexo(rs.getString("sexo"));
                cliente.setProvincia(rs.getString("provincia"));
                cliente.setCiudad(rs.getString("ciudad"));
                cliente.setFechaAlta(rs.getDate("fecha_alta").toLocalDate());

                clientes.add(cliente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;

    }

    public List<Cliente> buscarPorProvincia(String provincia) {

        List<Cliente> clientes = new ArrayList<>();

        String sql = "SELECT * FROM clientes WHERE provincia LIKE ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            ps.setString(1, "%" + provincia + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Cliente cliente = new Cliente();

                cliente.setIdCliente(rs.getInt("id_cliente"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setEdad(rs.getInt("edad"));
                cliente.setSexo(rs.getString("sexo"));
                cliente.setProvincia(rs.getString("provincia"));
                cliente.setCiudad(rs.getString("ciudad"));
                cliente.setFechaAlta(rs.getDate("fecha_alta").toLocalDate());

                clientes.add(cliente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;

    }

    public List<String> listarProvincias() {
        List<String> provincias = new ArrayList<>();

        String sql = "SELECT DISTINCT provincia FROM clientes";

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

    public void insertar(Cliente cliente) {

        String sql = "INSERT INTO clientes (nombre, apellido, edad, sexo, provincia, ciudad, fecha_alta) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setInt(3, cliente.getEdad());
            ps.setString(4, cliente.getSexo());
            ps.setString(5, cliente.getProvincia());
            ps.setString(6, cliente.getCiudad());
            ps.setDate(7, Date.valueOf(cliente.getFechaAlta()));

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void actualizar(Cliente cliente) {
    
        String sql = "UPDATE clientes SET nombre = ?, apellido = ?, edad = ?, sexo = ?, provincia = ?, ciudad = ?, fecha_alta = ? WHERE id_cliente = ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setInt(3, cliente.getEdad());
            ps.setString(4, cliente.getSexo());
            ps.setString(5, cliente.getProvincia());
            ps.setString(6, cliente.getCiudad());
            ps.setDate(7, Date.valueOf(cliente.getFechaAlta()));
            ps.setInt(8, cliente.getIdCliente());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void eliminar(int id) {

        String sql = "DELETE FROM clientes WHERE id_cliente = ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (SQLException e) {

            if (e.getErrorCode() == 1451) {
                throw new RuntimeException(
                    "No se puede eliminar el cliente porque tiene ventas asociadas."
                );
            }

            e.printStackTrace();
        }

    }

}
