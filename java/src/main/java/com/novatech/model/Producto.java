package com.novatech.model;

public class Producto {

    private int idProducto;
    private String nombre;
    private String marca;
    private int idCategoria;
    private double precio;
    private int stock;

    public Producto() {

    }

    public Producto(int idProducto, String nombre, String marca, int idCategoria, double precio, int stock) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.marca = marca;
        this.idCategoria = idCategoria;
        this.precio = precio;
        this.stock = stock;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Producto [idProducto=" + idProducto + ", nombre=" + nombre + ", marca=" + marca + ", idCategoria="
                + idCategoria + ", precio=" + precio + ", stock=" + stock + "]";
    }

}
