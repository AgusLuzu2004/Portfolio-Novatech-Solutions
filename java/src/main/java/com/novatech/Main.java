package com.novatech;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static final Logger logger =
        LoggerFactory.getLogger(Main.class);

    @Override
    public void start(Stage stage) {

        try {

            logger.info("Iniciando NovaTech Solutions.");

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/LoginView.fxml")
            );

            Scene scene = new Scene(loader.load());

            logger.info("Pantalla de inicio de sesión cargada correctamente.");

            stage.setTitle("NovaTech Solutions - Iniciar sesión");

            stage.setScene(scene);

            stage.setResizable(false);

            stage.show();

        } catch (Exception e) {

            logger.error("Error al iniciar la aplicación.", e);

        }

    }

    public static void main(String[] args) {
        launch(args);
    }

}
