package com.mycompany.nequi;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Factura {

    // ------------------- FACTURA DE UN SOLO MOVIMIENTO -------------------
    public static void generarFacturaMovimiento(UsuarioNequi usuario, MovimientosNequi movimiento) {
        try {
            // Fecha actual para el archivo
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
            String fechaArchivo = LocalDateTime.now().format(dtf);

            // Nombre dinámico del PDF
            String nombreArchivo = "Factura_" + movimiento.getClass().getSimpleName() + "_" + fechaArchivo + ".pdf";

            // Documento PDF
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(nombreArchivo));
            doc.open();

            // Título
            Paragraph titulo = new Paragraph("FACTURA NEQUI", new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD));
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(titulo);
            doc.add(new Paragraph("────────────────────────────\n"));

            // Info del cliente y banco
            doc.add(new Paragraph("Banco: " + usuario.getCuenta().getBanco().getNombre()));
            doc.add(new Paragraph("Cliente: " + usuario.getNombre()));
            doc.add(new Paragraph("Cédula: " + usuario.getCedula()));
            doc.add(new Paragraph("Cuenta: " + usuario.getCuenta().getNumeroCuenta()));
            doc.add(new Paragraph("\n"));

            // Tabla del movimiento
            PdfPTable tabla = new PdfPTable(3);
            tabla.setWidthPercentage(100);

            PdfPCell c1 = new PdfPCell(new Phrase("Movimiento"));
            PdfPCell c2 = new PdfPCell(new Phrase("Monto"));
            PdfPCell c3 = new PdfPCell(new Phrase("Fecha"));

            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c2.setHorizontalAlignment(Element.ALIGN_CENTER);
            c3.setHorizontalAlignment(Element.ALIGN_CENTER);

            tabla.addCell(c1);
            tabla.addCell(c2);
            tabla.addCell(c3);

            tabla.addCell(movimiento.getClass().getSimpleName());
            tabla.addCell("$" + String.format("%,.2f", movimiento.getMonto()));
            tabla.addCell(movimiento.getFecha());

            doc.add(tabla);

            // Saldo final
            doc.add(new Paragraph("\nSaldo disponible: $" + String.format("%,.2f", usuario.getCuenta().getSaldo())));
            doc.add(new Paragraph("────────────────────────────\n"));
            doc.add(new Paragraph("   Gracias por usar Nequi", new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC)));

            doc.close();
            System.out.println("Factura generada: " + nombreArchivo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ------------------- FACTURA RESUMEN DE TODOS LOS MOVIMIENTOS -------------------
    public static void generarFacturaTotal(UsuarioNequi usuario) {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
            String fechaArchivo = LocalDateTime.now().format(dtf);

            String nombreArchivo = "FacturaTotal_" + usuario.getNombre() + "_" + fechaArchivo + ".pdf";

            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(nombreArchivo));
            doc.open();

            // Título
            Paragraph titulo = new Paragraph("FACTURA TOTAL DE MOVIMIENTOS", new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD));
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(titulo);
            doc.add(new Paragraph("=====================================\n"));

            // Info general
            doc.add(new Paragraph("Banco: " + usuario.getCuenta().getBanco().getNombre()));
            doc.add(new Paragraph("Cliente: " + usuario.getNombre()));
            doc.add(new Paragraph("Cédula: " + usuario.getCedula()));
            doc.add(new Paragraph("Cuenta: " + usuario.getCuenta().getNumeroCuenta()));
            doc.add(new Paragraph("\n"));

            // Tabla con todos los movimientos
            PdfPTable tabla = new PdfPTable(3);
            tabla.setWidthPercentage(100);

            PdfPCell c1 = new PdfPCell(new Phrase("Movimiento"));
            PdfPCell c2 = new PdfPCell(new Phrase("Monto"));
            PdfPCell c3 = new PdfPCell(new Phrase("Fecha"));

            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c2.setHorizontalAlignment(Element.ALIGN_CENTER);
            c3.setHorizontalAlignment(Element.ALIGN_CENTER);

            tabla.addCell(c1);
            tabla.addCell(c2);
            tabla.addCell(c3);

            List<MovimientosNequi> movimientos = usuario.getCuenta().getMovimientos();
            for (MovimientosNequi mov : movimientos) {
                tabla.addCell(mov.getClass().getSimpleName());
                tabla.addCell("$" + String.format("%,.2f", mov.getMonto()));
                tabla.addCell(mov.getFecha());
            }

            doc.add(tabla);

            // Saldo final
            doc.add(new Paragraph("\nSaldo final disponible: $" + String.format("%,.2f", usuario.getCuenta().getSaldo())));
            doc.add(new Paragraph("=====================================\n"));
            doc.add(new Paragraph("   Gracias por usar Nequi", new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC)));

            doc.close();
            System.out.println("Factura total generada: " + nombreArchivo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
