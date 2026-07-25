package com.novatech.controller;

import com.novatech.model.reporte.*;
import com.novatech.service.ReporteService;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;

public class ReporteController {

    private ReporteService reporteService;

    @FXML
    private Label lblFacturacion;

    @FXML
    private Label lblVentas;

    @FXML
    private Label lblClientes;

    @FXML
    private Label lblProductos;

    @FXML
    private ComboBox<String> cbAnio;

    @FXML
    private ComboBox<String> cbProvincia;

    @FXML
    private ComboBox<String> cbCategoria;

    @FXML
    private ComboBox<String> cbSucursal;

    @FXML
    private LineChart<String, Number> lineVentasMensuales;

    @FXML
    private BarChart<String, Number> barCategorias;

    @FXML
    private BarChart<String, Number> barProductos;

    @FXML
    private BarChart<String, Number> barEmpleados;

    @FXML
    private PieChart pieProvincias;

    @FXML
    private PieChart pieMediosPago;

    @FXML
    public void initialize() {

        reporteService = new ReporteService();

        cargarDashboard();

        cargarGraficos();

        cargarCombos();

    }

    private void cargarDashboard() {

        DashboardResumen dashboard = reporteService.getDashboardResumen();

        lblFacturacion.setText(String.format("$%,.2f",
                dashboard.getFacturacionTotal()));

        lblVentas.setText(String.valueOf(
                dashboard.getCantidadVentas()));

        lblClientes.setText(String.valueOf(
                dashboard.getClientesActivos()));

        lblProductos.setText(String.valueOf(
                dashboard.getProductosVendidos()));

    }

    private void cargarVentasMensuales() {

        lineVentasMensuales.getData().clear();

        XYChart.Series<String, Number> serie = new XYChart.Series<>();

        for (VentaMensual venta :
                reporteService.getVentasMensuales()) {

            serie.getData().add(

                new XYChart.Data<>(

                        venta.getMes(),

                        venta.getFacturacion()

                )

            );

        }

        lineVentasMensuales.getData().add(serie);

    }

    private void cargarCategorias() {

        barCategorias.getData().clear();

        XYChart.Series<String, Number> serie = new XYChart.Series<>();

        for (VentaCategoria categoria :

                reporteService.getVentasPorCategoria()) {

            serie.getData().add(

                    new XYChart.Data<>(

                            categoria.getCategoria(),

                            categoria.getFacturacion()

                    )

            );

        }

        barCategorias.getData().add(serie);

    }

    private void cargarTopProductos() {

        barProductos.getData().clear();

        XYChart.Series<String, Number> serie = new XYChart.Series<>();

        for (TopProducto producto :

                reporteService.getTopProductos()) {

            serie.getData().add(

                    new XYChart.Data<>(

                            producto.getNombre(),

                            producto.getCantidad()

                    )

            );

        }

        barProductos.getData().add(serie);

    }

    private void cargarRanking() {

        barEmpleados.getData().clear();

        XYChart.Series<String, Number> serie = new XYChart.Series<>();

        for (RankingEmpleado empleado :

                reporteService.getRankingEmpleados()) {

            serie.getData().add(

                    new XYChart.Data<>(

                            empleado.getEmpleado(),

                            empleado.getFacturacion()

                    )

            );

        }

        barEmpleados.getData().add(serie);

    }

    private void cargarProvincias() {

        pieProvincias.getData().clear();

        for (VentaProvincia provincia :

                reporteService.getVentasPorProvincia()) {

            pieProvincias.getData().add(

                    new PieChart.Data(

                            provincia.getProvincia(),

                            provincia.getFacturacion()

                    )

            );

        }

    }

    private void cargarMediosPago() {

        pieMediosPago.getData().clear();

        for (MedioPagoReporte medio :

                reporteService.getMediosPago()) {

            pieMediosPago.getData().add(

                    new PieChart.Data(

                            medio.getMedioPago(),

                            medio.getCantidad()

                    )

            );

        }

    }

    private void cargarGraficos() {

        cargarVentasMensuales();

        cargarCategorias();

        cargarTopProductos();

        cargarRanking();

        cargarProvincias();

        cargarMediosPago();

    }

    private void cargarCombos() {

        cbAnio.getItems().clear();
        cbProvincia.getItems().clear();
        cbCategoria.getItems().clear();
        cbSucursal.getItems().clear();

        cbAnio.getItems().add("Todos");
        reporteService.getAnios()
                .forEach(anio -> cbAnio.getItems().add(String.valueOf(anio)));

        cbProvincia.getItems().add("Todas");
        cbProvincia.getItems().addAll(reporteService.getProvincias());

        cbCategoria.getItems().add("Todas");
        cbCategoria.getItems().addAll(reporteService.getCategorias());

        cbSucursal.getItems().add("Todas");
        cbSucursal.getItems().addAll(reporteService.getSucursales());

        cbAnio.getSelectionModel().selectFirst();
        cbProvincia.getSelectionModel().selectFirst();
        cbCategoria.getSelectionModel().selectFirst();
        cbSucursal.getSelectionModel().selectFirst();
    }

    @FXML
    private void actualizar() {

        cargarDashboard();

        cargarGraficos();

    }

    @FXML
    private void filtrar() {

        cargarDashboard();

        cargarGraficos();

    }
}
