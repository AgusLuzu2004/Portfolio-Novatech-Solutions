package com.novatech.service;

import com.novatech.dao.ReporteDAO;
import com.novatech.model.reporte.*;

import java.util.List;

public class ReporteService {

    private final ReporteDAO reporteDAO;

    public ReporteService() {
        reporteDAO = new ReporteDAO();
    }

    public double getFacturacionTotal() {
        return reporteDAO.obtenerFacturacionTotal();
    }

    public int getCantidadVentas() {
        return reporteDAO.obtenerCantidadVentas();
    }

    public int getClientesActivos() {
        return reporteDAO.obtenerClientesActivos();
    }

    public int getProductosVendidos() {
        return reporteDAO.obtenerProductosVendidos();
    }

    public DashboardResumen getDashboardResumen() {
        return reporteDAO.obtenerDashboardResumen();
    }

    public List<VentaMensual> getVentasMensuales() {
        return reporteDAO.obtenerVentasMensuales();
    }

    public List<TopProducto> getTopProductos() {
        return reporteDAO.obtenerTopProductos();
    }

    public List<VentaCategoria> getVentasPorCategoria() {
        return reporteDAO.obtenerVentasPorCategoria();
    }

    public List<VentaProvincia> getVentasPorProvincia() {
        return reporteDAO.obtenerVentasPorProvincia();
    }

    public List<RankingEmpleado> getRankingEmpleados() {
        return reporteDAO.obtenerRankingEmpleados();
    }

    public List<MedioPagoReporte> getMediosPago() {
        return reporteDAO.obtenerMediosPago();
    }

    public List<VentaAnual> getComparacionAnual() {
        return reporteDAO.obtenerComparacionAnual();
    }

    public List<Integer> getAnios() {
        return reporteDAO.obtenerAnios();
    }

    public List<String> getProvincias() {
        return reporteDAO.obtenerProvincias();
    }

    public List<String> getCategorias() {
        return reporteDAO.obtenerCategorias();
    }

    public List<String> getSucursales() {
        return reporteDAO.obtenerSucursales();
    }

}
