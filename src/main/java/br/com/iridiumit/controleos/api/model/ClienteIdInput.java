package br.com.iridiumit.controleos.api.model;

import javax.validation.constraints.NotNull;

public class ClienteIdInput {

	@NotNull(message = "Id do cliente é obrigatório!")
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
