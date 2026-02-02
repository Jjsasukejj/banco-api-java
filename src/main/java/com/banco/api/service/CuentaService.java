package com.banco.api.service;

import java.util.List;

import com.banco.api.domain.entity.Cuenta;
import com.banco.api.dto.request.ActualizarCuentaRequest;
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
	/**
	 * Interfaz para listar cuentas por cliente
	 * @param clienteId
	 * @return
	 */
	List<Cuenta> listarCuentasPorCliente(Long clienteId);
	/**
	 * Interfaz para actualizar cuenta
	 * @param numeroCuenta
	 * @param request
	 * @return
	 */
    Cuenta actualizarCuenta(String numeroCuenta, ActualizarCuentaRequest request);
    /**
     * Interfaz para borrar una cuenta (borrado logico, lo que cambiamos es el estado de activo a inactivo)
     * @param numeroCuenta
     */
    void eliminarCuenta(String numeroCuenta);
    /**
     * Interfaz para listar todas las cuentas
     * @param numeroCuenta
     */
    List<Cuenta> listarCuentas();
}
