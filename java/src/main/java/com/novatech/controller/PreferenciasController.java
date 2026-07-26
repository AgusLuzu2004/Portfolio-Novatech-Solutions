package com.novatech.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class PreferenciasController {

    @FXML
    private ComboBox<String> cmbTema;

    @FXML
    private ComboBox<String> cmbIdioma;

    @FXML
    private TextField txtRuta;

    @FXML
    private CheckBox chkAutoGuardar;

    private static final Path ARCHIVO_PREFERENCIAS =
            Path.of(System.getProperty("user.home"), ".novatech", "preferencias.properties");

    @FXML
    public void initialize() {

        cargarPreferencias();

    }

    @FXML
    private void guardarPreferencias() {

        Properties propiedades = new Properties();

        propiedades.setProperty("tema", valorSeguro(cmbTema.getValue(), "Claro"));
        propiedades.setProperty("idioma", valorSeguro(cmbIdioma.getValue(), "Español"));
        propiedades.setProperty("rutaExportacion", valorSeguro(txtRuta.getText(), ""));
        propiedades.setProperty("autoGuardar", String.valueOf(chkAutoGuardar.isSelected()));

        try {

            if (ARCHIVO_PREFERENCIAS.getParent() != null) {
                Files.createDirectories(ARCHIVO_PREFERENCIAS.getParent());
            }

            try (OutputStream salida = Files.newOutputStream(ARCHIVO_PREFERENCIAS)) {
                propiedades.store(salida, "Preferencias de NovaTech Solutions");
            }

            mostrarInfo("Preferencias guardadas correctamente.");

        } catch (IOException e) {

            mostrarError("No se pudieron guardar las preferencias: " + e.getMessage());

        }

    }

    @FXML
    private void restaurarValores() {

        aplicarValoresPorDefecto();

        mostrarInfo("Se restauraron los valores predeterminados.");

    }

    private void cargarPreferencias() {

        if (!Files.exists(ARCHIVO_PREFERENCIAS)) {
            aplicarValoresPorDefecto();
            return;
        }

        Properties propiedades = new Properties();

        try (InputStream entrada = Files.newInputStream(ARCHIVO_PREFERENCIAS)) {

            propiedades.load(entrada);

            cmbTema.setValue(propiedades.getProperty("tema", "Claro"));
            cmbIdioma.setValue(propiedades.getProperty("idioma", "Español"));
            txtRuta.setText(propiedades.getProperty("rutaExportacion", System.getProperty("user.home")));
            chkAutoGuardar.setSelected(Boolean.parseBoolean(propiedades.getProperty("autoGuardar", "true")));

        } catch (IOException e) {

            aplicarValoresPorDefecto();

        }

    }

    private void aplicarValoresPorDefecto() {

        cmbTema.setValue("Claro");

        cmbIdioma.setValue("Español");

        txtRuta.setText(System.getProperty("user.home"));

        chkAutoGuardar.setSelected(true);

    }

    private String valorSeguro(String valor, String porDefecto) {
        return valor != null ? valor : porDefecto;
    }

    private void mostrarInfo(String mensaje) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Preferencias");

        alert.setHeaderText(null);

        alert.setContentText(mensaje);

        alert.showAndWait();

    }

    private void mostrarError(String mensaje) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Preferencias");

        alert.setHeaderText(null);

        alert.setContentText(mensaje);

        alert.showAndWait();

    }

}
