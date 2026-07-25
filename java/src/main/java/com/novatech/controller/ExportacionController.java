package com.novatech.controller;

import com.novatech.model.Cliente;
import com.novatech.model.Venta;
import com.novatech.service.ClienteService;
import com.novatech.service.EmpleadoService;
import com.novatech.service.ExportacionService;
import com.novatech.service.ProductoService;
import com.novatech.service.VentaService;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class ExportacionController {

    private final ExportacionService exportacionService =
            new ExportacionService();

    private final ClienteService clienteService = new ClienteService();

    private final ProductoService productoService = new ProductoService();

    private final VentaService ventaService = new VentaService();

    private final EmpleadoService empleadoService = new EmpleadoService();

    @FXML
    private void exportarExcel() {

        try {

            FileChooser fileChooser = new FileChooser();

            fileChooser.setTitle("Guardar Excel");

            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter(
                            "Excel (*.xlsx)",
                            "*.xlsx"
                    )
            );

            File archivo = fileChooser.showSaveDialog(null);

            if (archivo != null) {

                List<Cliente> clientes = clienteService.listarClientes();

                exportacionService.exportarClientesExcel(
                        clientes,
                        archivo.getAbsolutePath()
                );

                mostrarInformacion(
                        "Exportación",
                        "Archivo Excel generado correctamente."
                );

            }

        } catch (Exception e) {

            mostrarError(e.getMessage());

        }

    }

    @FXML
    private void exportarPDF() {

        try {

            FileChooser fileChooser = new FileChooser();

            fileChooser.setTitle("Guardar PDF");

            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter(
                            "PDF (*.pdf)",
                            "*.pdf"
                    )
            );

            File archivo = fileChooser.showSaveDialog(null);

            if (archivo != null) {

                List<Venta> ventas = ventaService.listarVentas();

                exportacionService.exportarVentasPDF(
                        ventas,
                        archivo.getAbsolutePath()
                );

                mostrarInformacion(
                        "Exportación",
                        "PDF generado correctamente."
                );

            }

        } catch (Exception e) {

            mostrarError(e.getMessage());

        }

    }

    private void mostrarInformacion(String titulo, String mensaje) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        alert.showAndWait();

    }

    private void mostrarError(String mensaje) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        alert.showAndWait();

    }

}
