package com.novatech.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ExcelUtil {

    private ExcelUtil() {
    }

    public static <T> void exportar(List<T> datos, String nombreHoja, String ruta) {

        if (datos == null || datos.isEmpty()) {
            throw new IllegalArgumentException("No hay datos para exportar.");
        }

        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet(nombreHoja);

            CellStyle estiloTitulo = workbook.createCellStyle();

            Font fuenteTitulo = workbook.createFont();
            fuenteTitulo.setBold(true);
            fuenteTitulo.setColor(IndexedColors.WHITE.getIndex());

            estiloTitulo.setFont(fuenteTitulo);
            estiloTitulo.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
            estiloTitulo.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Row filaTitulos = sheet.createRow(0);

            Field[] campos = datos.get(0).getClass().getDeclaredFields();

            for (int i = 0; i < campos.length; i++) {

                Cell celda = filaTitulos.createCell(i);

                celda.setCellValue(campos[i].getName());

                celda.setCellStyle(estiloTitulo);

            }

            int numeroFila = 1;

            for (T objeto : datos) {

                Row fila = sheet.createRow(numeroFila++);

                for (int i = 0; i < campos.length; i++) {

                    campos[i].setAccessible(true);

                    Cell celda = fila.createCell(i);

                    Object valor = campos[i].get(objeto);

                    if (valor == null) {

                        celda.setCellValue("");

                    } else if (valor instanceof String) {

                        celda.setCellValue((String) valor);

                    } else if (valor instanceof Integer) {

                        celda.setCellValue((Integer) valor);

                    } else if (valor instanceof Double) {

                        celda.setCellValue((Double) valor);

                    } else if (valor instanceof Float) {

                        celda.setCellValue((Float) valor);

                    } else if (valor instanceof Long) {

                        celda.setCellValue((Long) valor);

                    } else if (valor instanceof Boolean) {

                        celda.setCellValue((Boolean) valor);

                    } else if (valor instanceof LocalDate) {

                        celda.setCellValue(valor.toString());

                    } else if (valor instanceof LocalDateTime) {

                        celda.setCellValue(valor.toString());

                    } else {

                        celda.setCellValue(valor.toString());

                    }

                }

            }

            for (int i = 0; i < campos.length; i++) {

                sheet.autoSizeColumn(i);

            }

            try (FileOutputStream fos = new FileOutputStream(ruta)) {

                workbook.write(fos);

            }

        } catch (IOException e) {

            throw new RuntimeException("Error al generar el archivo Excel.", e);

        } catch (IllegalAccessException e) {

            throw new RuntimeException("No fue posible acceder a los datos.", e);

        }

    }

}
