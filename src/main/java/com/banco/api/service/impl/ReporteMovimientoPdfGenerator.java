package com.banco.api.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.banco.api.dto.response.ReporteMovimientoResponse;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class ReporteMovimientoPdfGenerator {
	/**
	 * Genera un PDF del reporte y lo retorna en Base64
	 */
	public static byte[] generarPdf(List<ReporteMovimientoResponse> movimientos) {
		
		Document document = new Document(PageSize.A4);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		try {
			PdfWriter.getInstance(document, outputStream);
            document.open();
            
            Font tituloFont = new Font(Font.HELVETICA, 14, Font.BOLD);
            Paragraph titulo = new Paragraph("Estado de Cuenta - Reporte de Movimientos", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            document.add(titulo);
            
            PdfPTable table = new PdfPTable(8);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{2, 2, 2, 2, 2, 2, 2, 2});
            
            agregarHeader(table);
            
            for (ReporteMovimientoResponse r : movimientos) {
                table.addCell(r.getFecha().toString());
                table.addCell(r.getCliente());
                table.addCell(r.getNumeroCuenta());
                table.addCell(r.getTipoCuenta().name());
                table.addCell(r.getSaldoInicial().toString());
                table.addCell(r.getEstado().name());
                table.addCell(r.getMovimiento().toString());
                table.addCell(r.getSaldoDisponible().toString());
            }
            
            document.add(table);
		} catch (Exception e) {
            throw new RuntimeException("Error generando PDF del reporte", e);
        } finally {
            document.close();
        }
		
		return outputStream.toByteArray();
	}
	
	private static void agregarHeader(PdfPTable table) {
		Font headerFont = new Font(Font.HELVETICA, 10, Font.BOLD);
		
		table.addCell(new PdfPCell(new Phrase("Fecha", headerFont)));
		table.addCell(new PdfPCell(new Phrase("Cliente", headerFont)));
		table.addCell(new PdfPCell(new Phrase("Cuenta", headerFont)));
		table.addCell(new PdfPCell(new Phrase("Tipo", headerFont)));
		table.addCell(new PdfPCell(new Phrase("Saldo Inicial", headerFont)));
		table.addCell(new PdfPCell(new Phrase("Estado", headerFont)));
		table.addCell(new PdfPCell(new Phrase("Movimiento", headerFont)));
		table.addCell(new PdfPCell(new Phrase("Saldo Disponible", headerFont)));
	}
}
