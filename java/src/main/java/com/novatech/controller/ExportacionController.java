package com.novatech.controller;

import com.novatech.model.Venta;
import com.novatech.service.ClienteService;
import com.novatech.service.EmpleadoService;
import com.novatech.service.ExportacionService;
import com.novatech.service.PDFService;
import com.novatech.service.ProductoService;
import com.novatech.service.VentaService;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class ExportacionController {

    @FXML
    private ComboBox<String> cmbTipo;

    private final ExportacionService exportacionService =
            new ExportacionService();

    private final PDFService pdfService = new PDFService();

    private final ClienteService clienteService = new ClienteService();

    private final ProductoService productoService = new ProductoService();

    private final VentaService ventaService = new VentaService();

    private final EmpleadoService empleadoService = new EmpleadoService();

    @FXML
    public void initialize() {

        cmbTipo.getItems().addAll(
                "Clientes",
                "Productos",
                "Empleados",
                "Ventas"
        );

        cmbTipo.getSelectionModel().selectFirst();

    }

    @FXML
    private void exportarExcel() {

        String tipo = cmbTipo.getValue();

        if (tipo == null) {

            mostrarError("Seleccioná qué querés exportar.");

            return;

        }

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

            if (archivo == null) {
                return;
            }

            String ruta = archivo.getAbsolutePath();

            switch (tipo) {

                case "Clientes":
                    exportacionService.exportarClientesExcel(
                            clienteService.listarClientes(), ruta);
                    break;

                case "Productos":
                    exportacionService.exportarProductosExcel(
                            productoService.obtenerTodos(), ruta);
                    break;

                case "Empleados":
                    exportacionService.exportarEmpleadosExcel(
                            empleadoService.obtenerTodos(), ruta);
                    break;

                case "Ventas":
                    List<Venta> ventas = ventaService.listarVentas();
                    exportacionService.exportarVentasExcel(ventas, ruta);
                    break;

                default:
                    mostrarError("Tipo de exportación no reconocido: " + tipo);
                    return;

            }

            mostrarInformacion(
                    "Exportación",
                    "Archivo Excel de " + tipo + " generado correctamente."
            );

        } catch (Exception e) {

            mostrarError(e.getMessage());

        }

    }

    @FXML
    private void exportarPDF() {

        String tipo = cmbTipo.getValue();

        if (tipo == null) {

            mostrarError("Seleccioná qué querés exportar.");

            return;

        }

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

            if (archivo == null) {
                return;
            }

            String ruta = archivo.getAbsolutePath();

            switch (tipo) {

                case "Clientes":
                    pdfService.generarReporteClientes(
                            clienteService.listarClientes(), ruta);
                    break;

                case "Productos":
                    pdfService.generarReporteProductos(
                            productoService.obtenerTodos(), ruta);
                    break;

                case "Empleados":
                    pdfService.generarReporteEmpleados(
                            empleadoService.obtenerTodos(), ruta);
                    break;

                case "Ventas":
                    List<Venta> ventas = ventaService.listarVentas();
                    exportacionService.exportarVentasPDF(ventas, ruta);
                    break;

                default:
                    mostrarError("Tipo de exportación no reconocido: " + tipo);
                    return;

            }

            mostrarInformacion(
                    "Exportación",
                    "PDF de " + tipo + " generado correctamente."
            );

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
