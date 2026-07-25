package com.novatech.model.reporte;

public class MedioPagoReporte {

    private String medioPago;
    private int cantidad;

    public MedioPagoReporte(String medioPago, int cantidad) {
        this.medioPago = medioPago;
        this.cantidad = cantidad;
    }

    public String getMedioPago() {
        return medioPago;
    }

    public int getCantidad() {
        return cantidad;
    }
}
