package com.example.osworks.domain.repository;

import com.example.osworks.domain.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	Cliente findByEmail(String email);
}
