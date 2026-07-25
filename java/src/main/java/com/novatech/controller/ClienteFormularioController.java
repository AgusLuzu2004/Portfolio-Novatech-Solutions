package com.novatech.controller;

import com.novatech.model.Cliente;
import com.novatech.service.ClienteService;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ClienteFormularioController implements Initializable {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtEdad;

    @FXML
    private ComboBox<String> cmbSexo;

    @FXML
    private ComboBox<String> cmbProvincia;

    @FXML
    private TextField txtCiudad;

    @FXML
    private DatePicker dpFechaAlta;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnCancelar;

    private Cliente cliente;

    private ClienteService clienteService;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        clienteService = new ClienteService();

        cargarSexos();

        cargarProvincias();

        dpFechaAlta.setValue(LocalDate.now());

    }

    public void setCliente(Cliente cliente) {

        this.cliente = cliente;

        txtNombre.setText(cliente.getNombre());

        txtApellido.setText(cliente.getApellido());

        txtEdad.setText(String.valueOf(cliente.getEdad()));

        cmbSexo.setValue(cliente.getSexo());

        cmbProvincia.setValue(cliente.getProvincia());

        txtCiudad.setText(cliente.getCiudad());

        dpFechaAlta.setValue(cliente.getFechaAlta());

    }

    @FXML
    private void guardarCliente() {

        if (cliente == null) {

            clienteService.guardarCliente(cliente);

        }
        else {
            clienteService.actualizarCliente(cliente);
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

    private void cargarSexos() {

        cmbSexo.getItems().addAll(
                "Masculino",
                "Femenino",
                "Otro"
        );

    }

    private void cargarProvincias() {

        cmbProvincia.getItems().addAll(
                clienteService.listarProvincias()
        );

    }

}