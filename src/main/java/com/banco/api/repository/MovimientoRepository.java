package com.banco.api.repository;

import com.banco.api.domain.entity.Movimiento;
import com.banco.api.domain.enums.TipoMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
/**
 * Repository de Movimiento
 * COntiene consultas especificas necesarias para reportes, calculos de cupo diario
 */

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
	/**
	 * Obtiene movimientos de una cuenta en un rango de fechas
	 * @param cuentaId
	 * @param desde
	 * @param hasta
	 * @return
	 */
	List<Movimiento> findByCuentaIdAndFecha(Long cuentaId, LocalDateTime desde, LocalDateTime hasta);
	/**
	 * Suma el total de movimientos por tipo en un rango
	 * Controla el cupo diario, suma los retiros del dia y comparamos contra el limite permitido 
	 * COALESCE evita null cuando no hay movimientos.
	 * Lo colocamos aqui por que el Repository es el responsable de como se obtiene los datos mientras que el servicio decide que hacer con ellos.
	 * @param cuentaId
	 * @param tipo
	 * @param desde
	 * @param hasta
	 * @return
	 */
	@Query("""
	           SELECT COALESCE(SUM(m.monto), 0)
	           FROM Movimiento m
	           WHERE m.cuenta.id = :cuentaId
	             AND m.tipo = :tipo
	             AND m.fecha BETWEEN :desde AND :hasta
	           """)
	    BigDecimal sumMontoByCuentaAndTipoAndFechaBetween(
	            @Param("cuentaId") Long cuentaId,
	            @Param("tipo") TipoMovimiento tipo,
	            @Param("desde") LocalDateTime desde,
	            @Param("hasta") LocalDateTime hasta
	    );
	
	/**
	 * Reporte: lista movimientos por cliente (usuario) y rango de fechas.
	 * JOIN FETCH se usa para evitar LazyLoading fuera de la transaccion y para reducir N+1 queries cuando mapeamos a DTO.
	 */
	@Query("""
			  SELECT m
			  FROM Movimiento m
			  JOIN FETCH m.cuenta c
			  JOIN FETCH c.cliente cl
			  WHERE cl.id = :clienteId
			    AND m.fecha BETWEEN :desde AND :hasta
			  ORDER BY m.fecha DESC
			""")
	List<Movimiento> findByClienteAndFechaBetween(
	        @Param("clienteId") Long clienteId,
	        @Param("desde") LocalDateTime desde,
	        @Param("hasta") LocalDateTime hasta
	);
}
