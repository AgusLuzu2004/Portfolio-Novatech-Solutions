package com.novatech.controller;

import java.io.IOException;
import java.net.URL;
import com.novatech.model.Cliente;
import com.novatech.service.ClienteService;
import java.util.List;
import java.util.ResourceBundle;

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

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.collections.FXCollections;

public class ClienteController implements Initializable {

    @FXML
    private TableView<Cliente> tablaClientes;

    @FXML
    private TableColumn<Cliente, Integer> colId;

    @FXML
    private TableColumn<Cliente, String> colNombre;

    @FXML
    private TableColumn<Cliente, String> colApellido;

    @FXML
    private TableColumn<Cliente, Integer> colEdad;

    @FXML
    private TableColumn<Cliente, String> colProvincia;

    @FXML
    private TableColumn<Cliente, String> colCiudad;

    @FXML
    private TextField txtBuscar;

    @FXML
    private ComboBox<String> cmbProvincia;

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    private ClienteService clienteService = new ClienteService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        configurarTabla();

        cargarClientes();

        cargarProvincias();

    }

    private void configurarTabla() {

        colId.setCellValueFactory(new PropertyValueFactory<>("idCliente"));

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));

        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));

        colProvincia.setCellValueFactory(new PropertyValueFactory<>("provincia"));

        colCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));

    }

    private void cargarClientes() {

        List<Cliente> clientes = clienteService.listarClientes();

        tablaClientes.setItems(
            FXCollections.observableArrayList(clientes)
        );

    }

    private void cargarProvincias() {
        List<String> provincias = clienteService.listarProvincias();

        cmbProvincia.setItems(
            FXCollections.observableArrayList(provincias)
        );
    }

    @FXML
    private void buscarCliente() {

        String texto = txtBuscar.getText();

        List<Cliente> lista = clienteService.buscar(texto);

        tablaClientes.setItems(
            FXCollections.observableArrayList(lista)
        );

    }

    @FXML
    private void filtrarProvincia() {

        String provincia = cmbProvincia.getValue();

        List<Cliente> lista =
            clienteService.buscarPorProvincia(provincia);

        tablaClientes.setItems(
            FXCollections.observableArrayList(lista)
        );

    }

    @FXML
    private void nuevoCliente() {

        try {

            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/ClienteFormulario.fxml")
            );

            Parent root = loader.load();

            Stage stage = new Stage();

            stage.setTitle("Nuevo Cliente");

            stage.setScene(new Scene(root));

            stage.showAndWait();

            cargarClientes();

        } catch (IOException e) {

            mostrarAlerta("No se pudo abrir el formulario.");

        }

    }

    @FXML
    private void editarCliente() {

        Cliente cliente =
            tablaClientes.getSelectionModel().getSelectedItem();

        if(cliente == null){

            mostrarAlerta("Seleccione un cliente.");

            return;

        }

        try {

            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/ClienteFormulario.fxml")
            );

            Parent root = loader.load();

            ClienteFormularioController controller =
                    loader.getController();

            controller.setCliente(cliente);

            Stage stage = new Stage();

            stage.setTitle("Editar Cliente");

            stage.setScene(new Scene(root));

            stage.showAndWait();

            cargarClientes();

        } catch (IOException e) {

            mostrarAlerta("No se pudo abrir el formulario.");

        }

    }

    @FXML
    private void eliminarCliente() {

        Cliente cliente =
            tablaClientes.getSelectionModel().getSelectedItem();

        if(cliente == null){

            mostrarAlerta("Seleccione un cliente.");

            return;

        }

        try {
            clienteService.eliminarCliente(cliente.getIdCliente());

            cargarClientes();

            mostrarAlerta("Cliente eliminado correctamente.");

        } catch (RuntimeException e) {

            mostrarAlerta(e.getMessage());

        }

    }

    private void limpiarFormulario() {

        txtBuscar.clear();

        cmbProvincia.getSelectionModel().clearSelection();

    }

    private void mostrarAlerta(String mensaje){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setHeaderText(null);

        alert.setContentText(mensaje);

        alert.showAndWait();

    }

}
