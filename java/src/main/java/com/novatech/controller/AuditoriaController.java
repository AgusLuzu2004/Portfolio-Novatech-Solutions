package com.novatech.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.novatech.model.Auditoria;
import com.novatech.service.AuditoriaService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AuditoriaController {

    @FXML
    private TableView<Auditoria> tabla;

    @FXML
    private TableColumn<Auditoria, Integer> colId;

    @FXML
    private TableColumn<Auditoria, String> colUsuario;

    @FXML
    private TableColumn<Auditoria, String> colAccion;

    @FXML
    private TableColumn<Auditoria, String> colModulo;

    @FXML
    private TableColumn<Auditoria, LocalDateTime> colFecha;

    @FXML
    private ComboBox<String> cmbModulo;

    @FXML
    private ComboBox<String> cmbUsuario;

    @FXML
    private DatePicker dpInicio;

    @FXML
    private DatePicker dpFin;

    private AuditoriaService auditoriaService;
    private ObservableList<Auditoria> listaAuditoria;

    public void initialize() {

        auditoriaService = new AuditoriaService();

        configurarTabla();
        cargarAuditoria();

    }

    private void configurarTabla() {

        colId.setCellValueFactory(new PropertyValueFactory<>("idAuditoria"));
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        colAccion.setCellValueFactory(new PropertyValueFactory<>("accion"));
        colModulo.setCellValueFactory(new PropertyValueFactory<>("modulo"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

    }

    private void cargarAuditoria() {

        listaAuditoria = FXCollections.observableArrayList(
                auditoriaService.listar()
        );

        tabla.setItems(listaAuditoria);

    }

    @FXML
    private void buscar() {

        String usuario = cmbUsuario.getValue();
        String modulo = cmbModulo.getValue();

        LocalDate inicio = dpInicio.getValue();
        LocalDate fin = dpFin.getValue();

        listaAuditoria = FXCollections.observableArrayList();
        
        if (usuario != null && !usuario.isEmpty()) {
            listaAuditoria.addAll(auditoriaService.buscarPorUsuario(usuario));
        }
        if (modulo != null && !modulo.isEmpty()) {
            listaAuditoria.addAll(auditoriaService.buscarPorModulo(modulo));
        }
        if (inicio != null && fin != null) {
            listaAuditoria.addAll(auditoriaService.buscarPorFechas(inicio, fin));
        }

        tabla.setItems(listaAuditoria);

    }

    @FXML
    private void limpiarFiltros() {

        cmbUsuario.setValue(null);

        cmbModulo.setValue(null);

        dpInicio.setValue(null);

        dpFin.setValue(null);

        cargarAuditoria();

    }

    @FXML
    private void exportarExcel() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Información");

        alert.setHeaderText(null);

        alert.setContentText("Disponible en la Fase 25.");

        alert.showAndWait();

    }

    @FXML
    private void exportarPDF() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Información");

        alert.setHeaderText(null);

        alert.setContentText("Disponible en la Fase 25.");

        alert.showAndWait();

    }

}
