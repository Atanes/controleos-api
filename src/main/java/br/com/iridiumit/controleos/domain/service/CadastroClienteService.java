package br.com.iridiumit.controleos.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.iridiumit.controleos.domain.exception.NegocioException;
import br.com.iridiumit.controleos.domain.model.Cliente;
import br.com.iridiumit.controleos.domain.repository.ClienteRepository;

@Service
public class CadastroClienteService {

	@Autowired
	private ClienteRepository clientes;
	
	public Cliente salvar(Cliente cliente) {
		Cliente clienteExistente = clientes.findByEmail(cliente.getEmail());
		
		if (clienteExistente != null && !clienteExistente.equals(cliente)) {
			throw new NegocioException("Já existe um cliente cadastrado com este e-mail.");
		}
		
		return clientes.save(cliente);
	}
	
	public void excluir(Long clienteId) {
		try {
			clientes.deleteById(clienteId);
		} catch (DataIntegrityViolationException e) {
			throw new NegocioException("Esse cliente tem alguma Ordem de Serviço associada"
					+ " a ele e não pode ser excluido da base!");
		}
		
	}
	
}