package com.novatech.controller;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class ConfiguracionController {

    @FXML
    private TextField txtEmpresa;

    @FXML
    private ComboBox<String> cmbMoneda;

    @FXML
    private ColorPicker colorPrincipal;

    @FXML
    private Spinner<Integer> spRegistros;

    @FXML
    public void initialize() {

        cmbMoneda.getItems().addAll(
                "ARS - Peso Argentino",
                "USD - Dólar Estadounidense",
                "EUR - Euro"
        );

        spRegistros.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 100, 20)
        );

        restaurarValores();

    }

    @FXML
    private void guardarConfiguracion() {

        String empresa = txtEmpresa.getText();
        String moneda = cmbMoneda.getValue();
        String color = colorPrincipal.getValue().toString();
        Integer registros = spRegistros.getValue();

        System.out.println("Empresa: " + empresa);
        System.out.println("Moneda: " + moneda);
        System.out.println("Color: " + color);
        System.out.println("Registros: " + registros);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Configuración");
        alert.setHeaderText(null);
        alert.setContentText("Configuración guardada correctamente.");
        alert.showAndWait();
    }

    @FXML
    private void restaurarValores() {

        txtEmpresa.setText("NovaTech Solutions");

        cmbMoneda.setValue("ARS - Peso Argentino");

        colorPrincipal.setValue(Color.DODGERBLUE);

        spRegistros.getValueFactory().setValue(20);

    }

    @FXML
    private void cambiarLogo() {

        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Seleccionar logo");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "Imágenes",
                        "*.png",
                        "*.jpg",
                        "*.jpeg"
                )
        );

        File archivo = fileChooser.showOpenDialog(txtEmpresa.getScene().getWindow());

        if (archivo != null) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Logo");
            alert.setHeaderText(null);
            alert.setContentText("Logo seleccionado:\n" + archivo.getName());
            alert.showAndWait();

            // Más adelante podés guardar la ruta
        }

    }

}
