package com.novatech.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion {

    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {

        Properties propiedades = new Properties();

        try (InputStream input = Conexion.class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {

            if (input != null) {
                propiedades.load(input);
            }

        } catch (IOException e) {
            throw new ExceptionInInitializerError(
                    "No se pudo leer db.properties: " + e.getMessage()
            );
        }

        URL = System.getenv().getOrDefault(
                "DB_URL",
                propiedades.getProperty("db.url", "jdbc:mysql://localhost:3306/novatech")
        );

        USER = System.getenv().getOrDefault(
                "DB_USER",
                propiedades.getProperty("db.user", "root")
        );

        PASSWORD = System.getenv().getOrDefault(
                "DB_PASSWORD",
                propiedades.getProperty("db.password", "")
        );

        if (PASSWORD.isEmpty()) {
            System.err.println(
                    "ADVERTENCIA: no se encontró db.password. " +
                    "Configurá src/main/resources/db.properties " +
                    "(a partir de db.properties.example) o la variable de entorno DB_PASSWORD."
            );
        }

    }

    public static Connection conectar() throws SQLException {

        return DriverManager.getConnection(URL, USER, PASSWORD);

    }

}
