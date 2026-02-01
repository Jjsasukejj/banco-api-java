package com.banco.api.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banco.api.domain.entity.Cuenta;
import com.banco.api.domain.entity.Movimiento;
import com.banco.api.domain.enums.EstadoCuenta;
import com.banco.api.domain.enums.TipoMovimiento;
import com.banco.api.dto.response.ReporteMovimientoResponse;
import com.banco.api.exception.BusinessException;
import com.banco.api.repository.CuentaRepository;
import com.banco.api.repository.MovimientoRepository;
import com.banco.api.service.MovimientoService;

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
            throw new BusinessException("Saldo no disponible");
        }
        
        //Regla: validar cupo diario de retiro
        LocalDate hoy = LocalDate.now();
        LocalDateTime inicioDia = hoy.atStartOfDay();
        LocalDateTime finDia = hoy.atTime(23, 59, 59);
        
        BigDecimal totalRetirosHoy = movimientoRepository.sumMontoByCuentaAndTipoAndFechaBetween(cuenta.getId(), TipoMovimiento.RETIRO, inicioDia, finDia);
        
        if (totalRetirosHoy.add(monto).compareTo(CUPO_DIARIO_RETIRO) > 0) {
            throw new BusinessException("Cupo diario excedido");
        }

        BigDecimal nuevoSaldo = cuenta.getSaldo().subtract(monto);
        
        cuenta.setSaldo(nuevoSaldo);
        cuentaRepository.save(cuenta);
        
        Movimiento movimiento = crearMovimiento(cuenta, TipoMovimiento.RETIRO, monto, nuevoSaldo);
        
		return movimientoRepository.save(movimiento);
	}
	
	/**
	 * Genera el reporte de movimientos por cliente y rango de fechas
	 * @param clienteId
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	public List<ReporteMovimientoResponse> reporteMovimientosPorCliente(Long clienteId, LocalDate fechaInicio, LocalDate fechaFin) {
		// Convertimos el rango de fechas a rango de LocalDateTime para incluir todo el dia (desde 00:00 hasta 23:59:59)
	    LocalDateTime desde = fechaInicio.atStartOfDay();
	    LocalDateTime hasta = fechaFin.atTime(23, 59, 59);

	    // Consultamos los movimientos del cliente en ese rango
	    List<Movimiento> movimientos = movimientoRepository.findByClienteAndFechaBetween(
	            clienteId, desde, hasta
	    );

	    // Mapeamos cada Movimiento a la fila de reporte solicitada
	    return movimientos.stream()
	            .map(this::toReporteDto)
	            .toList();
	}
	
	/**
	 * Mapper interno que convierte un Movimiento a la estructura del reporte
	 * @param m
	 * @return
	 */
	private ReporteMovimientoResponse toReporteDto(Movimiento m) {
		ReporteMovimientoResponse dto = new ReporteMovimientoResponse();

	    dto.setFecha(m.getFecha().toLocalDate());
	    
	    // Datos del cliente y cuenta
	    dto.setCliente(m.getCuenta().getCliente().getNombre());
	    dto.setNumeroCuenta(m.getCuenta().getNumeroCuenta());
	    dto.setTipoCuenta(m.getCuenta().getTipoCuenta());
	    dto.setEstado(m.getCuenta().getEstado());
	    
	    //Movimiento con signo
	    BigDecimal movimientoConSigno = (m.getTipo() == TipoMovimiento.RETIRO) ? m.getMonto().negate() : m.getMonto();
	    
	    dto.setMovimiento(movimientoConSigno);
	    
	    //Saldo disponible
	    dto.setSaldoDisponible(m.getSaldoPosterior());
	    
	    //Saldo inicial
	    dto.setSaldoInicial(m.getSaldoPosterior().subtract(movimientoConSigno));
	    
	    return dto;
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
