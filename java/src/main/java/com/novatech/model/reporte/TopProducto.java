package com.novatech.model.reporte;

public class TopProducto {

    private String nombre;
    private int cantidad;

    public TopProducto(String nombre, int cantidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }
}
