package com.novatech.model.reporte;

public class VentaCategoria {

    private String categoria;
    private double facturacion;

    public VentaCategoria(String categoria, double facturacion) {
        this.categoria = categoria;
        this.facturacion = facturacion;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getFacturacion() {
        return facturacion;
    }
}
