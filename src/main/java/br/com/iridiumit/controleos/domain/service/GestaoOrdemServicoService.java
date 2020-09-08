package br.com.iridiumit.controleos.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.iridiumit.controleos.api.model.Comentario;
import br.com.iridiumit.controleos.domain.exception.EntidadeNaoEncontradaException;
import br.com.iridiumit.controleos.domain.exception.NegocioException;
import br.com.iridiumit.controleos.domain.model.Cliente;
import br.com.iridiumit.controleos.domain.model.OrdemServico;
import br.com.iridiumit.controleos.domain.model.StatusOrdemServico;
import br.com.iridiumit.controleos.domain.repository.ClienteRepository;
import br.com.iridiumit.controleos.domain.repository.ComentarioRepository;
import br.com.iridiumit.controleos.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {

	@Autowired
	private OrdemServicoRepository ordensServico;

	@Autowired
	private ClienteRepository clientes;

	@Autowired
	private ComentarioRepository comentarios;

	public OrdemServico criar(OrdemServico ordemServico) {
		Cliente cliente = clientes.findById(ordemServico.getCliente().getId())
				.orElseThrow(() -> new NegocioException("Cliente não encontrado!"));

		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());

		return ordensServico.save(ordemServico);
	}

	public void finalizar(Long ordemServicoId) {
		OrdemServico ordemServico = buscar(ordemServicoId);

		ordemServico.finalizar();

		ordensServico.save(ordemServico);
	}
	
	public void cancelar(Long ordemServicoId) {
		OrdemServico ordemServico = buscar(ordemServicoId);

		ordemServico.cancelar();

		ordensServico.save(ordemServico);

	}

	public Comentario adicionarComentario(Long ordemServicoId, String descricao) {
		OrdemServico ordemServico = buscar(ordemServicoId);

		Comentario comentario = new Comentario();
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setDescricao(descricao);
		comentario.setOrdemServico(ordemServico);

		return comentarios.save(comentario);
	}

	private OrdemServico buscar(Long ordemServicoId) {
		return ordensServico.findById(ordemServicoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada"));
	}

}
