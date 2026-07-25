package com.novatech.model;

import java.time.LocalDate;

public class Auditoria {

    private int idAuditoria;
    private String usuario;
    private String accion;
    private String modulo;
    private LocalDate fecha;

    public Auditoria() {
    
    }

    public Auditoria(int idAuditoria, String usuario, String accion, String modulo, LocalDate fecha) {
        this.idAuditoria = idAuditoria;
        this.usuario = usuario;
        this.accion = accion;
        this.modulo = modulo;
        this.fecha = fecha;
    }

    public int getIdAuditoria() {
        return idAuditoria;
    }

    public void setIdAuditoria(int idAuditoria) {
        this.idAuditoria = idAuditoria;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Auditoria [idAuditoria=" + idAuditoria + ", usuario=" + usuario + ", accion=" + accion + ", modulo="
                + modulo + ", fecha=" + fecha + "]";
    }

}
