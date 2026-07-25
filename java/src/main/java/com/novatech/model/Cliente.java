package com.novatech.model;

import java.time.LocalDate;

public class Cliente {

    private int idCliente;
    private String nombre;
    private String apellido;
    private int edad;
    private String sexo;
    private String provincia;
    private String ciudad;
    private LocalDate fechaAlta;

    public Cliente() {

    }

    public Cliente(int idCliente, String nombre, String apellido, int edad, String sexo, String provincia,
            String ciudad, LocalDate fechaAlta) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.sexo = sexo;
        this.provincia = provincia;
        this.ciudad = ciudad;
        this.fechaAlta = fechaAlta;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
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

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    @Override
    public String toString() {
        return "Cliente [idCliente=" + idCliente + ", nombre=" + nombre + ", apellido=" + apellido + ", edad=" + edad
                + ", sexo=" + sexo + ", provincia=" + provincia + ", ciudad=" + ciudad + ", fechaAlta=" + fechaAlta
                + "]";
    }

}
