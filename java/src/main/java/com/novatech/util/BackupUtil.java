package com.novatech.util;

import java.io.IOException;

public class BackupUtil {

    // Configuración de la base de datos
    private static final String HOST = "localhost";
    private static final String PUERTO = "3306";
    private static final String BASE_DATOS = "novatech";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "tu_password";

    private BackupUtil() {
    }

    public static void crearBackup(String rutaDestino) {

        ProcessBuilder processBuilder = new ProcessBuilder(

                "mysqldump",
                "-h", HOST,
                "-P", PUERTO,
                "-u", USUARIO,
                "--password=" + PASSWORD,
                BASE_DATOS,
                "--result-file=" + rutaDestino

        );

        ejecutarProceso(processBuilder, "Backup creado correctamente.");

    }

    public static void restaurarBackup(String archivoBackup) {

        ProcessBuilder processBuilder = new ProcessBuilder(

                "mysql",
                "-h", HOST,
                "-P", PUERTO,
                "-u", USUARIO,
                "--password=" + PASSWORD,
                BASE_DATOS

        );

        processBuilder.redirectInput(new java.io.File(archivoBackup));

        ejecutarProceso(processBuilder, "Backup restaurado correctamente.");

    }

    private static void ejecutarProceso(ProcessBuilder processBuilder, String mensajeExito) {

        try {

            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            int codigo = process.waitFor();

            if (codigo == 0) {

                System.out.println(mensajeExito);

            } else {

                throw new RuntimeException("El proceso terminó con código: " + codigo);

            }

        } catch (IOException | InterruptedException e) {

            throw new RuntimeException("Error ejecutando el proceso.", e);

        }

    }

}
