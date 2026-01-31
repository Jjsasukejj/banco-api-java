package com.banco.api.repository;

import com.banco.api.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
/**
 * Repository de Cliente
 * Spring Data JPA genera automaticamente CRUD basico, agregamos metodos de consulta que usaremos en los servicios
 */
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	/**
	 * Permite buscar un cliente por identificacion
	 */
	Optional<Cliente> findByIdentificacion(String identificacion);
}
