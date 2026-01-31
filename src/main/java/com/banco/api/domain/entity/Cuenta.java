package com.banco.api.domain.entity;

import java.math.BigDecimal;

import com.banco.api.domain.enums.EstadoCuenta;
import com.banco.api.domain.enums.TipoCuenta;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(
        name = "cuentas",
        indexes = {
                // Indice unico para numero de cuenta
                @Index(name = "ux_cuentas_numero", columnList = "numero_cuenta", unique = true)
        }
)
/**
 * Entidad Cuenta
 * Representa una cuenta bancaria de un cliente, contiene informacion financiera y de estado.
 */
public class Cuenta {
	// PK tecnica
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cuenta_id")
    private Long id;
	
	/**
     * Numero de cuenta visible al usuario, se marca como unico, pero no es la PK.
     */
	@NotBlank(message = "El numero de cuenta es obligatorio")
    @Column(name = "numero_cuenta", length = 30, nullable = false, unique = true)
    private String numeroCuenta;
	
	@NotNull(message = "El tipo de cuenta es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cuenta", length = 20, nullable = false)
    private TipoCuenta tipoCuenta;
	
	/**
     * Saldo actual de la cuenta, se usa BigDecimal para evitar errores de precisión.
     */
	@NotNull(message = "El saldo es obligatorio")
    @PositiveOrZero(message = "El saldo no puede ser negativo")
    @Column(name = "saldo", precision = 15, scale = 2, nullable = false)
    private BigDecimal saldo;
	
	@NotNull(message = "El estado de la cuenta es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 20, nullable = false)
    private EstadoCuenta estado;
	
	/**
     * Relacion con Cliente.
     * Muchas cuentas pertenecen a un cliente.
     * FetchType.LAZY para evitar cargas innecesarias.
     */
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false, foreignKey = @ForeignKey(name = "FK_cuentas_clientes"))
    private Cliente cliente;
	
	//Constructor vacio, esto por que JPA lo requiere
	public Cuenta() {}
	
	//Getters/Setters (explicitos para evitar dependencia a Lombok)
	public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }

    public TipoCuenta getTipoCuenta() { return tipoCuenta; }
    public void setTipoCuenta(TipoCuenta tipoCuenta) { this.tipoCuenta = tipoCuenta; }

    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }

    public EstadoCuenta getEstado() { return estado; }
    public void setEstado(EstadoCuenta estado) { this.estado = estado; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
}
