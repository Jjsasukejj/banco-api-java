package com.banco.api.dto.mapper;

import com.banco.api.domain.entity.Movimiento;
import com.banco.api.dto.response.MovimientoResponse;
/**
 * Mapper de Movimiento
 * Centraliza la conversion de entidad a DTO.
 */
public class MovimientoMapper {
	public static MovimientoResponse toResponse(Movimiento movimiento) {
		MovimientoResponse response = new MovimientoResponse();
        response.setId(movimiento.getId());
        response.setNumeroCuenta(movimiento.getCuenta().getNumeroCuenta());
        response.setTipoMovimiento(movimiento.getTipo());
        response.setMonto(movimiento.getMonto());
        response.setSaldoPosterior(movimiento.getSaldoPosterior());
        response.setFecha(movimiento.getFecha());

        return response;
	}
}
