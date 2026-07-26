package com.novatech.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.novatech.model.Cliente;
import com.novatech.model.DetalleVenta;
import com.novatech.model.Empleado;
import com.novatech.model.Producto;
import com.novatech.model.Venta;
import com.novatech.service.ClienteService;
import com.novatech.service.EmpleadoService;
import com.novatech.service.VentaService;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class VentaController {

    @FXML
    private ComboBox<Cliente> cmbCliente;

    @FXML
    private ComboBox<Empleado> cmbEmpleado;

    @FXML
    private ComboBox<String> cmbMedioPago;

    @FXML
    private ComboBox<String> cmbCanal;

    @FXML
    private TableView<DetalleVenta> tblDetalles;

    @FXML
    private TableColumn<DetalleVenta, String> colProducto;

    @FXML
    private TableColumn<DetalleVenta, Integer> colCantidad;

    @FXML
    private TableColumn<DetalleVenta, Double> colPrecio;

    @FXML
    private TableColumn<DetalleVenta, Double> colSubtotal;

    @FXML
    private Label lblTotal;

    @FXML
    private TextField txtDescuento;

    @FXML
    private ComboBox<Producto> cmbProducto;

    @FXML
    private Spinner<Integer> spCantidad;

    private VentaService ventaService = new VentaService();

    private ObservableList<DetalleVenta> detalles =
        FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        cargarClientes();

        cargarEmpleados();

        cargarMediosPago();

        cargarCanales();

        configurarTabla();

    }

    private ClienteService clienteService = new ClienteService();

    private void cargarClientes() {

        cmbCliente.setItems(
            FXCollections.observableArrayList(
                clienteService.listarClientes()));

    }

    private EmpleadoService empleadoService = new EmpleadoService();

    private void cargarEmpleados() {

        cmbEmpleado.setItems(
            FXCollections.observableArrayList(
                empleadoService.listarEmpleados()));

    }

    private void cargarMediosPago() {

        cmbMedioPago.getItems().addAll(
            "Efectivo",
            "Tarjeta de Débito",
            "Tarjeta de Crédito",
            "Transferencia",
            "Mercado Pago"
        );

    }

    private void cargarCanales() {

        cmbCanal.getItems().addAll(
            "Local",
            "Online",
            "Teléfono"
        );

    }

    private void configurarTabla() {

        colProducto.setCellValueFactory(
            data -> new SimpleStringProperty(
                data.getValue().getProducto().getNombre()));

        colCantidad.setCellValueFactory(
            new PropertyValueFactory<>("cantidad"));

        colPrecio.setCellValueFactory(
            new PropertyValueFactory<>("precioUnitario"));

        colSubtotal.setCellValueFactory(
            new PropertyValueFactory<>("subtotal"));

        tblDetalles.setItems(detalles);

    }

    @FXML
    private void agregarProducto() {

        Producto producto = cmbProducto.getValue();

        if (producto == null) {
            mostrarError("Seleccione un producto.");
            return;
        }

        int cantidad = spCantidad.getValue();

        DetalleVenta detalle = new DetalleVenta();

        detalle.setProducto(producto);
        detalle.setCantidad(cantidad);
        detalle.setPrecioUnitario(producto.getPrecio());
        detalle.setSubtotal(producto.getPrecio() * cantidad);

        detalles.add(detalle);

        actualizarTotal();
    }

    @FXML
    private void eliminarProducto() {

        DetalleVenta detalle =
                tblDetalles.getSelectionModel().getSelectedItem();

        if(detalle != null){

            detalles.remove(detalle);

            actualizarTotal();

        }

    }

    private void actualizarTotal() {

        double total = 0;

        for (DetalleVenta d : detalles) {
            total += d.getSubtotal();
        }

        double descuento = 0;

        if (!txtDescuento.getText().isBlank()) {
            descuento = Double.parseDouble(txtDescuento.getText());
        }

        total *= (1 - descuento / 100);

        lblTotal.setText(String.format("$ %.2f", total));
    }

    @FXML
    private void confirmarVenta() {

        try {

            Venta venta = new Venta();

            venta.setCliente(
                    cmbCliente.getValue());

            venta.setEmpleado(
                    cmbEmpleado.getValue());

            venta.setFecha(
                    LocalDateTime.now());

            venta.setMedioPago(
                    cmbMedioPago.getValue());

            venta.setCanal(
                    cmbCanal.getValue());

            double descuento = 0;

            if (!txtDescuento.getText().isBlank()) {
                descuento = Double.parseDouble(txtDescuento.getText());
            }

            venta.setDescuento(descuento);

            venta.setDetalles(
                    new ArrayList<>(detalles));

            boolean ok =
                    ventaService.registrarVenta(venta);

            if (ok) {

                mostrarMensaje(
                        "Venta registrada correctamente.");

                limpiarFormulario();

            } else {

                mostrarError(
                        "No se pudo registrar la venta. Verifique el stock disponible e intente nuevamente.");

            }

        } catch (Exception e) {

            mostrarError(e.getMessage());

        }

    }

    private void limpiarFormulario(){

        cmbCliente.getSelectionModel().clearSelection();

        cmbEmpleado.getSelectionModel().clearSelection();

        cmbCanal.getSelectionModel().clearSelection();

        cmbMedioPago.getSelectionModel().clearSelection();

        txtDescuento.clear();

        detalles.clear();

        lblTotal.setText("$ 0.00");

    }


    private void mostrarMensaje(String mensaje){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setHeaderText(null);

        alert.setContentText(mensaje);

        alert.showAndWait();

    }

    private void mostrarError(String mensaje){

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setHeaderText("Error");

        alert.setContentText(mensaje);

        alert.showAndWait();

    }

}
