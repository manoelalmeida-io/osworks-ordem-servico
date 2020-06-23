package com.example.osworks.api.controller;

import com.example.osworks.api.model.OrdemServicoInput;
import com.example.osworks.api.model.OrdemServicoModel;
import com.example.osworks.domain.model.OrdemServico;
import com.example.osworks.domain.repository.OrdemServicoRepository;
import com.example.osworks.domain.service.GestaoOrdemServicoService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

	@Autowired
	private GestaoOrdemServicoService service;
	
	@Autowired
	private OrdemServicoRepository repository;

	@Autowired
	private ModelMapper mapper;
	
	@GetMapping
	public List<OrdemServicoModel> listar() {
		return toCollectionModel(repository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OrdemServicoModel> buscar(@PathVariable Long id) {
		Optional<OrdemServico> ordemServico = repository.findById(id);
		
		if (ordemServico.isPresent()) {
			OrdemServicoModel model = toModel(ordemServico.get());
			return ResponseEntity.ok(model);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServicoModel criar(@Valid @RequestBody OrdemServicoInput ordemServicoInput) {
		OrdemServico ordemServico = toEntity(ordemServicoInput);
		
		return toModel(service.criar(ordemServico));
	}
	
	@PutMapping("/{id}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable Long id) {
		service.finalizar(id);
	}
	
	private OrdemServicoModel toModel(OrdemServico ordemServico) {
		return mapper.map(ordemServico, OrdemServicoModel.class);
	}
	
	private List<OrdemServicoModel> toCollectionModel(List<OrdemServico> ordensServico) {
		return ordensServico.stream()
				.map(ordemServico -> toModel(ordemServico))
				.collect(Collectors.toList());
	}
	
	private OrdemServico toEntity(OrdemServicoInput ordemServicoInput) {
		return mapper.map(ordemServicoInput, OrdemServico.class);
	}
}
