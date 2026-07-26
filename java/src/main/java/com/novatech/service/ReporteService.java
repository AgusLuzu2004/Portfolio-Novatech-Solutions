package com.novatech.service;

import com.novatech.dao.ReporteDAO;
import com.novatech.model.reporte.*;

import java.util.List;

public class ReporteService {

    private final ReporteDAO reporteDAO;

    public ReporteService() {
        reporteDAO = new ReporteDAO();
    }

    public double getFacturacionTotal(FiltroReporte filtro) {
        return reporteDAO.obtenerFacturacionTotal(filtro);
    }

    public int getCantidadVentas(FiltroReporte filtro) {
        return reporteDAO.obtenerCantidadVentas(filtro);
    }

    public int getClientesActivos(FiltroReporte filtro) {
        return reporteDAO.obtenerClientesActivos(filtro);
    }

    public int getProductosVendidos(FiltroReporte filtro) {
        return reporteDAO.obtenerProductosVendidos(filtro);
    }

    public DashboardResumen getDashboardResumen(FiltroReporte filtro) {
        return reporteDAO.obtenerDashboardResumen(filtro);
    }

    public List<VentaMensual> getVentasMensuales(FiltroReporte filtro) {
        return reporteDAO.obtenerVentasMensuales(filtro);
    }

    public List<TopProducto> getTopProductos(FiltroReporte filtro) {
        return reporteDAO.obtenerTopProductos(filtro);
    }

    public List<VentaCategoria> getVentasPorCategoria(FiltroReporte filtro) {
        return reporteDAO.obtenerVentasPorCategoria(filtro);
    }

    public List<VentaProvincia> getVentasPorProvincia(FiltroReporte filtro) {
        return reporteDAO.obtenerVentasPorProvincia(filtro);
    }

    public List<RankingEmpleado> getRankingEmpleados(FiltroReporte filtro) {
        return reporteDAO.obtenerRankingEmpleados(filtro);
    }

    public List<MedioPagoReporte> getMediosPago(FiltroReporte filtro) {
        return reporteDAO.obtenerMediosPago(filtro);
    }

    public List<VentaAnual> getComparacionAnual(FiltroReporte filtro) {
        return reporteDAO.obtenerComparacionAnual(filtro);
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
