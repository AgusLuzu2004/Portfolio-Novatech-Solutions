package com.novatech.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class PreferenciasController {

    @FXML
    private void guardarPreferencias() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Preferencias");

        alert.setHeaderText(null);

        alert.setContentText(
                "Preferencias guardadas correctamente."
        );

        alert.showAndWait();

    }

    @FXML
    private void restaurarValores() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Preferencias");

        alert.setHeaderText(null);

        alert.setContentText(
                "Se restauraron los valores predeterminados."
        );

        alert.showAndWait();

    }

}
