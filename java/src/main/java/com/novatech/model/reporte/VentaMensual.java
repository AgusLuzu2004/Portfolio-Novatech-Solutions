package com.novatech.model.reporte;

public class VentaMensual {

    private String mes;
    private double facturacion;

    public VentaMensual(String mes, double facturacion) {
        this.mes = mes;
        this.facturacion = facturacion;
    }

    public String getMes() {
        return mes;
    }

    public double getFacturacion() {
        return facturacion;
    }
}
