package br.com.iridiumit.controleos.api.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ClienteInput {
	
	@NotBlank(message = "Nome do cliente não pode estar em branco e não pode ser nulo!")
	@Size(max = 80, message = "O nome do cliente não pode ser maior que 80 caracteres!")
	private String nome;
	
	@NotBlank(message = "E-mail do cliente não pode estar em branco e não pode ser nulo!")
	@Email(message = "Endereço de e-mail deve ser válido!")
	@Size(max = 255, message = "O e-mail do cliente não pode ser maior que 255 caracteres!")
	private String email;
	
	@NotBlank(message = "Telefone do cliente não pode estar em branco e não pode ser nulo!")
	@Size(max = 20, message = "O telefone do cliente não pode ser maior que 20 caracteres!")
	private String telefone;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

}
