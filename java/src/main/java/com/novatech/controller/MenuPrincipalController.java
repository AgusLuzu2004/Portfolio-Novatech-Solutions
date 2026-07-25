package com.novatech.controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuPrincipalController {

    private void abrirVentana(String archivo, String titulo) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/" + archivo)
            );

            Stage stage = new Stage();

            stage.setTitle(titulo);

            stage.setScene(new Scene(loader.load()));

            stage.show();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public void abrirClientes() {

        abrirVentana("ClienteView.fxml", "Clientes");

    }

    public void abrirProductos() {

        abrirVentana("ProductoView.fxml", "Productos");

    }

    public void abrirEmpleados() {

        abrirVentana("EmpleadoView.fxml", "Empleados");

    }

    public void abrirVentas() {

        abrirVentana("VentaView.fxml", "Ventas");

    }

    public void abrirReportes() {

        abrirVentana("ReporteView.fxml", "Reportes");

    }

    public void abrirUsuarios() {

        abrirVentana("UsuarioView.fxml", "Usuarios");

    }

    public void abrirAuditoria() {

        abrirVentana("AuditoriaView.fxml", "Auditoría");

    }

    public void abrirConfiguracion() {

        abrirVentana("ConfiguracionView.fxml", "Configuración");

    }

    public void abrirExportaciones() {

        abrirVentana("ExportacionView.fxml", "Exportaciones");

    }

    public void abrirBackup() {

        abrirVentana("BackupView.fxml", "Backups");

    }

    public void abrirPreferencias() {

        abrirVentana("PreferenciasView.fxml", "Preferencias");

    }

    public void salir() {

        Platform.exit();

    }

}