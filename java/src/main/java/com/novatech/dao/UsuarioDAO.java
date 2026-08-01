package com.novatech.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.novatech.config.Conexion;
import com.novatech.model.Rol;
import com.novatech.model.Usuario;

public class UsuarioDAO {

    public List<Usuario> listarUsuarios() {

        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT * FROM usuarios";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        ) {

            while (rs.next()) {

                Usuario usuario = new Usuario();

                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setContraseña(rs.getString("password"));
                usuario.setRol(Rol.valueOf(rs.getString("rol")));
                usuario.setActivo(rs.getBoolean("activo"));

                usuarios.add(usuario);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;

    }

    public Usuario buscarPorId(int id) {

        String sql = "SELECT * FROM usuarios WHERE id_usuario = ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Usuario usuario = new Usuario();

                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setContraseña(rs.getString("password"));
                usuario.setRol(Rol.valueOf(rs.getString("rol")));
                usuario.setActivo(rs.getBoolean("activo"));

                return usuario;

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public Usuario buscarPorUsuario(String nombre) {

        String sql = "SELECT * FROM usuarios WHERE usuario = ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {
            ps.setString(1, nombre);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Usuario usuario = new Usuario();

                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setContraseña(rs.getString("password"));
                usuario.setRol(Rol.valueOf(rs.getString("rol")));
                usuario.setActivo(rs.getBoolean("activo"));

                return usuario;

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public List<Usuario> buscarPorNombre(String nombre) {

        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT * FROM usuarios WHERE nombre = ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {
            ps.setString(1, nombre);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Usuario usuario = new Usuario();

                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setContraseña(rs.getString("password"));
                usuario.setRol(Rol.valueOf(rs.getString("rol")));
                usuario.setActivo(rs.getBoolean("activo"));

                usuarios.add(usuario);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    public boolean insertar(Usuario usuario) {

        String sql = "INSERT INTO usuarios (usuario, password, nombre, rol, activo) VALUES (?, ?, ?, ?, ?)";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            ps.setString(1, usuario.getUsuario());
            ps.setString(2, usuario.getContraseña());
            ps.setString(3, usuario.getNombre());
            ps.setString(4, usuario.getRol().name());
            ps.setBoolean(5, usuario.isActivo());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;

    }

    public boolean actualizar(Usuario usuario) {

        String sql = "UPDATE usuarios SET usuario = ?, nombre = ?, rol = ?, activo = ? WHERE id_usuario = ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            ps.setString(1, usuario.getUsuario());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getRol().name());
            ps.setBoolean(4, usuario.isActivo());
            ps.setInt(5, usuario.getIdUsuario());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;

    }

    public boolean cambiarContraseña(int id, String nuevaContraseña) {

        String sql = "UPDATE usuarios SET password = ? WHERE id_usuario = ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            ps.setString(1, nuevaContraseña);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;

    }

    public boolean activar(int id) {

        String sql = "UPDATE usuarios SET activo = TRUE WHERE id_usuario = ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;

    }

    public boolean desactivar(int id) {

        String sql = "UPDATE usuarios SET activo = FALSE WHERE id_usuario = ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;

    }

    public boolean eliminar(int id) {

        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            ps.setInt(1, id);

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;

        }

    }

}
