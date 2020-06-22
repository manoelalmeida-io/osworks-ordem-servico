package com.example.osworks.domain.service;

import com.example.osworks.domain.exception.NegocioException;
import com.example.osworks.domain.model.Cliente;
import com.example.osworks.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroClienteService {
	
	@Autowired
	private ClienteRepository repository;
	
	public Cliente salvar(Cliente cliente) {
		Cliente clienteExistente = repository.findByEmail(cliente.getEmail());
		
		if (clienteExistente != null && !clienteExistente.equals(cliente)) {
			throw new NegocioException("Já existe um cliente cadastrado com este e-mail.");
		}
		
		return repository.save(cliente);
	}
	
	public void excluir(Long id) {
		repository.deleteById(id);
	}
}
