package br.com.iridiumit.controleos.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.iridiumit.controleos.api.model.ClienteInput;
import br.com.iridiumit.controleos.api.model.ClienteModel;
import br.com.iridiumit.controleos.domain.model.Cliente;
import br.com.iridiumit.controleos.domain.repository.ClienteRepository;
import br.com.iridiumit.controleos.domain.service.CadastroClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	ClienteRepository clientes;
	
	@Autowired
	CadastroClienteService clienteService;
	
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping
	public List<ClienteModel> listar() {

		return toCollectionModel(clientes.findAll());

	}

	@GetMapping("/{idCliente}")
	public ResponseEntity<ClienteModel> buscaCliente(@PathVariable Long idCliente) {

		Optional<Cliente> cliente = clientes.findById(idCliente);
		if (cliente.isPresent()) {
			ClienteModel clienteModel = toModel(cliente.get());
			return ResponseEntity.ok(clienteModel); //Retorna Status 200 Ok e os dados do cliente no corpo da resposta
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public ClienteModel incluirCliente(@Valid @RequestBody ClienteInput cliente) {
		
		return toModel(clienteService.salvar(toEntity(cliente)));
	}

	@PutMapping("/{idCliente}")
	public ResponseEntity<ClienteModel> buscaCliente(@PathVariable Long idCliente, @Valid @RequestBody ClienteModel clienteModel) {
		if (!clientes.existsById(idCliente)) {
			return ResponseEntity.notFound().build(); //Retorna Status 404 Not Found
		}
		clienteModel.setId(idCliente);
		return ResponseEntity.ok(toModel(clienteService.salvar(toEntity(clienteModel))));

	}
	
	@DeleteMapping("/{idCliente}")
	public ResponseEntity<Void> excluirCliente(@PathVariable Long idCliente){
		if (!clientes.existsById(idCliente)) {
			return ResponseEntity.notFound().build();
		}
		
		clienteService.excluir(idCliente);
		
		return ResponseEntity.noContent().build(); //Retorna Status 204 No Content
	}
	
	private List<ClienteModel> toCollectionModel(List<Cliente> clientes) {
		return clientes.stream()
				.map(cliente -> toModel(cliente))
				.collect(Collectors.toList());
	}

	private ClienteModel toModel(Cliente cliente) {
		return modelMapper.map(cliente, ClienteModel.class);
	}
	
	private Cliente toEntity(ClienteInput clienteInput) {
		return modelMapper.map(clienteInput, Cliente.class);
	}
	
	private Cliente toEntity(ClienteModel clienteModel) {
		return modelMapper.map(clienteModel, Cliente.class);
	}

}
