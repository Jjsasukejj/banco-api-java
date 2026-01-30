package com.banco.api.exception;
/**
 * Excepciones para reglas del negocio.
 * Por ejemplo:
 * - Saldo no disponible
 * - Cupo diario excedido
 * - Cuenta inactiva
 */
public class BusinessException extends RuntimeException {
	/**
     * Identificador de version para serializacion.
     * Se define para evitar warnings y problemas de compatibilidad en serializacion.
     */
	private static final long serialVersionUID = 1l;
	public BusinessException(String message) {
		super(message);
	}
}
