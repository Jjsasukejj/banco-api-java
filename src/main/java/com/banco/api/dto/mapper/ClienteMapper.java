package com.banco.api.dto.mapper;

import com.banco.api.domain.entity.Cliente;
import com.banco.api.dto.response.ClienteResponse;
/**
 * Mapper de Cliente
 * Centraliza la conversion de entidad a DTO.
 */
public class ClienteMapper {
	public static ClienteResponse toResponse(Cliente cliente) {
		ClienteResponse response = new ClienteResponse();
        response.setId(cliente.getId());
        response.setNombre(cliente.getNombre());
        response.setGenero(cliente.getGenero());
        response.setEdad(cliente.getEdad());
        response.setIdentificacion(cliente.getIdentificacion());
        response.setDireccion(cliente.getDireccion());
        response.setTelefono(cliente.getTelefono());
        response.setEstado(cliente.getEstado());

        return response;
	}
}
