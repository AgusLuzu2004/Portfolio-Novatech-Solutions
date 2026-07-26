package com.novatech.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import com.novatech.config.DbConfig;

public class BackupUtil {

    private BackupUtil() {
    }

    public static void crearBackup(String rutaDestino) {

        ProcessBuilder processBuilder = new ProcessBuilder(

                "mysqldump",
                "-h", DbConfig.getHost(),
                "-P", DbConfig.getPuerto(),
                "-u", DbConfig.getUsuario(),
                "--password=" + DbConfig.getPassword(),
                DbConfig.getBaseDatos(),
                "--result-file=" + rutaDestino

        );

        ejecutarProceso(processBuilder, "Backup creado correctamente.");

    }

    public static void restaurarBackup(String archivoBackup) {

        ProcessBuilder processBuilder = new ProcessBuilder(

                "mysql",
                "-h", DbConfig.getHost(),
                "-P", DbConfig.getPuerto(),
                "-u", DbConfig.getUsuario(),
                "--password=" + DbConfig.getPassword(),
                DbConfig.getBaseDatos()

        );

        processBuilder.redirectInput(new File(archivoBackup));

        ejecutarProceso(processBuilder, "Backup restaurado correctamente.");

    }

    private static void ejecutarProceso(ProcessBuilder processBuilder, String mensajeExito) {

        try {

            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            StringBuilder salida = new StringBuilder();

            try (BufferedReader lector = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {

                String linea;

                while ((linea = lector.readLine()) != null) {
                    salida.append(linea).append(System.lineSeparator());
                }

            }

            int codigo = process.waitFor();

            if (codigo == 0) {
                System.out.println(mensajeExito);
            } else {
                throw new RuntimeException(
                        "El proceso terminó con código " + codigo +
                        (salida.length() > 0 ? ": " + salida : "")
                );
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error ejecutando el proceso: " + e.getMessage(), e);
        }

    }

}
