package com.example.osworks.domain.service;

import com.example.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.example.osworks.domain.exception.NegocioException;
import com.example.osworks.domain.model.Cliente;
import com.example.osworks.domain.model.Comentario;
import com.example.osworks.domain.model.OrdemServico;
import com.example.osworks.domain.model.StatusOrdemServico;
import com.example.osworks.domain.repository.ClienteRepository;
import com.example.osworks.domain.repository.ComentarioRepository;
import com.example.osworks.domain.repository.OrdemServicoRepository;
import java.time.OffsetDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestaoOrdemServicoService {

	@Autowired
	private OrdemServicoRepository repository;
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public OrdemServico criar(OrdemServico ordemServico) {
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow(() -> new NegocioException("Cliente não encontrado"));
		
		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		
		return repository.save(ordemServico);
	}
	
	public void finalizar(Long id) {
		OrdemServico ordemServico = buscar(id);
		
		ordemServico.finalizar();
		
		repository.save(ordemServico);
	}
	
	public Comentario adicionarComentario(Long ordemServicoId, String descricao) {
		OrdemServico ordemServico = buscar(ordemServicoId);
		
		Comentario comentario = new Comentario();
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setDescricao(descricao);
		comentario.setOrdemServico(ordemServico);
		
		return comentarioRepository.save(comentario);
	}
	
	private OrdemServico buscar(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada"));
	}
}
