package com.novatech.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.novatech.config.Conexion;

public class MigracionContraseñas {

    public static void main(String[] args) {

        List<String> migrados = new ArrayList<>();
        List<String> yaHasheados = new ArrayList<>();
        List<String> conError = new ArrayList<>();

        String selectSql = "SELECT id_usuario, usuario, password FROM usuarios";
        String updateSql = "UPDATE usuarios SET password = ? WHERE id_usuario = ?";

        try (Connection conexion = Conexion.conectar()) {

            conexion.setAutoCommit(false);

            try (
                PreparedStatement selectPs = conexion.prepareStatement(selectSql);
                ResultSet rs = selectPs.executeQuery()
            ) {

                try (PreparedStatement updatePs = conexion.prepareStatement(updateSql)) {

                    while (rs.next()) {

                        int id = rs.getInt("id_usuario");
                        String nombreUsuario = rs.getString("usuario");
                        String valorActual = rs.getString("password");

                        if (valorActual != null && PasswordUtil.pareceHasheada(valorActual)) {
                            yaHasheados.add(nombreUsuario);
                            continue;
                        }

                        if (valorActual == null || valorActual.isEmpty()) {
                            conError.add(nombreUsuario + " (contraseña vacía, se saltea)");
                            continue;
                        }

                        try {

                            String hash = PasswordUtil.hashear(valorActual);

                            updatePs.setString(1, hash);
                            updatePs.setInt(2, id);
                            updatePs.executeUpdate();

                            migrados.add(nombreUsuario);

                        } catch (Exception e) {
                            conError.add(nombreUsuario + " (" + e.getMessage() + ")");
                        }

                    }

                }

            }

            conexion.commit();

        } catch (SQLException e) {
            System.err.println("Error de conexión/consulta: " + e.getMessage());
            return;
        }

        System.out.println("=== Migración de contraseñas: resumen ===");
        System.out.println("Migrados ahora:      " + migrados.size());
        System.out.println("Ya estaban hasheados: " + yaHasheados.size());
        System.out.println("Con error/saltados:   " + conError.size());

        if (!migrados.isEmpty()) {
            System.out.println();
            System.out.println("Usuarios migrados: " + migrados);
        }

        if (!conError.isEmpty()) {
            System.out.println();
            System.out.println("Revisar manualmente: " + conError);
        }

    }

}
