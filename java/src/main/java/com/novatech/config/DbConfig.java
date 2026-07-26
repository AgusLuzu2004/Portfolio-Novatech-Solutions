package com.novatech.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DbConfig {

    private static final Pattern PATRON_URL =
            Pattern.compile("jdbc:mysql://([^:/]+):(\\d+)/([^?]+)");

    private static final String URL;
    private static final String USUARIO;
    private static final String PASSWORD;
    private static final String HOST;
    private static final String PUERTO;
    private static final String BASE_DATOS;

    static {

        Properties propiedades = new Properties();

        try (InputStream input = DbConfig.class
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

        USUARIO = System.getenv().getOrDefault(
                "DB_USER",
                propiedades.getProperty("db.user", "root")
        );

        PASSWORD = System.getenv().getOrDefault(
                "DB_PASSWORD",
                propiedades.getProperty("db.password", "")
        );

        Matcher coincidencia = PATRON_URL.matcher(URL);

        if (coincidencia.find()) {
            HOST = coincidencia.group(1);
            PUERTO = coincidencia.group(2);
            BASE_DATOS = coincidencia.group(3);
        } else {
            HOST = "localhost";
            PUERTO = "3306";
            BASE_DATOS = "novatech";
        }

        if (PASSWORD.isEmpty()) {
            System.err.println(
                    "ADVERTENCIA: no se encontró db.password. " +
                    "Configurá src/main/resources/db.properties " +
                    "(a partir de db.properties.example) o la variable de entorno DB_PASSWORD."
            );
        }

    }

    private DbConfig() {

    }

    public static String getUrl() {
        return URL;
    }

    public static String getUsuario() {
        return USUARIO;
    }

    public static String getPassword() {
        return PASSWORD;
    }

    public static String getHost() {
        return HOST;
    }

    public static String getPuerto() {
        return PUERTO;
    }

    public static String getBaseDatos() {
        return BASE_DATOS;
    }

}
