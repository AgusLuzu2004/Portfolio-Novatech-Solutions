package com.novatech.controller;

import com.novatech.service.BackupService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.File;

public class BackupController {

    private final BackupService backupService =
            new BackupService();

    @FXML
    private void crearBackup() {

        FileChooser chooser = new FileChooser();

        chooser.setTitle("Guardar Backup");

        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "SQL (*.sql)",
                        "*.sql"
                )
        );

        File archivo = chooser.showSaveDialog(null);

        if (archivo != null) {

            try {

                backupService.crearBackup(
                        archivo.getAbsolutePath()
                );

                mostrar(Alert.AlertType.INFORMATION, "Backup creado correctamente.");

            } catch (Exception e) {

                mostrar(Alert.AlertType.ERROR,
                        "No se pudo crear el backup:" + System.lineSeparator() + e.getMessage());

            }

        }

    }

    @FXML
    private void restaurarBackup() {

        FileChooser chooser = new FileChooser();

        chooser.setTitle("Seleccionar Backup");

        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "SQL (*.sql)",
                        "*.sql"
                )
        );

        File archivo = chooser.showOpenDialog(null);

        if (archivo != null) {

            try {

                backupService.restaurarBackup(
                        archivo.getAbsolutePath()
                );

                mostrar(Alert.AlertType.INFORMATION, "Backup restaurado correctamente.");

            } catch (Exception e) {

                mostrar(Alert.AlertType.ERROR,
                        "No se pudo restaurar el backup:" + System.lineSeparator() + e.getMessage());

            }

        }

    }

    private void mostrar(Alert.AlertType tipo, String mensaje) {

        Alert alert = new Alert(tipo);

        alert.setHeaderText(null);

        alert.setContentText(mensaje);

        alert.showAndWait();

    }

}
