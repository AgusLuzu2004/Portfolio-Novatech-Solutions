package com.novatech.service;

import com.novatech.util.PDFUtil;

import java.util.List;

public class PDFService {

    public void generarReporteVentas(List<?> ventas, String ruta) {

        PDFUtil.generarReporte(ventas, "Ventas", ruta);

    }

    public void generarReporteClientes(List<?> clientes, String ruta) {

        PDFUtil.generarReporte(clientes, "Clientes", ruta);

    }

    public void generarReporteProductos(List<?> productos, String ruta) {

        PDFUtil.generarReporte(productos, "Productos", ruta);

    }

}
