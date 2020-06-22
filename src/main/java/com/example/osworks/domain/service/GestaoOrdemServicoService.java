package com.example.osworks.domain.service;

import com.example.osworks.domain.exception.NegocioException;
import com.example.osworks.domain.model.Cliente;
import com.example.osworks.domain.model.OrdemServico;
import com.example.osworks.domain.model.StatusOrdemServico;
import com.example.osworks.domain.repository.ClienteRepository;
import com.example.osworks.domain.repository.OrdemServicoRepository;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestaoOrdemServicoService {

	@Autowired
	private OrdemServicoRepository repository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public OrdemServico criar(OrdemServico ordemServico) {
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow(() -> new NegocioException("Cliente não encontrado"));
		
		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(LocalDateTime.now());
		
		return repository.save(ordemServico);
	}
}
