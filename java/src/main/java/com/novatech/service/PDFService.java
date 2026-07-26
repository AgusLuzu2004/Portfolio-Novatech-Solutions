package com.novatech.service;

import com.novatech.model.Venta;
import com.novatech.util.PDFUtil;

import java.util.List;

public class PDFService {

    public void generarReporteVentas(List<Venta> ventas, String ruta) {

        PDFUtil.generarReporteVentas(ventas, ruta);

    }

    public void generarReporteClientes(List<?> clientes, String ruta) {

        PDFUtil.generarReporte(clientes, "Clientes", ruta);

    }

    public void generarReporteProductos(List<?> productos, String ruta) {

        PDFUtil.generarReporte(productos, "Productos", ruta);

    }

}
