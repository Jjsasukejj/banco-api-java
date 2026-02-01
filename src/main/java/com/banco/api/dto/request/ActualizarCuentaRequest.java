package com.banco.api.dto.request;

import com.banco.api.domain.enums.EstadoCuenta;
import com.banco.api.domain.enums.TipoCuenta;

import jakarta.validation.constraints.NotNull;

/**
 * DTO de entrada para actualizar una cuenta, define el contrato que consume el frontend
 */
public class ActualizarCuentaRequest {
	@NotNull(message = "El tipo de cuenta es obligatorio")
    private TipoCuenta tipoCuenta;

    @NotNull(message = "El estado de la cuenta es obligatorio")
    private EstadoCuenta estado;

    public TipoCuenta getTipoCuenta() { return tipoCuenta; }
    public void setTipoCuenta(TipoCuenta tipoCuenta) { this.tipoCuenta = tipoCuenta; }

    public EstadoCuenta getEstado() { return estado; }
    public void setEstado(EstadoCuenta estado) { this.estado = estado; }
}
