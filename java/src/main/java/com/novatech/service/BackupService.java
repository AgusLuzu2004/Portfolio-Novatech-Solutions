package com.novatech.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.novatech.util.BackupUtil;

public class BackupService {

    private static final Logger logger =
            LoggerFactory.getLogger(BackupService.class);

    public void crearBackup(String rutaDestino) {

        try {

            if (rutaDestino == null || rutaDestino.isBlank()) {
                throw new IllegalArgumentException(
                        "La ruta de destino es obligatoria.");
            }

            logger.info(
                    "Iniciando creación de backup. Destino: {}",
                    rutaDestino);

            BackupUtil.crearBackup(rutaDestino);

            logger.info(
                    "Backup creado correctamente. Destino: {}",
                    rutaDestino);

        } catch (IllegalArgumentException e) {

            logger.warn(
                    "No fue posible crear el backup: {}",
                    e.getMessage());

            throw e;

        } catch (Exception e) {

            logger.error(
                    "Error al crear el backup.",
                    e);

            throw e;
        }

    }

    public void restaurarBackup(String archivoBackup) {

        try {

            if (archivoBackup == null || archivoBackup.isBlank()) {
                throw new IllegalArgumentException(
                        "Debe seleccionar un archivo de backup.");
            }

            logger.info(
                    "Iniciando restauración desde: {}",
                    archivoBackup);

            BackupUtil.restaurarBackup(archivoBackup);

            logger.info(
                    "Backup restaurado correctamente desde: {}",
                    archivoBackup);

        } catch (IllegalArgumentException e) {

            logger.warn(
                    "No fue posible restaurar el backup: {}",
                    e.getMessage());

            throw e;

        } catch (Exception e) {

            logger.error(
                    "Error al restaurar el backup.",
                    e);

            throw e;
        }

    }

}
