package com.novatech.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Venta {

    private int idVenta;
    private Cliente cliente;
    private Empleado empleado;
    private LocalDateTime fecha;
    private String medioPago;
    private String canal;
    private double descuento;
    private double total;
    private List<DetalleVenta> detalles = new ArrayList<>();

    public Venta() {

    }

    public Venta(int idVenta, Cliente cliente, Empleado empleado, LocalDateTime fecha, String medioPago, String canal,
            double descuento, double total, List<DetalleVenta> detalles) {
        this.idVenta = idVenta;
        this.cliente = cliente;
        this.empleado = empleado;
        this.fecha = fecha;
        this.medioPago = medioPago;
        this.canal = canal;
        this.descuento = descuento;
        this.total = total;
        this.detalles = detalles;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }

    @Override
    public String toString() {
        return "Venta [idVenta=" + idVenta + ", cliente=" + cliente + ", empleado=" + empleado + ", fecha=" + fecha
                + ", medioPago=" + medioPago + ", canal=" + canal + ", descuento=" + descuento + ", total=" + total
                + ", detalles=" + detalles + "]";
    }

}
