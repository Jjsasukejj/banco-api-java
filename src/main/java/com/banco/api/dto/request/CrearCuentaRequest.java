package com.banco.api.dto.request;

import com.banco.api.domain.enums.TipoCuenta;
import jakarta.validation.constraints.NotNull;
/**
 * DTO de entrada para crear una cuenta, define el contrato que consume el frontend
 */
public class CrearCuentaRequest {
	@NotNull(message = "El tipo de cuenta es obligatorio")
    private TipoCuenta tipoCuenta;

    @NotNull(message = "El numero de cuenta es obligatorio")
    private String numeroCuenta;

    public TipoCuenta getTipoCuenta() { return tipoCuenta; }
    public void setTipoCuenta(TipoCuenta tipoCuenta) { this.tipoCuenta = tipoCuenta; }

    public String getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }
}
