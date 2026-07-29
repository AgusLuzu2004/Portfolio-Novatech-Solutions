package com.novatech.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.novatech.model.Empleado;
import com.novatech.model.Venta;
import com.novatech.util.ExcelUtil;
import com.novatech.util.PDFUtil;

public class ExportacionService {

    private static final Logger logger =
            LoggerFactory.getLogger(ExportacionService.class);

    public void exportarClientesExcel(List<?> clientes, String ruta) {

        try {

            if (clientes == null || clientes.isEmpty()) {
                throw new IllegalArgumentException("No hay clientes para exportar.");
            }

            logger.info(
                    "Exportando {} clientes a Excel. Ruta: {}",
                    clientes.size(),
                    ruta
            );

            ExcelUtil.exportar(clientes, "Clientes", ruta);

            logger.info("Clientes exportados correctamente.");

        } catch (IllegalArgumentException e) {

            logger.warn("No fue posible exportar clientes: {}", e.getMessage());

            throw e;

        } catch (Exception e) {

            logger.error("Error al exportar clientes a Excel.", e);

            throw e;
        }

    }

    public void exportarProductosExcel(List<?> productos, String ruta) {

        try {

            if (productos == null || productos.isEmpty()) {
                throw new IllegalArgumentException("No hay productos para exportar.");
            }

            logger.info(
                    "Exportando {} productos a Excel. Ruta: {}",
                    productos.size(),
                    ruta
            );

            ExcelUtil.exportar(productos, "Productos", ruta);

            logger.info("Productos exportados correctamente.");

        } catch (IllegalArgumentException e) {

            logger.warn("No fue posible exportar productos: {}", e.getMessage());

            throw e;

        } catch (Exception e) {

            logger.error("Error al exportar productos a Excel.", e);

            throw e;
        }

    }

    public void exportarEmpleadosExcel(List<Empleado> empleados, String ruta) {

        try {

            if (empleados == null || empleados.isEmpty()) {
                throw new IllegalArgumentException("No hay empleados para exportar.");
            }

            logger.info(
                    "Exportando {} empleados a Excel. Ruta: {}",
                    empleados.size(),
                    ruta
            );

            ExcelUtil.exportar(empleados, "Empleados", ruta);

            logger.info("Empleados exportados correctamente.");

        } catch (IllegalArgumentException e) {

            logger.warn("No fue posible exportar empleados: {}", e.getMessage());

            throw e;

        } catch (Exception e) {

            logger.error("Error al exportar empleados a Excel.", e);

            throw e;
        }

    }

    public void exportarVentasExcel(List<Venta> ventas, String ruta) {

        try {

            if (ventas == null || ventas.isEmpty()) {
                throw new IllegalArgumentException("No hay ventas para exportar.");
            }

            logger.info(
                    "Exportando {} ventas a Excel. Ruta: {}",
                    ventas.size(),
                    ruta
            );

            ExcelUtil.exportarVentas(ventas, ruta);

            logger.info("Ventas exportadas correctamente a Excel.");

        } catch (IllegalArgumentException e) {

            logger.warn("No fue posible exportar ventas: {}", e.getMessage());

            throw e;

        } catch (Exception e) {

            logger.error("Error al exportar ventas a Excel.", e);

            throw e;
        }

    }

    public void exportarVentasPDF(List<Venta> ventas, String ruta) {

        try {

            if (ventas == null || ventas.isEmpty()) {
                throw new IllegalArgumentException("No hay ventas para exportar.");
            }

            logger.info(
                    "Exportando {} ventas a PDF. Ruta: {}",
                    ventas.size(),
                    ruta
            );

            PDFUtil.generarReporteVentas(ventas, ruta);

            logger.info("Ventas exportadas correctamente a PDF.");

        } catch (IllegalArgumentException e) {

            logger.warn("No fue posible exportar ventas a PDF: {}", e.getMessage());

            throw e;

        } catch (Exception e) {

            logger.error("Error al exportar ventas a PDF.", e);

            throw e;
        }

    }

}
