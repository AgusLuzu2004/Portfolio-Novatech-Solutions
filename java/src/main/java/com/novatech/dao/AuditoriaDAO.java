package com.novatech.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.novatech.config.Conexion;
import com.novatech.model.Auditoria;

public class AuditoriaDAO {

    public boolean registrar(Auditoria auditoria) {

        String sql = "INSERT INTO auditoria (usuario, accion, modulo, fecha) VALUES (?, ?, ?, ?)";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            ps.setString(1, auditoria.getUsuario());
            ps.setString(2, auditoria.getAccion());
            ps.setString(3, auditoria.getModulo());
            ps.setTimestamp(4, Timestamp.valueOf(auditoria.getFecha()));

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;

    }

    public List<Auditoria> listar() {

        List<Auditoria> auditorias = new ArrayList<>();

        String sql = "SELECT * FROM auditoria ORDER BY fecha DESC";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        ) {

            while (rs.next()) {
                auditorias.add(mapearFila(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return auditorias;
    }

    public List<Auditoria> buscarPorUsuario(String usuario) {

        List<Auditoria> auditorias = new ArrayList<>();

        String sql = "SELECT * FROM auditoria WHERE usuario = ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            ps.setString(1, usuario);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    auditorias.add(mapearFila(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return auditorias;

    }

    public List<Auditoria> buscarPorModulo(String modulo) {

        List<Auditoria> auditorias = new ArrayList<>();

        String sql = "SELECT * FROM auditoria WHERE modulo = ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            ps.setString(1, modulo);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    auditorias.add(mapearFila(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return auditorias;

    }

    public List<Auditoria> buscarPorFecha(LocalDateTime inicio, LocalDateTime fin) {

        List<Auditoria> auditorias = new ArrayList<>();

        String sql = "SELECT * FROM auditoria WHERE fecha BETWEEN ? AND ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            ps.setTimestamp(1, Timestamp.valueOf(inicio));
            ps.setTimestamp(2, Timestamp.valueOf(fin));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    auditorias.add(mapearFila(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return auditorias;

    }

    private Auditoria mapearFila(ResultSet rs) throws SQLException {

        Auditoria auditoria = new Auditoria();

        auditoria.setIdAuditoria(rs.getInt("id"));
        auditoria.setUsuario(rs.getString("usuario"));
        auditoria.setAccion(rs.getString("accion"));
        auditoria.setModulo(rs.getString("modulo"));
        auditoria.setFecha(rs.getTimestamp("fecha").toLocalDateTime());

        return auditoria;

    }

}
