package com.banco.api.service.impl;

import com.banco.api.domain.entity.Cliente;
import com.banco.api.domain.enums.EstadoCliente;
import com.banco.api.exception.BusinessException;
import com.banco.api.exception.NotFoundException;
import com.banco.api.repository.ClienteRepository;
import com.banco.api.service.ClienteService;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 * implementacion del servicio de cliente, se aplica las reglas de negocio relacionadas al cliente
 */
@Service
public class ClienteServiceImpl implements ClienteService {
	private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

	@Override
	public Cliente crearCliente(Cliente cliente) {
		//Regla: No se puede registrar un clinete con identificacion duplicada
		clienteRepository.findByIdentificacion(cliente.getIdentificacion())
			.ifPresent(x -> {
				throw new BusinessException("Ya existe un cliente con esa identificacion");
			});
		
		//regla: todo cliente nuevo inicia ACTIVO
		cliente.setEstado(EstadoCliente.ACTIVO);
		return clienteRepository.save(cliente);
	}

	@Override
	public Cliente obtenerClientePorId(Long id) {
		return clienteRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
	}

	@Override
	public List<Cliente> listarClientes() {
		return clienteRepository.findAll();
	}

	@Override
	public void inactivarCliente(Long id) {
		Cliente cliente = obtenerClientePorId(id);
		cliente.setEstado(EstadoCliente.INACTIVO);
		clienteRepository.save(cliente);
		
	}
}
