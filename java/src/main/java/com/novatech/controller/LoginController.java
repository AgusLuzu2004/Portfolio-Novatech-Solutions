package com.novatech.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.novatech.model.Usuario;
import com.novatech.service.UsuarioService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtContraseña;

    @FXML
    private Label lblError;

    private final UsuarioService usuarioService = new UsuarioService();

    private static final Logger logger =
        LoggerFactory.getLogger(LoginController.class);

    @FXML
    private void iniciarSesion() {

        String usuario = txtUsuario.getText();

        String contraseña = txtContraseña.getText();

        Usuario usuarioAutenticado =
                usuarioService.iniciarSesion(usuario, contraseña);

        if (usuarioAutenticado == null) {

            lblError.setText("Usuario o contraseña incorrectos.");

            return;

        }

        try {

            logger.info(
                    "Abriendo menú principal para el usuario {}.",
                    usuarioAutenticado.getUsuario());

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/MenuPrincipalView.fxml")
            );

            Parent raiz = loader.load();

            Stage stageActual = (Stage) txtUsuario.getScene().getWindow();

            Stage stageMenu = new Stage();

            stageMenu.setTitle("NovaTech Solutions");

            stageMenu.setScene(new Scene(raiz));

            stageMenu.setMaximized(true);

            stageMenu.show();

            stageActual.close();

        } catch (Exception e) {

            logger.error("Error al abrir el menú principal.", e);

            lblError.setText(
                    "No se pudo abrir el menú principal: " + e.getMessage());

        }

    }

}
