package com.banco.api.repository;

import com.banco.api.domain.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
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
	Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);
	/**
	 * Lista todas las cuentas de un cliente
	 * @param clienteId
	 * @return
	 */
	List<Cuenta> findByClienteId(Long clienteId);
}
