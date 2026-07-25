package com.novatech.model.reporte;

public class RankingEmpleado {

    private String empleado;
    private double facturacion;

    public RankingEmpleado(String empleado, double facturacion) {
        this.empleado = empleado;
        this.facturacion = facturacion;
    }

    public String getEmpleado() {
        return empleado;
    }

    public double getFacturacion() {
        return facturacion;
    }
}
