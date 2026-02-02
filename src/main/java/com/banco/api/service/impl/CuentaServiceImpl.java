package com.banco.api.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banco.api.domain.entity.Cliente;
import com.banco.api.domain.entity.Cuenta;
import com.banco.api.domain.enums.EstadoCuenta;
import com.banco.api.dto.request.ActualizarCuentaRequest;
import com.banco.api.exception.BusinessException;
import com.banco.api.exception.NotFoundException;
import com.banco.api.repository.ClienteRepository;
import com.banco.api.repository.CuentaRepository;
import com.banco.api.service.CuentaService;

/**
 * implementacion del servicio de cuenta, se aplica las reglas de negocio relacionadas a la cuenta.
 */
@Service
public class CuentaServiceImpl implements CuentaService {
	
	private final CuentaRepository cuentaRepository;
    private final ClienteRepository clienteRepository;

    public CuentaServiceImpl(CuentaRepository cuentaRepository, ClienteRepository clienteRepository) {
        this.cuentaRepository = cuentaRepository;
        this.clienteRepository = clienteRepository;
    }

	@Override
	@Transactional
	public Cuenta crearCuenta(Long clienteId, Cuenta cuenta) {
		
		Cliente cliente = clienteRepository.findById(clienteId)
				.orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
		
		//Regla: el cliente debe estar activo
		if (cliente.getEstado() == null) {
			throw new BusinessException("El cliente no esta activo");
		}
		
		//Regla: toda cuenta nueva inicia con saldo 0 y estado ACTIVA
		cuenta.setSaldo(cuenta.getSaldo());
		cuenta.setEstado(EstadoCuenta.ACTIVA);
		cuenta.setCliente(cliente);
		
		return cuentaRepository.save(cuenta);
	}

	@Override
	public Cuenta obtenerCuentaPorNumero(String numeroCuenta) {
		return cuentaRepository.findByNumeroCuentaFetchCliente(numeroCuenta)
				.orElseThrow(() -> new NotFoundException("Cuenta no encontrada"));
	}

	@Override
	public List<Cuenta> listarCuentasPorCliente(Long clienteId) {
		return cuentaRepository.findByClienteIdFetchCliente(clienteId);
	}

	@Override
	@Transactional
	public Cuenta actualizarCuenta(String numeroCuenta, ActualizarCuentaRequest request) {
		
		Cuenta cuenta = obtenerCuentaPorNumero(numeroCuenta);
		
		cuenta.setTipoCuenta(request.getTipoCuenta());
        cuenta.setEstado(request.getEstado());
        
		return cuentaRepository.save(cuenta);
	}

	@Override
	@Transactional
	public void eliminarCuenta(String numeroCuenta) {
		
		Cuenta cuenta = obtenerCuentaPorNumero(numeroCuenta);
		
		cuenta.setEstado(EstadoCuenta.INACTIVA);
        cuentaRepository.save(cuenta);
		
	}

	@Override
	public List<Cuenta> listarCuentas() {
		return cuentaRepository.findAllWithCliente();
	}

}
