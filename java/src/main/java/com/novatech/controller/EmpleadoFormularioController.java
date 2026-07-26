package com.novatech.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

import com.novatech.model.Empleado;
import com.novatech.service.EmpleadoService;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EmpleadoFormularioController {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellido;

    @FXML
    private ComboBox<Integer> cmbSucursal;

    @FXML
    private TextField txtFechaIngreso;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnCancelar;

    private Empleado empleado;

    private EmpleadoService empleadoService;

    public void initialize(URL url, ResourceBundle rb) {

        empleadoService = new EmpleadoService();

        cargarDatos();

    }

    public void setEmpleado(Empleado empleado) {

        this.empleado = empleado;

        txtNombre.setText(empleado.getNombre());

        txtApellido.setText(empleado.getApellido());

        cmbSucursal.setValue(empleado.getIdSucursal());

        txtFechaIngreso.setText(String.valueOf(empleado.getFechaIngreso()));

    }

    @FXML
    private void guardarEmpleado() {

        try {

            if (empleado == null) {
                empleado = new Empleado();
            }

            empleado.setNombre(txtNombre.getText());
            empleado.setApellido(txtApellido.getText());
            empleado.setIdSucursal(cmbSucursal.getValue());
            empleado.setFechaIngreso(LocalDate.parse(txtFechaIngreso.getText()));

            if (empleado.getIdEmpleado() == 0) {
                empleadoService.guardarEmpleado(empleado);
            } else {
                empleadoService.actualizarEmpleado(empleado);
            }

            cerrarVentana();

        } catch (DateTimeParseException e) {

            mostrarError("La fecha de ingreso debe tener el formato AAAA-MM-DD.");

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

        cmbSucursal.getItems().addAll(1, 2, 3, 4, 5);
    }

    private void mostrarError(String mensaje) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setHeaderText("Error");

        alert.setContentText(mensaje);

        alert.showAndWait();

    }

}
