package com.novatech.controller;

import java.io.IOException;
import java.util.List;

import com.novatech.model.Producto;
import com.novatech.service.ProductoService;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ProductoController implements Initializable {

    @FXML
    private TableView<Producto> tablaProductos;

    @FXML
    private TableColumn<Producto, Integer> colId;

    @FXML
    private TableColumn<Producto, String> colNombre;

    @FXML
    private TableColumn<Producto, String> colMarca;

    @FXML
    private TableColumn<Producto, Integer> colCategoria;

    @FXML
    private TableColumn<Producto, Double> colPrecio;

    @FXML
    private TableColumn<Producto, Integer> colStock;

    @FXML
    private TextField txtBuscar;

    @FXML
    private ComboBox<String> cmbCategoria;

    @FXML
    private ComboBox<String> cmbMarca;

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    private ProductoService productoService = new ProductoService();

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {

        configurarTabla();

        cargarDatos();

        cargarMarcas();

        cargarCategorias();

    }

    private void cargarCategorias() {

        cmbCategoria.getItems().clear();

        cmbCategoria.getItems().add("Todas");

        cmbCategoria.getItems().addAll(productoService.obtenerCategorias());

        cmbCategoria.getSelectionModel().selectFirst();

    }

    @FXML
    private void filtrarCategoria() {

        String categoria = cmbCategoria.getValue();

        List<Producto> lista;

        if (categoria == null || categoria.equals("Todas")) {
            lista = productoService.obtenerTodos();
        } else {
            lista = productoService.buscarPorCategoria(categoria);
        }

        tablaProductos.setItems(
            FXCollections.observableArrayList(lista)
        );

    }

    private void cargarMarcas() {

        cmbMarca.getItems().clear();

        cmbMarca.getItems().add("Todas");

        cmbMarca.getItems().addAll(productoService.obtenerMarcas());

        cmbMarca.getSelectionModel().selectFirst();

    }

    @FXML
    private void filtrarMarca() {

        String marca = cmbMarca.getValue();

        List<Producto> lista;

        if (marca == null || marca.equals("Todas")) {
            lista = productoService.obtenerTodos();
        } else {
            lista = productoService.buscarPorMarca(marca);
        }

        tablaProductos.setItems(
            FXCollections.observableArrayList(lista)
        );

    }

    private void configurarTabla() {

        colId.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("idProducto"));
        colNombre.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("nombre"));
        colMarca.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("marca"));
        colCategoria.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("idCategoria"));
        colPrecio.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("precio"));
        colStock.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("stock"));

    }

    private void cargarDatos() {

        List<Producto> productos = productoService.obtenerTodos();
        tablaProductos.setItems(FXCollections.observableArrayList(productos));

    }

    @FXML
    private void buscarProducto() {

        String texto = txtBuscar.getText();

        List<Producto> lista = productoService.buscar(texto);

        tablaProductos.setItems(
            FXCollections.observableArrayList(lista)
        );

    }

    @FXML
    private void nuevoProducto() {
        try {

            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/ProductoFormulario.fxml")
            );

            Parent root = loader.load();

            Stage stage = new Stage();

            stage.setTitle("Nuevo Producto");

            stage.setScene(new Scene(root));

            stage.showAndWait();

            cargarDatos();

        } catch (IOException e) {

            mostrarAlerta("No se pudo abrir el formulario.");

        }
    }

    @FXML
    private void editarProducto() {

        Producto producto =
            tablaProductos.getSelectionModel().getSelectedItem();

        if(producto == null){

            mostrarAlerta("Seleccione un producto.");

            return;

        }

        try {

            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/ProductoFormulario.fxml")
            );

            Parent root = loader.load();

            ProductoFormularioController controller =
                    loader.getController();

            controller.setProducto(producto);

            Stage stage = new Stage();

            stage.setTitle("Editar Producto");

            stage.setScene(new Scene(root));

            stage.showAndWait();

            cargarDatos();

        } catch (IOException e) {

            mostrarAlerta("No se pudo abrir el formulario.");

        }

    }

    @FXML
    private void eliminarProducto() {

        Producto producto =
            tablaProductos.getSelectionModel().getSelectedItem();

        if(producto == null){

            mostrarAlerta("Seleccione un producto.");

            return;

        }

        try {
            productoService.eliminarProducto(producto.getIdProducto());

            cargarDatos();

            mostrarAlerta("Producto eliminado correctamente.");

        } catch (RuntimeException e) {

            mostrarAlerta(e.getMessage());

        }

    }

    private void limpiarFormulario() {

        txtBuscar.clear();

    }

    private void mostrarAlerta(String mensaje){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setHeaderText(null);

        alert.setContentText(mensaje);

        alert.showAndWait();

    }

}
