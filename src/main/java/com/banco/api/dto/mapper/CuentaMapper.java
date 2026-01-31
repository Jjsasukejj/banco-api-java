package com.banco.api.dto.mapper;

import com.banco.api.domain.entity.Cuenta;
import com.banco.api.dto.response.CuentaResponse;
/**
 * Mapper de Cuenta
 * Centraliza la conversion de entidad a DTO.
 */
public class CuentaMapper {
	public static CuentaResponse toResponse(Cuenta cuenta) {
		CuentaResponse response = new CuentaResponse();
        response.setId(cuenta.getId());
        response.setNumeroCuenta(cuenta.getNumeroCuenta());
        response.setTipoCuenta(cuenta.getTipoCuenta());
        response.setSaldo(cuenta.getSaldo());
        response.setEstado(cuenta.getEstado());
        response.setClienteId(cuenta.getCliente().getId());

        return response;
	}
}
