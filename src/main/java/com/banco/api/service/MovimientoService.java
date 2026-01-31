package com.banco.api.service;

import com.banco.api.domain.entity.Movimiento;
import java.math.BigDecimal;

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
}
