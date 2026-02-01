package com.banco.api.dto.request;

import com.banco.api.domain.enums.EstadoCliente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO de entrada para actualizar un cliente, define el contrato que consume el frontend
 */
public class ActualizarClienteRequest {
	@NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    private String nombre;

    @NotBlank(message = "La direccion es obligatoria")
    @Size(max = 200)
    private String direccion;

    @NotBlank(message = "El telefono es obligatorio")
    @Size(max = 20)
    private String telefono;

    @NotNull(message = "El estado es obligatorio")
    private EstadoCliente estado;

    // Getters / Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public EstadoCliente getEstado() { return estado; }
    public void setEstado(EstadoCliente estado) { this.estado = estado; }
}
