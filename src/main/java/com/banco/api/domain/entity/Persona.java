package com.banco.api.domain.entity;

import com.banco.api.domain.enums.Genero;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(
		name = "personas",
		indexes = {
				//Indice para busquedas frecuentes por identificacion
				@Index(name = "ix_personas_identificacion", columnList = "identificacion", unique = true)
		}
		)
@Inheritance(strategy = InheritanceType.JOINED)

/**
 * Entidad base Persona
 * Esta clase representa los datos comunes de una Persona, se usa JPA por que Cliente extiende Persona
 * JOINED crea la tabla persona y la tabla hija clientes se relaciona por PK
 */
public class Persona {
	//PK tecnica
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "perona_id")
	private Long id;
	
	@NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no debe exceder 100 caracteres")
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;
	
	@NotNull(message = "El genero es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "genero", length = 20, nullable = false)
    private Genero genero;
	
	@NotNull(message = "La edad es obligatoria")
    @Min(value = 0, message = "La edad no puede ser negativa")
    @Max(value = 150, message = "La edad no puede ser mayor a 150")
    @Column(name = "edad", nullable = false)
    private Integer edad;
	
	//Campo identificacion es un dato unico.
	@NotBlank(message = "La identificacion es obligatoria")
    @Size(max = 10, message = "La identificacion no debe exceder 10 caracteres")
    @Column(name = "identificacion", length = 20, nullable = false, unique = true)
    private String identificacion;
	
	@NotBlank(message = "La direccion es obligatoria")
    @Size(max = 200, message = "La direccion no debe exceder 200 caracteres")
    @Column(name = "direccion", length = 200, nullable = false)
    private String direccion;
	
	@NotBlank(message = "El telefono es obligatorio")
    @Size(max = 20, message = "El telefono no debe exceder 20 caracteres")
    @Column(name = "telefono", length = 20, nullable = false)
    private String telefono;
	
	//Constructor vacio, esto por que JPA lo requiere
	public Persona() {}
	
	//Getters/Setters (explicitos para evitar dependencia a Lombok)
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	
	public String getNombre() { return nombre; }
	public void setNombre(String nombre) { this.nombre = nombre; }
	
	public Genero getGenero() { return genero; }
	public void setGenero(Genero genero) { this.genero = genero; }
	
	public Integer getEdad() { return edad; }
	public void setEdad(Integer edad) { this.edad = edad; }
	
	public String getIdentificacion() { return identificacion; }
	public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }
	
	public String getDireccion() { return direccion; }
	public void setDireccion(String direccion) { this.direccion = direccion; }
	
	public String getTelefono() { return telefono; }
	public void setTelefono(String telefono) { this.telefono = telefono; }
}
