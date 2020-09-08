package br.com.iridiumit.controleos.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.iridiumit.controleos.api.model.Comentario;
import br.com.iridiumit.controleos.api.model.ComentarioInput;
import br.com.iridiumit.controleos.api.model.ComentarioModel;
import br.com.iridiumit.controleos.domain.exception.EntidadeNaoEncontradaException;
import br.com.iridiumit.controleos.domain.model.OrdemServico;
import br.com.iridiumit.controleos.domain.repository.OrdemServicoRepository;
import br.com.iridiumit.controleos.domain.service.GestaoOrdemServicoService;


@RestController
@RequestMapping("/ordens-servico/{ordemServicoId}/comentarios")
public class ComentarioController {

	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServico;
	
	@Autowired
	private OrdemServicoRepository ordensServico;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
	public List<ComentarioModel> listar(@PathVariable Long ordemServicoId) {
		OrdemServico ordemServico = ordensServico.findById(ordemServicoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada"));
		
		return toCollectionModel(ordemServico.getComentarios());
		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ComentarioModel adicionar(@PathVariable Long ordemServicoId, 
			@Valid @RequestBody ComentarioInput comentarioInput) {
		Comentario comentario = gestaoOrdemServico.adicionarComentario(ordemServicoId, 
				comentarioInput.getDescricao());
		
		return toModel(comentario);
	}
	
	private ComentarioModel toModel(Comentario comentario) {
		return modelMapper.map(comentario, ComentarioModel.class);
	}
	
	private List<ComentarioModel> toCollectionModel(List<Comentario> comentarios) {
		return comentarios.stream()
				.map(comentario -> toModel(comentario))
				.collect(Collectors.toList());
	}
	
}
