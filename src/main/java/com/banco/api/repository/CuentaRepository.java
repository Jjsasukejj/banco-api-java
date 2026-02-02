package com.banco.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.banco.api.domain.entity.Cuenta;
/**
 * Repository Cuenta
 * Incluye metodos de consulta por numero de cuenta y por cliente, estos nos serviran para operaciones bancarias y reportes
 */
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
	/**
	 * Cusca un cuenta por su numero
	 * @param numeroCuenta
	 * @return
	 */
	@Query("""
		    select c
		    from Cuenta c
		    join fetch c.cliente cl
		    where c.numeroCuenta = :numeroCuenta
		""")
	Optional<Cuenta> findByNumeroCuentaFetchCliente(String numeroCuenta);
	/**
	 * Lista todas las cuentas de un cliente
	 * @param clienteId
	 * @return
	 */
	@Query("""
	        select c
	        from Cuenta c
	        join fetch c.cliente cl
	        where cl.id = :clienteId
	    """)
	List<Cuenta> findByClienteIdFetchCliente(Long clienteId);
	/**
	 * Lista todas las cuentas
	 * @param clienteId
	 * @return
	 */
	@Query("""
	        SELECT c
	        FROM Cuenta c
	        JOIN FETCH c.cliente
	    """)
	List<Cuenta> findAllWithCliente();
}
