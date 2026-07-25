package com.novatech.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.novatech.model.Empleado;
import com.novatech.service.EmpleadoService;

import javafx.fxml.FXML;
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

        if (empleado == null) {

            empleadoService.guardarEmpleado(empleado);

        }
        else {

            empleadoService.actualizarEmpleado(empleado);

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

}
