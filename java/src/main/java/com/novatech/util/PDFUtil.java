package com.novatech.util;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.novatech.model.DetalleVenta;
import com.novatech.model.Venta;

public class PDFUtil {

    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private PDFUtil() {
    }

    public static <T> void generarReporte(List<T> datos, String titulo, String ruta) {

        if (datos == null || datos.isEmpty()) {
            throw new IllegalArgumentException("No hay datos para exportar.");
        }

        Document document = new Document(PageSize.A4.rotate());

        try {

            PdfWriter.getInstance(document, new FileOutputStream(ruta));

            document.open();

            Font tituloFont = FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD,
                    18,
                    Color.BLUE
            );

            Paragraph tituloDocumento = new Paragraph(titulo, tituloFont);
            tituloDocumento.setAlignment(Element.ALIGN_CENTER);

            document.add(tituloDocumento);

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Fecha de generación: " + LocalDate.now()));
            document.add(new Paragraph(" "));

            Field[] campos = datos.get(0).getClass().getDeclaredFields();

            PdfPTable tabla = new PdfPTable(campos.length);

            tabla.setWidthPercentage(100);

            Font encabezadoFont = FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD,
                    11,
                    Color.WHITE
            );

            for (Field campo : campos) {

                PdfPCell celda = new PdfPCell(
                        new Phrase(campo.getName(), encabezadoFont)
                );

                celda.setBackgroundColor(Color.DARK_GRAY);

                celda.setHorizontalAlignment(Element.ALIGN_CENTER);

                tabla.addCell(celda);

            }

            for (T objeto : datos) {

                for (Field campo : campos) {

                    campo.setAccessible(true);

                    Object valor = campo.get(objeto);

                    if (valor == null) {

                        tabla.addCell("");

                    } else if (valor instanceof LocalDate) {

                        tabla.addCell(valor.toString());

                    } else if (valor instanceof LocalDateTime) {

                        tabla.addCell(valor.toString());

                    } else {

                        tabla.addCell(String.valueOf(valor));

                    }

                }

            }

            document.add(tabla);

            document.add(new Paragraph(" "));
            document.add(new Paragraph(
                    "Documento generado por NovaTech Solutions",
                    FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10)
            ));

        } catch (DocumentException | IOException | IllegalAccessException e) {

            throw new RuntimeException("Error al generar el PDF.", e);

        } finally {

            if (document.isOpen()) {
                document.close();
            }

        }

    }

    public static void generarReporteVentas(List<Venta> ventas, String ruta) {

        if (ventas == null || ventas.isEmpty()) {
            throw new IllegalArgumentException("No hay ventas para exportar.");
        }

        Document document = new Document(PageSize.A4.rotate());

        String[] columnas = {
                "Id Venta", "Fecha", "Cliente", "Empleado", "Producto",
                "Cantidad", "Precio Unit.", "Subtotal", "Desc. (%)",
                "Total c/desc.", "Medio de Pago", "Canal"
        };

        try {

            PdfWriter.getInstance(document, new FileOutputStream(ruta));

            document.open();

            Font tituloFont = FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD, 18, Color.BLUE
            );

            Paragraph tituloDocumento = new Paragraph("Ventas", tituloFont);
            tituloDocumento.setAlignment(Element.ALIGN_CENTER);
            document.add(tituloDocumento);

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Fecha de generación: " + LocalDate.now()));
            document.add(new Paragraph(" "));

            PdfPTable tabla = new PdfPTable(columnas.length);
            tabla.setWidthPercentage(100);

            Font encabezadoFont = FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD, 9, Color.WHITE
            );

            for (String nombreColumna : columnas) {

                PdfPCell celda = new PdfPCell(new Phrase(nombreColumna, encabezadoFont));
                celda.setBackgroundColor(Color.DARK_GRAY);
                celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabla.addCell(celda);

            }

            for (Venta venta : ventas) {

                List<DetalleVenta> detalles = venta.getDetalles();

                if (detalles == null || detalles.isEmpty()) {
                    continue;
                }

                for (DetalleVenta detalle : detalles) {

                    tabla.addCell(String.valueOf(venta.getIdVenta()));
                    tabla.addCell(venta.getFecha() != null ? venta.getFecha().format(FORMATO_FECHA) : "");
                    tabla.addCell(nombreCompletoCliente(venta));
                    tabla.addCell(nombreCompletoEmpleado(venta));
                    tabla.addCell(detalle.getProducto() != null ? detalle.getProducto().getNombre() : "");
                    tabla.addCell(String.valueOf(detalle.getCantidad()));
                    tabla.addCell(String.valueOf(detalle.getPrecioUnitario()));
                    tabla.addCell(String.valueOf(detalle.getSubtotal()));
                    tabla.addCell(String.valueOf(venta.getDescuento()));
                    tabla.addCell(String.valueOf(detalle.getSubtotal() * (1 - venta.getDescuento() / 100)));
                    tabla.addCell(venta.getMedioPago() != null ? venta.getMedioPago() : "");
                    tabla.addCell(venta.getCanal() != null ? venta.getCanal() : "");

                }

            }

            document.add(tabla);

            document.add(new Paragraph(" "));
            document.add(new Paragraph(
                    "Documento generado por NovaTech Solutions",
                    FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10)
            ));

        } catch (DocumentException | IOException e) {

            throw new RuntimeException("Error al generar el PDF.", e);

        } finally {

            if (document.isOpen()) {
                document.close();
            }

        }

    }

    private static String nombreCompletoCliente(Venta venta) {

        if (venta.getCliente() == null) {
            return "";
        }

        return venta.getCliente().getNombre() + " " + venta.getCliente().getApellido();

    }

    private static String nombreCompletoEmpleado(Venta venta) {

        if (venta.getEmpleado() == null) {
            return "";
        }

        return venta.getEmpleado().getNombre() + " " + venta.getEmpleado().getApellido();

    }

}
