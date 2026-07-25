package com.novatech.model.reporte;

public class DashboardResumen {

    private double facturacionTotal;
    private int cantidadVentas;
    private int clientesActivos;
    private int productosVendidos;

    public DashboardResumen(double facturacionTotal,
                            int cantidadVentas,
                            int clientesActivos,
                            int productosVendidos) {

        this.facturacionTotal = facturacionTotal;
        this.cantidadVentas = cantidadVentas;
        this.clientesActivos = clientesActivos;
        this.productosVendidos = productosVendidos;
    }

    public double getFacturacionTotal() {
        return facturacionTotal;
    }

    public int getCantidadVentas() {
        return cantidadVentas;
    }

    public int getClientesActivos() {
        return clientesActivos;
    }

    public int getProductosVendidos() {
        return productosVendidos;
    }
}
