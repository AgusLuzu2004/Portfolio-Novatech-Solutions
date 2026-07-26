package com.novatech.service;

import java.util.List;

import com.novatech.model.Venta;
import com.novatech.util.ExcelUtil;
import com.novatech.util.PDFUtil;

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

    public void exportarVentasExcel(List<Venta> ventas, String ruta) {

        ExcelUtil.exportarVentas(ventas, ruta);

    }

    public void exportarVentasPDF(List<Venta> ventas, String ruta) {

        PDFUtil.generarReporteVentas(ventas, ruta);

    }

}
