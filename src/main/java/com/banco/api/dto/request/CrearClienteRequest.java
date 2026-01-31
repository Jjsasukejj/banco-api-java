package com.banco.api.dto.request;

import com.banco.api.domain.enums.Genero;
import jakarta.validation.constraints.*;
/**
 * DTO de entrada para crear un cliente, define el contrato que consume el frontend
 */
public class CrearClienteRequest {
	@NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "El genero es obligatorio")
    private Genero genero;

    @NotNull(message = "La edad es obligatoria")
    @Min(0)
    @Max(150)
    private Integer edad;

    @NotBlank(message = "La identificacion es obligatoria")
    private String identificacion;

    @NotBlank(message = "La direccion es obligatoria")
    private String direccion;

    @NotBlank(message = "El telefono es obligatorio")
    private String telefono;

    @NotBlank(message = "La contraseña es obligatoria")
    private String contrasena;

    // getters y setters
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

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}
