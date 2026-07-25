package com.novatech.service;

import com.novatech.util.ExcelUtil;
import com.novatech.util.PDFUtil;

import java.util.List;

public class ExportacionService {

    public void exportarClientesExcel(List<?> clientes, String ruta) {

        if (clientes == null || clientes.isEmpty()) {
            throw new IllegalArgumentException("No hay clientes para exportar.");
        }

        ExcelUtil.exportar(clientes, "Clientes", ruta);

    }

    public void exportarProductosExcel(List<?> productos, String ruta) {

        if (productos == null || productos.isEmpty()) {
            throw new IllegalArgumentException("No hay productos para exportar.");
        }

        ExcelUtil.exportar(productos, "Productos", ruta);

    }

    public void exportarVentasExcel(List<?> ventas, String ruta) {

        if (ventas == null || ventas.isEmpty()) {
            throw new IllegalArgumentException("No hay ventas para exportar.");
        }

        ExcelUtil.exportar(ventas, "Ventas", ruta);

    }

    public void exportarVentasPDF(List<?> ventas, String ruta) {

        if (ventas == null || ventas.isEmpty()) {
            throw new IllegalArgumentException("No hay ventas para exportar.");
        }

        PDFUtil.generarReporte(ventas, "Ventas", ruta);

    }

}
