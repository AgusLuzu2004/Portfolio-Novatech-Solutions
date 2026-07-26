package com.novatech.model.reporte;

public class FiltroReporte {

    private Integer anio;
    private String provincia;
    private String categoria;
    private String sucursal;

    public FiltroReporte() {
    }

    public FiltroReporte(Integer anio, String provincia, String categoria, String sucursal) {
        this.anio = anio;
        this.provincia = provincia;
        this.categoria = categoria;
        this.sucursal = sucursal;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

}
