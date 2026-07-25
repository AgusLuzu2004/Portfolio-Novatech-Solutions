package com.novatech.controller;

import com.novatech.model.Rol;
import com.novatech.model.Usuario;
import com.novatech.service.UsuarioService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UsuarioFormularioController {

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
    private Button btnGuardar;

    @FXML
    private Button btnCancelar;

    private Usuario usuario;

    private UsuarioService usuarioService;


    @FXML
    public void initialize() {

        usuarioService = new UsuarioService();

        cargarDatos();

    }

    private void cargarDatos() {

        cmbRol.getItems().setAll(Rol.values());

    }

    public void setUsuario(Usuario usuario) {

        this.usuario = usuario;

        txtUsuario.setText(usuario.getUsuario());

        txtNombre.setText(usuario.getNombre());

        cmbRol.setValue(usuario.getRol());

        chkActivo.setSelected(usuario.isActivo());

    }

    @FXML
    private void guardarUsuario() {

        if (usuario == null) {

            usuario = new Usuario();

        }

        usuario.setUsuario(txtUsuario.getText());

        usuario.setNombre(txtNombre.getText());

        usuario.setContraseña(txtPassword.getText());

        usuario.setRol(cmbRol.getValue());

        usuario.setActivo(chkActivo.isSelected());

        if (usuario.getIdUsuario() == 0) {

            usuarioService.crearUsuario(usuario);

        } else {

            usuarioService.actualizarUsuario(usuario);

        }

        cerrarVentana();

    }

    @FXML
    private void cancelar() {

        cerrarVentana();

    }

    private void cerrarVentana() {

        Stage stage = (Stage) btnCancelar.getScene().getWindow();

        stage.close();

    }

}
