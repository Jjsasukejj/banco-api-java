package com.banco.api.exception.handler;

import com.banco.api.exception.ApiError;
import com.banco.api.exception.BusinessException;
import com.banco.api.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.OffsetDateTime;
import java.util.stream.Collectors;
/**
 * Manejador global de excepciones de la API.
 *
 * Centraliza el manejo de errores para evitar try/catch en los controllers.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
	/**
     * Maneja errores cuando un recurso no existe.
     */
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ApiError> handleNotFound(NotFoundException ex, HttpServletRequest req) {
		return build(HttpStatus.NOT_FOUND, ex.getMessage(), req.getRequestURI());
	}
	/**
     * Maneja errores de reglas de negocio.
     */
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ApiError> handleNotFound(BusinessException ex, HttpServletRequest req) {
		return build(HttpStatus.BAD_REQUEST, ex.getMessage(), req.getRequestURI());
	}
	/**
     * Maneja errores de validacion de DTOs.
     */
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        String msg = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(" | "));
        return build(HttpStatus.BAD_REQUEST, msg, req.getRequestURI());
    }
	/**
     * Manejo generico para errores no controlados.
     */
	@ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest req) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor", req.getRequestURI());
    }
	/**
     * Construye la respuesta estandar de error.
     */
    private ResponseEntity<ApiError> build(HttpStatus status, String message, String path) {
        ApiError body = new ApiError(
                OffsetDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                path
        );
        return ResponseEntity.status(status).body(body);
    }
}
