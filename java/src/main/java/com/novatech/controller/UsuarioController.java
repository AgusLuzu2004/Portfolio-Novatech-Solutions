package com.novatech.controller;

import java.io.IOException;
import java.util.List;

import com.novatech.model.Rol;
import com.novatech.model.Usuario;
import com.novatech.service.UsuarioService;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class UsuarioController {

    @FXML
    private TableView<Usuario> tablaUsuarios;

    @FXML
    private TableColumn<Usuario, Integer> colId;

    @FXML
    private TableColumn<Usuario, String> colUsuario;

    @FXML
    private TableColumn<Usuario, String> colNombre;

    @FXML
    private TableColumn<Usuario, String> colRol;

    @FXML
    private TableColumn<Usuario, Boolean> colActivo;

    @FXML
    private TextField txtUsuario;

    @FXML
    private TextField txtNombre;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private ComboBox<Rol> cmbRol;

    @FXML
    private CheckBox chkActivo;

    @FXML
    private TextField txtBuscar;

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    private UsuarioService usuarioService = new UsuarioService();

    @FXML
    public void initialize() {

        configurarTabla();

        cargarUsuarios();

    }

    private void configurarTabla() {

        colId.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        colActivo.setCellValueFactory(new PropertyValueFactory<>("activo"));

    }

    private void cargarUsuarios() {

        List<Usuario> usuarios = usuarioService.listarUsuarios();
        tablaUsuarios.setItems(FXCollections.observableArrayList(usuarios));

    }

    @FXML
    private void buscarUsuario() {

        String texto = txtBuscar.getText();

        List<Usuario> lista = usuarioService.buscar(texto);

        tablaUsuarios.setItems(
            FXCollections.observableArrayList(lista)
        );

    }

    @FXML
    private void nuevoUsuario() {

        try {

            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/UsuarioFormulario.fxml")
            );

            Parent root = loader.load();

            Stage stage = new Stage();

            stage.setTitle("Nuevo Usuario");

            stage.setScene(new Scene(root));

            stage.showAndWait();

            cargarUsuarios();

        } catch (IOException e) {

            mostrarAlerta("No se pudo abrir el formulario.");

        }

    }

    @FXML
    private void editarUsuario() {

        Usuario usuario = tablaUsuarios.getSelectionModel().getSelectedItem();

        if (usuario == null) {

            mostrarAlerta("Seleccione un usuario.");

            return;

        } try {

            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/UsuarioFormulario.fxml")
            );

            Parent root = loader.load();

            UsuarioFormularioController controller =
                    loader.getController();

            controller.setUsuario(usuario);

            Stage stage = new Stage();

            stage.setTitle("Editar Usuario");

            stage.setScene(new Scene(root));

            stage.showAndWait();

            cargarUsuarios();

        } catch (IOException e) {

            mostrarAlerta("No se pudo abrir el formulario.");

        }

    }

    @FXML
    private void activarUsuario() {

        Usuario usuario = tablaUsuarios.getSelectionModel().getSelectedItem();

        if (usuario == null) {

            mostrarAlerta("Seleccione un usuario.");

            return;

        }

        if (usuarioService.activarUsuario(usuario.getIdUsuario())) {

            mostrarAlerta("Usuario activado correctamente.");

            cargarUsuarios();

        } else {

            mostrarAlerta("No se pudo activar el usuario.");

        }

    }

    @FXML
    private void desactivarUsuario() {

        Usuario usuario = tablaUsuarios.getSelectionModel().getSelectedItem();

        if (usuario == null) {

            mostrarAlerta("Seleccione un usuario.");

            return;

        }

        if (usuarioService.desactivarUsuario(usuario.getIdUsuario())) {

            mostrarAlerta("Usuario desactivado correctamente.");

            cargarUsuarios();

        } else {

            mostrarAlerta("No se pudo desactivar el usuario.");

        }

    }

    @FXML
    private void seleccionarUsuario() {

        Usuario usuario = tablaUsuarios.getSelectionModel().getSelectedItem();

        if (usuario == null) {
            return;
        }

        txtUsuario.setText(usuario.getUsuario());
        txtNombre.setText(usuario.getNombre());
        cmbRol.setValue(usuario.getRol());
        chkActivo.setSelected(usuario.isActivo());

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
