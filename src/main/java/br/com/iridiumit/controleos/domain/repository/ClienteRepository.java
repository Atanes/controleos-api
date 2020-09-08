package br.com.iridiumit.controleos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.iridiumit.controleos.domain.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	Cliente findByEmail(String email);

}
