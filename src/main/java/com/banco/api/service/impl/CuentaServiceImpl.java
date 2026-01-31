package com.banco.api.service.impl;

import com.banco.api.domain.entity.Cliente;
import com.banco.api.domain.entity.Cuenta;
import com.banco.api.domain.enums.EstadoCuenta;
import com.banco.api.exception.BusinessException;
import com.banco.api.exception.NotFoundException;
import com.banco.api.repository.ClienteRepository;
import com.banco.api.repository.CuentaRepository;
import com.banco.api.service.CuentaService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
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
	public Cuenta crearCuenta(Long clienteId, Cuenta cuenta) {
		
		Cliente cliente = clienteRepository.findById(clienteId)
				.orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
		
		//Regla: el cliente debe estar activo
		if (cliente.getEstado() == null) {
			throw new BusinessException("El cliente no esta activo");
		}
		
		//Regla: toda cuenta nueva inicia con saldo 0 y estado ACTIVA
		cuenta.setSaldo(BigDecimal.ZERO);
		cuenta.setEstado(EstadoCuenta.ACTIVA);
		cuenta.setCliente(cliente);
		
		return cuentaRepository.save(cuenta);
	}

	@Override
	public Cuenta obtenerCuentaPorNumero(String numeroCuenta) {
		return cuentaRepository.findByNumeroCuenta(numeroCuenta)
				.orElseThrow(() -> new NotFoundException("Cuenta no encontrada"));
	}

}
