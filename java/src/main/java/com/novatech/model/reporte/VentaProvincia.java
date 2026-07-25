package com.novatech.model.reporte;

public class VentaProvincia {

    private String provincia;
    private double facturacion;

    public VentaProvincia(String provincia, double facturacion) {
        this.provincia = provincia;
        this.facturacion = facturacion;
    }

    public String getProvincia() {
        return provincia;
    }

    public double getFacturacion() {
        return facturacion;
    }
}
