package com.banco.api.exception;
/**
 * Excepciones para recursos no encontrados.
 * Por ejemplo:
 * - Cliente no existe
 * - Cuenta no encontrada
 */
public class NotFoundException extends RuntimeException {
	/**
     * Identificador de version para serializacion.
     * Se define para evitar warnings y problemas de compatibilidad en serializacion.
     */
	private static final long serialVersionUID = 1l;
	
	public NotFoundException(String message) {
		super(message);
	}
}
