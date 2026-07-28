package com.novatech.controller;

import com.novatech.model.reporte.*;
import com.novatech.service.ReporteService;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ReporteController {

    public static class FilaResumen {

        private final SimpleStringProperty indicador;
        private final SimpleStringProperty valor;

        public FilaResumen(String indicador, String valor) {
            this.indicador = new SimpleStringProperty(indicador);
            this.valor = new SimpleStringProperty(valor);
        }

        public String getIndicador() {
            return indicador.get();
        }

        public String getValor() {
            return valor.get();
        }

    }

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
    private TableView<FilaResumen> tablaResumen;

    @FXML
    private TableColumn<FilaResumen, String> colIndicador;

    @FXML
    private TableColumn<FilaResumen, String> colValor;

    @FXML
    public void initialize() {

        reporteService = new ReporteService();

        colIndicador.setCellValueFactory(new PropertyValueFactory<>("indicador"));

        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));

        cargarCombos();

        FiltroReporte filtroInicial = obtenerFiltroActual();

        cargarDashboard(filtroInicial);

        cargarGraficos(filtroInicial);

    }

    private FiltroReporte obtenerFiltroActual() {

        Integer anio = null;
        String anioSeleccionado = cbAnio.getValue();

        if (anioSeleccionado != null && !anioSeleccionado.equals("Todos")) {
            anio = Integer.parseInt(anioSeleccionado);
        }

        String provincia = valorOTodos(cbProvincia.getValue(), "Todas");
        String categoria = valorOTodos(cbCategoria.getValue(), "Todas");
        String sucursal = valorOTodos(cbSucursal.getValue(), "Todas");

        return new FiltroReporte(anio, provincia, categoria, sucursal);

    }

    private String valorOTodos(String valor, String opcionTodos) {

        if (valor == null || valor.equals(opcionTodos)) {
            return null;
        }

        return valor;

    }

    private void cargarDashboard(FiltroReporte filtro) {

        DashboardResumen dashboard = reporteService.getDashboardResumen(filtro);

        lblFacturacion.setText(String.format("$%,.2f",
                dashboard.getFacturacionTotal()));

        lblVentas.setText(String.valueOf(
                dashboard.getCantidadVentas()));

        lblClientes.setText(String.valueOf(
                dashboard.getClientesActivos()));

        lblProductos.setText(String.valueOf(
                dashboard.getProductosVendidos()));

        tablaResumen.getItems().setAll(
                new FilaResumen("Facturación Total", String.format("$%,.2f", dashboard.getFacturacionTotal())),
                new FilaResumen("Cantidad de Ventas", String.valueOf(dashboard.getCantidadVentas())),
                new FilaResumen("Clientes Activos", String.valueOf(dashboard.getClientesActivos())),
                new FilaResumen("Productos Vendidos", String.valueOf(dashboard.getProductosVendidos()))
        );

    }

    private void cargarVentasMensuales(FiltroReporte filtro) {

        lineVentasMensuales.getData().clear();

        XYChart.Series<String, Number> serie = new XYChart.Series<>();

        for (VentaMensual venta :
                reporteService.getVentasMensuales(filtro)) {

            serie.getData().add(

                new XYChart.Data<>(

                        venta.getMes(),

                        venta.getFacturacion()

                )

            );

        }

        lineVentasMensuales.getData().add(serie);

    }

    private void cargarCategorias(FiltroReporte filtro) {

        barCategorias.getData().clear();

        XYChart.Series<String, Number> serie = new XYChart.Series<>();

        for (VentaCategoria categoria :

                reporteService.getVentasPorCategoria(filtro)) {

            serie.getData().add(

                    new XYChart.Data<>(

                            categoria.getCategoria(),

                            categoria.getFacturacion()

                    )

            );

        }

        barCategorias.getData().add(serie);

    }

    private void cargarTopProductos(FiltroReporte filtro) {

        barProductos.getData().clear();

        XYChart.Series<String, Number> serie = new XYChart.Series<>();

        for (TopProducto producto :

                reporteService.getTopProductos(filtro)) {

            serie.getData().add(

                    new XYChart.Data<>(

                            producto.getNombre(),

                            producto.getCantidad()

                    )

            );

        }

        barProductos.getData().add(serie);

    }

    private void cargarRanking(FiltroReporte filtro) {

        barEmpleados.getData().clear();

        XYChart.Series<String, Number> serie = new XYChart.Series<>();

        for (RankingEmpleado empleado :

                reporteService.getRankingEmpleados(filtro)) {

            serie.getData().add(

                    new XYChart.Data<>(

                            empleado.getEmpleado(),

                            empleado.getFacturacion()

                    )

            );

        }

        barEmpleados.getData().add(serie);

    }

    private void cargarProvincias(FiltroReporte filtro) {

        pieProvincias.getData().clear();

        for (VentaProvincia provincia :

                reporteService.getVentasPorProvincia(filtro)) {

            pieProvincias.getData().add(

                    new PieChart.Data(

                            provincia.getProvincia(),

                            provincia.getFacturacion()

                    )

            );

        }

    }

    private void cargarMediosPago(FiltroReporte filtro) {

        pieMediosPago.getData().clear();

        for (MedioPagoReporte medio :

                reporteService.getMediosPago(filtro)) {

            pieMediosPago.getData().add(

                    new PieChart.Data(

                            medio.getMedioPago(),

                            medio.getCantidad()

                    )

            );

        }

    }

    private void cargarGraficos(FiltroReporte filtro) {

        cargarVentasMensuales(filtro);

        cargarCategorias(filtro);

        cargarTopProductos(filtro);

        cargarRanking(filtro);

        cargarProvincias(filtro);

        cargarMediosPago(filtro);

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

        FiltroReporte filtro = obtenerFiltroActual();

        cargarDashboard(filtro);

        cargarGraficos(filtro);

    }

    @FXML
    private void filtrar() {

        FiltroReporte filtro = obtenerFiltroActual();

        cargarDashboard(filtro);

        cargarGraficos(filtro);

    }
}
