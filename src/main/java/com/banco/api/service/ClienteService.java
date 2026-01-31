package com.banco.api.service;

import com.banco.api.domain.entity.Cliente;
import java.util.List;
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
}
