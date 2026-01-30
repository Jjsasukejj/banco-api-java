package com.banco.api.exception;

import java.time.OffsetDateTime;
/**
 * Estructura estandar para los errores de la API, lo utilizaremos en el GlobalExceptionHandler para devolver
 * respuestas consistentes al frontend.
 */
public record ApiError(
		OffsetDateTime timestamp,
		int status,
		String error,
		String message,
		String path) { }
