package com.banco.api.service;

import com.banco.api.domain.entity.Cuenta;
/**
 * Servicio de Cuenta, define las operaciones de negocio relacionadas con cuentas.
 */
public interface CuentaService {
	/**
	 * Interfaz para crear cuentas
	 * @param clienteId
	 * @param cuenta
	 * @return
	 */
	Cuenta crearCuenta(Long clienteId, Cuenta cuenta);
	/**
	 * Interfaz para obtener una cuenta por Numero de Cuenta
	 * @param numeroCuenta
	 * @return
	 */
	Cuenta obtenerCuentaPorNumero(String numeroCuenta);
}
