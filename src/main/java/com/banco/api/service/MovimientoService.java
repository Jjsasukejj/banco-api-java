package com.banco.api.service;

import com.banco.api.domain.entity.Movimiento;
import com.banco.api.dto.response.ReporteMovimientoResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Servicio de Movimiento, define las operaciones de negocio relacionadas con movimientos.
 */
public interface MovimientoService {
	/**
	 * Interfaz para realizar un deposito a la cuenta
	 * @param numeroCuenta
	 * @param monto
	 * @return
	 */
	Movimiento realizarDeposito(String numeroCuenta, BigDecimal monto);
	/**
	 * Interfaz para realizar un retiro a la cuenta
	 * @param numeroCuenta
	 * @param monto
	 * @return
	 */
	Movimiento realizarRetiro(String numeroCuenta, BigDecimal monto);
	/**
	 * Interfaz para realizar un reporte
	 * @param clienteId
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	List<ReporteMovimientoResponse> reporteMovimientosPorCliente(Long clienteId, LocalDate fechaInicio, LocalDate fechaFin);
}
