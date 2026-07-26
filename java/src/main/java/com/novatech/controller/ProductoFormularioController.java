package com.novatech.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.novatech.model.Producto;
import com.novatech.service.ProductoService;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ProductoFormularioController {

    @FXML
    private TextField txtNombre;

    @FXML
    private ComboBox<String> cmbMarca;

    @FXML
    private ComboBox<Integer> cmbCategoria;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtStock;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnCancelar;

    private Producto producto;

    private ProductoService productoService;

    public void initialize(URL url, ResourceBundle rb) {

        productoService = new ProductoService();

        cargarDatos();

    }

    public void setProducto(Producto producto) {

        this.producto = producto;

        txtNombre.setText(producto.getNombre());

        cmbMarca.setValue(producto.getMarca());

        cmbCategoria.setValue(producto.getIdCategoria());

        txtPrecio.setText(String.valueOf(producto.getPrecio()));

        txtStock.setText(String.valueOf(producto.getStock()));

    }

    @FXML
    private void guardarProducto() {

        try {

            if (producto == null) {
                producto = new Producto();
            }

            producto.setNombre(txtNombre.getText());
            producto.setMarca(cmbMarca.getValue());
            producto.setIdCategoria(cmbCategoria.getValue());
            producto.setPrecio(Double.parseDouble(txtPrecio.getText()));
            producto.setStock(Integer.parseInt(txtStock.getText()));

            if (producto.getIdProducto() == 0) {
                productoService.guardarProducto(producto);
            } else {
                productoService.actualizarProducto(producto);
            }

            cerrarVentana();

        } catch (NumberFormatException e) {

            mostrarError("El precio y el stock deben ser números válidos.");

        } catch (Exception e) {

            mostrarError(e.getMessage());

        }

    }

    @FXML
    private void cancelar() {

        cerrarVentana();

    }

    private void cerrarVentana() {

        Stage stage = (Stage) btnCancelar.getScene().getWindow();

        stage.close();

    }

    private void cargarDatos() {

        cmbMarca.getItems().addAll(
                "Marca A",
                "Marca B",
                "Marca C"
        );

        cmbCategoria.getItems().addAll(
                1, 2, 3, 4, 5, 6
        );
    }

    private void mostrarError(String mensaje) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setHeaderText("Error");

        alert.setContentText(mensaje);

        alert.showAndWait();

    }

}
