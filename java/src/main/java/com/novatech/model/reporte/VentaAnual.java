package com.novatech.model.reporte;

public class VentaAnual {

    private int anio;
    private double facturacion;

    public VentaAnual(int anio, double facturacion) {
        this.anio = anio;
        this.facturacion = facturacion;
    }

    public int getAnio() {
        return anio;
    }

    public double getFacturacion() {
        return facturacion;
    }
}
