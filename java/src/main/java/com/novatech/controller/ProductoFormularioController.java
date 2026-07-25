package com.novatech.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.novatech.model.Producto;
import com.novatech.service.ProductoService;

import javafx.fxml.FXML;
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

        if (producto == null) {

            productoService.guardarProducto(producto);

        }
        else {
            productoService.actualizarProducto(producto);
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
                1, 2, 3, 4, 5
        );
    }

}
