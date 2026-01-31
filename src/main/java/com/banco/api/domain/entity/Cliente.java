package com.banco.api.domain.entity;

import com.banco.api.domain.enums.EstadoCliente;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "clientes")
@PrimaryKeyJoinColumn(name = "persona_id")
/**
 * Entidad Cliente
 * Hereda de Persona, comparte el mismo PK: persona_id
 * Guarda datos propios de cliente como contraseña y estado
 */
public class Cliente extends Persona {
	/**
	 * Nota de seguridad: 
	 * En un sistema real no se guarda en texto plano, se debe guardar como hash(BCrypt, Argon2, etc)
	 */
	@NotBlank(message = "La contraseña es obligatoria")
    @Size(max = 255, message = "La contraseña no debe exceder 255 caracteres")
    @Column(name = "contrasena", length = 255, nullable = false)
	private String contrasena;
	
	@NotNull(message = "El estado es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 20, nullable = false)
    private EstadoCliente estado;
	
	//Constructor vacio, esto por que JPA lo requiere
	public Cliente() {}
	
	//Getters/Setters (explicitos para evitar dependencia a Lombok)
	public String getContrasena() { return contrasena; }
	public void setContrasena(String contrasena) { this.contrasena = contrasena; }
	
	public EstadoCliente getEstado() { return estado; }
	public void setEstado(EstadoCliente estado) { this.estado = estado; }
}
