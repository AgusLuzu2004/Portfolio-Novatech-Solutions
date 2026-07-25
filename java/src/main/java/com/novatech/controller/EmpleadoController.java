package com.novatech.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.novatech.model.Empleado;
import com.novatech.service.EmpleadoService;

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

public class EmpleadoController implements Initializable {

    @FXML
    private TableView<Empleado> tablaEmpleados;

    @FXML
    private TableColumn<Empleado, Integer> colId;

    @FXML
    private TableColumn<Empleado, String> colNombre;

    @FXML
    private TableColumn<Empleado, String> colApellido;

    @FXML
    private TableColumn<Empleado, Integer> colSucursal;

    @FXML
    private TableColumn<Empleado, LocalDate> colFechaIngreso;

    @FXML
    private TextField txtBuscar;

    @FXML
    private ComboBox<String> cmbSucursal;

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    private EmpleadoService empleadoService = new EmpleadoService();

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {

        configurarTabla();

        cargarDatos();

    }

    private void configurarTabla() {

        colId.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("idEmpleado"));
        colNombre.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("apellido"));
        colSucursal.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("idSucursal"));
        colFechaIngreso.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("fechaIngreso"));

    }

    private void cargarDatos() {

        List<Empleado> empleados = empleadoService.obtenerTodos();
        tablaEmpleados.setItems(FXCollections.observableArrayList(empleados));

    }

    @FXML
    private void buscarEmpleado() {

        String texto = txtBuscar.getText();

        List<Empleado> lista = empleadoService.buscar(texto);

        tablaEmpleados.setItems(
            FXCollections.observableArrayList(lista)
        );

    }

    @FXML
    private void nuevoEmpleado() {
        try {

            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/EmpleadoFormulario.fxml")
            );

            Parent root = loader.load();

            Stage stage = new Stage();

            stage.setTitle("Nuevo Empleado");

            stage.setScene(new Scene(root));

            stage.showAndWait();

            cargarDatos();

        } catch (IOException e) {

            mostrarAlerta("No se pudo abrir el formulario.");

        }
    }

    @FXML
    private void editarEmpleado() {

        Empleado empleado =
            tablaEmpleados.getSelectionModel().getSelectedItem();

        if(empleado == null){

            mostrarAlerta("Seleccione un empleado.");

            return;

        }

        try {

            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/EmpleadoFormulario.fxml")
            );

            Parent root = loader.load();

            EmpleadoFormularioController controller =
                    loader.getController();

            controller.setEmpleado(empleado);

            Stage stage = new Stage();

            stage.setTitle("Editar Empleado");

            stage.setScene(new Scene(root));

            stage.showAndWait();

            cargarDatos();

        } catch (IOException e) {

            mostrarAlerta("No se pudo abrir el formulario.");

        }

    }

    @FXML
    private void eliminarEmpleado() {

        Empleado empleado =
            tablaEmpleados.getSelectionModel().getSelectedItem();

        if(empleado == null){

            mostrarAlerta("Seleccione un empleado.");

            return;

        }

        try {
            empleadoService.eliminarEmpleado(empleado.getIdEmpleado());

            cargarDatos();

            mostrarAlerta("Empleado eliminado correctamente.");

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
