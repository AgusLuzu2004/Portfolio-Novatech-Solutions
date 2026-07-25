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

            backupService.crearBackup(
                    archivo.getAbsolutePath()
            );

            mostrar("Backup creado correctamente.");

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

            backupService.restaurarBackup(
                    archivo.getAbsolutePath()
            );

            mostrar("Backup restaurado correctamente.");

        }

    }

    private void mostrar(String mensaje) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setHeaderText(null);

        alert.setContentText(mensaje);

        alert.showAndWait();

    }

}
