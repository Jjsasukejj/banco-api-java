package com.banco.api.service;

import java.util.List;

import com.banco.api.domain.entity.Cliente;
import com.banco.api.dto.request.ActualizarClienteRequest;
/**
 * Servicio de Cliente, define las operaciones de negocio relacionadas con clientes.
 */
public interface ClienteService {
	/**
	 * Interfaz para crear clientes
	 * @param cliente
	 * @return
	 */
	Cliente crearCliente(Cliente cliente);
	/**
	 * Intefaz para busqueda de cliente por Id
	 * @param id
	 * @return
	 */
	Cliente obtenerClientePorId(Long id);
	/**
	 * Intefaz para listar clientes
	 * @return
	 */
	List<Cliente> listarClientes();
	/**
	 * Intefaz para cambiar de estado al cliente de Activo a Inactivo
	 * @param id
	 */
	void inactivarCliente(Long id);
	/**
	 * Intefaz para actualizar un cliente
	 * @param id
	 * @param request
	 * @return
	 */
	Cliente actualizarCliente(Long id, ActualizarClienteRequest request);
}
