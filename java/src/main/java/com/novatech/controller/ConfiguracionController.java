package com.novatech.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ConfiguracionController {

    private static final Path ARCHIVO_CONFIGURACION =
            Path.of(System.getProperty("user.home"), ".novatech", "configuracion.properties");

    @FXML
    private TextField txtEmpresa;

    @FXML
    private ComboBox<String> cmbMoneda;

    @FXML
    private ColorPicker colorPrincipal;

    @FXML
    private Spinner<Integer> spRegistros;

    @FXML
    private TextField txtLogo;

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

        cargarConfiguracion();

    }

    @FXML
    private void guardarConfiguracion() {

        Properties propiedades = new Properties();

        propiedades.setProperty("empresa", valorSeguro(txtEmpresa.getText(), "NovaTech Solutions"));
        propiedades.setProperty("moneda", valorSeguro(cmbMoneda.getValue(), "ARS - Peso Argentino"));
        propiedades.setProperty("color", colorPrincipal.getValue().toString());
        propiedades.setProperty("registros", String.valueOf(spRegistros.getValue()));
        propiedades.setProperty("logo", valorSeguro(txtLogo.getText(), ""));

        try {

            if (ARCHIVO_CONFIGURACION.getParent() != null) {
                Files.createDirectories(ARCHIVO_CONFIGURACION.getParent());
            }

            try (OutputStream salida = Files.newOutputStream(ARCHIVO_CONFIGURACION)) {
                propiedades.store(salida, "Configuración de NovaTech Solutions");
            }

            mostrarInfo("Configuración guardada correctamente.");

        } catch (IOException e) {

            mostrarError("No se pudo guardar la configuración: " + e.getMessage());

        }

    }

    @FXML
    private void restaurarValores() {

        aplicarValoresPorDefecto();

        mostrarInfo("Se restauraron los valores predeterminados.");

    }

    private void cargarConfiguracion() {

        if (!Files.exists(ARCHIVO_CONFIGURACION)) {
            aplicarValoresPorDefecto();
            return;
        }

        Properties propiedades = new Properties();

        try (InputStream entrada = Files.newInputStream(ARCHIVO_CONFIGURACION)) {

            propiedades.load(entrada);

            txtEmpresa.setText(propiedades.getProperty("empresa", "NovaTech Solutions"));
            cmbMoneda.setValue(propiedades.getProperty("moneda", "ARS - Peso Argentino"));

            try {
                colorPrincipal.setValue(Color.web(propiedades.getProperty("color", "#1e90ff")));
            } catch (IllegalArgumentException e) {
                colorPrincipal.setValue(Color.DODGERBLUE);
            }

            spRegistros.getValueFactory().setValue(
                    Integer.parseInt(propiedades.getProperty("registros", "20"))
            );

            txtLogo.setText(propiedades.getProperty("logo", ""));

        } catch (IOException | NumberFormatException e) {

            aplicarValoresPorDefecto();

        }

    }

    private void aplicarValoresPorDefecto() {

        txtEmpresa.setText("NovaTech Solutions");

        cmbMoneda.setValue("ARS - Peso Argentino");

        colorPrincipal.setValue(Color.DODGERBLUE);

        spRegistros.getValueFactory().setValue(20);

        txtLogo.setText("");

    }

    private String valorSeguro(String valor, String porDefecto) {
        return valor != null && !valor.isBlank() ? valor : porDefecto;
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

            txtLogo.setText(archivo.getAbsolutePath());

            mostrarInfo("Logo seleccionado:\n" + archivo.getName());

        }

    }

    @FXML
    private void cancelar() {

        Stage stage = (Stage) txtEmpresa.getScene().getWindow();

        stage.close();

    }

    private void mostrarInfo(String mensaje) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Configuración");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();

    }

    private void mostrarError(String mensaje) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Configuración");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();

    }

}
