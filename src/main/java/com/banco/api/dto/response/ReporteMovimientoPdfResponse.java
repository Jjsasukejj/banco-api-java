package com.banco.api.dto.response;
/**
 * DTO de respuesta para exportacion del reporte en Pdf
 */
public class ReporteMovimientoPdfResponse {
	private String archivoBase64;
	private String nombreArchivo;
	
	public ReporteMovimientoPdfResponse(String archivoBase64, String nombreArchivo) {
		this.archivoBase64 = archivoBase64;
		this.nombreArchivo = nombreArchivo;
	}
	
	public String getArchivoBase64() {
		return archivoBase64;
	}
	
	public String getNombreArchivo() {
		return nombreArchivo;
	}
}
