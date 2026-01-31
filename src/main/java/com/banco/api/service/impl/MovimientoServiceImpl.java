package com.banco.api.service.impl;

import com.banco.api.domain.entity.Cuenta;
import com.banco.api.domain.entity.Movimiento;
import com.banco.api.domain.enums.EstadoCuenta;
import com.banco.api.domain.enums.TipoMovimiento;
import com.banco.api.exception.BusinessException;
import com.banco.api.repository.CuentaRepository;
import com.banco.api.repository.MovimientoRepository;
import com.banco.api.service.MovimientoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * implementacion del servicio de movimiento, se aplica las reglas de negocio relacionadas al movimiento bancario.
 */
@Service
public class MovimientoServiceImpl implements MovimientoService{
	
	private static final BigDecimal CUPO_DIARIO_RETIRO = new BigDecimal("1000");

    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;

    public MovimientoServiceImpl(CuentaRepository cuentaRepository, MovimientoRepository movimientoRepository) {
        this.cuentaRepository = cuentaRepository;
        this.movimientoRepository = movimientoRepository;
    }

    //Usamos @Transactional ya que si algo falla no se actualiza saldo y no registra movimiento
	@Override
	@Transactional 
	public Movimiento realizarDeposito(String numeroCuenta, BigDecimal monto) {
		
		Cuenta cuenta = obtenerCuentaActiva(numeroCuenta);
		
		BigDecimal nuevoSaldo = cuenta.getSaldo().add(monto);
		
		cuenta.setSaldo(nuevoSaldo);
		cuentaRepository.save(cuenta);
		
		Movimiento movimiento = crearMovimiento(cuenta, TipoMovimiento.DEPOSITO, monto, nuevoSaldo);
		
		return movimientoRepository.save(movimiento);
	}

	//Usamos @Transactional ya que si algo falla no se actualiza saldo y no registra movimiento
	@Override
	@Transactional
	public Movimiento realizarRetiro(String numeroCuenta, BigDecimal monto) {
		Cuenta cuenta = obtenerCuentaActiva(numeroCuenta);
		
		//Regla: no permitir saldo negativo
        if (cuenta.getSaldo().compareTo(monto) < 0) {
            throw new BusinessException("Saldo insuficiente");
        }
        
        //Regla: validar cupo diario de retiro
        LocalDate hoy = LocalDate.now();
        LocalDateTime inicioDia = hoy.atStartOfDay();
        LocalDateTime finDia = hoy.atTime(23, 59, 59);
        
        BigDecimal totalRetirosHoy = movimientoRepository.sumMontoByCuentaAndTipoAndFechaBetween(cuenta.getId(), TipoMovimiento.RETIRO, inicioDia, finDia);
        
        if (totalRetirosHoy.add(monto).compareTo(CUPO_DIARIO_RETIRO) > 0) {
            throw new BusinessException("Cupo diario de retiro excedido");
        }

        BigDecimal nuevoSaldo = cuenta.getSaldo().subtract(monto);
        
        cuenta.setSaldo(nuevoSaldo);
        cuentaRepository.save(cuenta);
        
        Movimiento movimiento = crearMovimiento(cuenta, TipoMovimiento.RETIRO, monto, nuevoSaldo);
        
		return movimientoRepository.save(movimiento);
	}
	
	/**
	 * Obtiene un cuenta activa o lanza exceptiones
	 * @param numeroCuenta
	 * @return
	 */
	private Cuenta obtenerCuentaActiva(String numeroCuenta) {
		Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta)
				.orElseThrow(() -> new BusinessException("Cuenta no encontrada"));
		
		if (cuenta.getEstado() != EstadoCuenta.ACTIVA) {
			throw new BusinessException("La cuenta no esta activa");
		}
		
		return cuenta;
	}
	/**
	 * Crea una instancia de Movimiento con datos comunes
	 * @param cuenta
	 * @param tipo
	 * @param monto
	 * @param saldoPosterior
	 * @return
	 */
	private Movimiento crearMovimiento(
			Cuenta cuenta,
			TipoMovimiento tipo,
			BigDecimal monto,
			BigDecimal saldoPosterior) {
		Movimiento movimiento = new Movimiento();
		movimiento.setTipo(tipo);
		movimiento.setMonto(monto);
		movimiento.setFecha(LocalDateTime.now());
		movimiento.setCuenta(cuenta);
		movimiento.setSaldoPosterior(saldoPosterior);
		
		return movimiento;
	}
	
}
