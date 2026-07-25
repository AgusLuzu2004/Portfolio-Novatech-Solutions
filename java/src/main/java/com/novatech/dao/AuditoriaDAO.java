package com.novatech.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
            ps.setDate(4, Date.valueOf(auditoria.getFecha()));

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

                Auditoria auditoria = new Auditoria();

                auditoria.setIdAuditoria(rs.getInt("id_auditoria"));
                auditoria.setUsuario(rs.getString("usuario"));
                auditoria.setAccion(rs.getString("accion"));
                auditoria.setModulo(rs.getString("modulo"));
                auditoria.setFecha(rs.getDate("fecha").toLocalDate());

                auditorias.add(auditoria);
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

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Auditoria auditoria = new Auditoria();

                auditoria.setIdAuditoria(rs.getInt("id_auditoria"));
                auditoria.setUsuario(rs.getString("usuario"));
                auditoria.setAccion(rs.getString("accion"));
                auditoria.setModulo(rs.getString("modulo"));
                auditoria.setFecha(rs.getDate("fecha").toLocalDate());
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

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Auditoria auditoria = new Auditoria();

                auditoria.setIdAuditoria(rs.getInt("id_auditoria"));
                auditoria.setUsuario(rs.getString("usuario"));
                auditoria.setAccion(rs.getString("accion"));
                auditoria.setModulo(rs.getString("modulo"));
                auditoria.setFecha(rs.getDate("fecha").toLocalDate());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return auditorias;

    }

    public List<Auditoria> buscarPorFecha(LocalDate inicio, LocalDate fin) {

        List<Auditoria> auditorias = new ArrayList<>();

        String sql = "SELECT * FROM auditoria WHERE DATE (fecha) BETWEEN ? AND ?";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql);
        ) {

            ps.setDate(1, Date.valueOf(inicio));
            ps.setDate(2, Date.valueOf(fin));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Auditoria auditoria = new Auditoria();

                auditoria.setIdAuditoria(rs.getInt("id_auditoria"));
                auditoria.setUsuario(rs.getString("usuario"));
                auditoria.setAccion(rs.getString("accion"));
                auditoria.setModulo(rs.getString("modulo"));
                auditoria.setFecha(rs.getDate("fecha").toLocalDate());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return auditorias;

    }
}
