package com.novatech.model;

public class Usuario {

    private int idUsuario;
    private String usuario;
    private String nombre;
    private String contraseña;
    private Rol rol;
    private boolean activo;

    public Usuario() {

    }

    public Usuario(int idUsuario, String usuario, String nombre, String contraseña, Rol rol, boolean activo) {
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.rol = rol;
        this.activo = activo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Usuario [idUsuario=" + idUsuario + ", usuario=" + usuario + ", nombre=" + nombre + ", contraseña="
                + contraseña + ", rol=" + rol + ", activo=" + activo + "]";
    }

}
