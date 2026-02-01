package com.banco.api.dto.request;

import java.math.BigDecimal;

import com.banco.api.domain.enums.EstadoCuenta;
import com.banco.api.domain.enums.TipoCuenta;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
/**
 * DTO de entrada para crear una cuenta, define el contrato que consume el frontend
 */
public class CrearCuentaRequest {
	@NotNull(message = "El tipo de cuenta es obligatorio")
    private TipoCuenta tipoCuenta;

    @NotNull(message = "El numero de cuenta es obligatorio")
    private String numeroCuenta;
    
    @NotNull(message = "El saldo inicial es obligatorio")
    @PositiveOrZero(message = "El saldo inicial no puede ser negativo")
    private BigDecimal saldoInicial;
    
    @NotNull(message = "El tipo de cuenta es obligatorio")
    private EstadoCuenta estado;

    public TipoCuenta getTipoCuenta() { return tipoCuenta; }
    public void setTipoCuenta(TipoCuenta tipoCuenta) { this.tipoCuenta = tipoCuenta; }

    public String getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }
    
    public BigDecimal getSaldoInicial() { return saldoInicial; }
    public void setSaldoInicial(BigDecimal saldoInicial) { this.saldoInicial = saldoInicial; }
    
    public EstadoCuenta getEstado() { return estado; }
    public void setEstado(EstadoCuenta estado) { this.estado = estado; }
}
