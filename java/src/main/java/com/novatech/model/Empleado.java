package com.novatech.model;

import java.time.LocalDate;

public class Empleado {

    private int idEmpleado;
    private String nombre;
    private String apellido;
    private int idSucursal;
    private LocalDate fechaIngreso;

    public Empleado() {

    }

    public Empleado(int idEmpleado, String nombre, String apellido, int idSucursal, LocalDate fechaIngreso) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.apellido = apellido;
        this.idSucursal = idSucursal;
        this.fechaIngreso = fechaIngreso;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    @Override
    public String toString() {
        return "Empleado [idEmpleado=" + idEmpleado + ", nombre=" + nombre + ", apellido=" + apellido + ", idSucursal="
                + idSucursal + ", fechaIngreso=" + fechaIngreso + "]";
    }

}
