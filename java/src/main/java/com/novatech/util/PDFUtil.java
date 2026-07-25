package com.novatech.util;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PDFUtil {

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

            document.close();

        }

    }

}
