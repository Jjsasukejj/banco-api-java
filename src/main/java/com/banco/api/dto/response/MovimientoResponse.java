package com.banco.api.dto.response;

import com.banco.api.domain.enums.TipoMovimiento;
import java.math.BigDecimal;
import java.time.LocalDateTime;
/**
 * DTO de salida para Movimiento, define exactamente que informacion de la cuenta se expone al frontend
 */
public class MovimientoResponse {
	private Long id;
    private String numeroCuenta;
    private TipoMovimiento tipoMovimiento;
    private BigDecimal monto;
    private BigDecimal saldoPosterior;
    private LocalDateTime fecha;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }

    public TipoMovimiento getTipoMovimiento() { return tipoMovimiento; }
    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) { this.tipoMovimiento = tipoMovimiento; }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public BigDecimal getSaldoPosterior() { return saldoPosterior; }
    public void setSaldoPosterior(BigDecimal saldoPosterior) { this.saldoPosterior = saldoPosterior; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
