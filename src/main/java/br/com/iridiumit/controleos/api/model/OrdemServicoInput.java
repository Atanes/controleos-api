package br.com.iridiumit.controleos.api.model;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class OrdemServicoInput {

	@NotBlank(message = "Campo descrição não pode estar vazio!")
	private String descricao;
	
	@NotNull(message = "Campo preço não pode estar vazio!")
	private BigDecimal preco;
	
	@Valid
	@NotNull(message = "Cliente não pode estar nulo ou vazio!")
	private ClienteIdInput cliente;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public ClienteIdInput getCliente() {
		return cliente;
	}

	public void setCliente(ClienteIdInput cliente) {
		this.cliente = cliente;
	}

}
