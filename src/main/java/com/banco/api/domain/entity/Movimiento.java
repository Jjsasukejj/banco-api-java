package com.banco.api.domain.entity;

import com.banco.api.domain.enums.TipoMovimiento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "movimientos",
        indexes = {
                // Indice para busqueda por cuenta y fecha (reportes, cupo diario)
                @Index(name = "ix_movimientos_cuenta_fecha", columnList = "cuenta_id, fecha")
        }
)
/**
 * Entidad Movimiento
 * Representa uns transaccion financiera sobre una cuenta, puede ser deposito o retiro
 */
public class Movimiento {
	// PK tecnica
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movimiento_id")
    private Long id;
	
	/**
     * Cuenta sobre la cual se realiza el movimiento.
     */
	@NotNull(message = "La cuenta es obligatoria")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_id", nullable = false)
    private Cuenta cuenta;
	
	@NotNull(message = "El tipo de movimiento es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", length = 20, nullable = false)
    private TipoMovimiento tipo;
	
	/**
     * Monto del movimiento.
     * Siempre positivo, el signo se interpreta segun el tipo (DEPOSITO / RETIRO).
     */
	@NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser mayor a cero")
    @Column(name = "monto", precision = 15, scale = 2, nullable = false)
    private BigDecimal monto;
	
	/**
     * Fecha y hora en que se realizó el movimiento.
     */
	@NotNull(message = "La fecha del movimiento es obligatoria")
    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;
	
	/**
     * Saldo de la cuenta despues de aplicar el movimiento, se guarda para auditoria y reportes.
     */
	@NotNull(message = "El saldo posterior es obligatorio")
    @Column(name = "saldo_posterior", precision = 15, scale = 2, nullable = false)
    private BigDecimal saldoPosterior;
	
	//Constructor vacio, esto por que JPA lo requiere
	public Movimiento() { }
	
	//Getters/Setters (explicitos para evitar dependencia a Lombok)
	public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Cuenta getCuenta() { return cuenta; }
    public void setCuenta(Cuenta cuenta) { this.cuenta = cuenta; }

    public TipoMovimiento getTipo() { return tipo; }
    public void setTipo(TipoMovimiento tipo) { this.tipo = tipo; }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public BigDecimal getSaldoPosterior() { return saldoPosterior; }
    public void setSaldoPosterior(BigDecimal saldoPosterior) { this.saldoPosterior = saldoPosterior; }
}
