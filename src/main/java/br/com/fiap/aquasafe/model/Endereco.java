package br.com.fiap.aquasafe.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Endereco {

	@Column(name = "logradouro")
	@Schema(description = "Logradouro", example = "Av. Paulista")
	private String logradouro;

	@Column(name = "numero")
	@Schema(description = "Numero", example = "1000")
	private String numero;

	@Column(name = "bairro")
	@Schema(description = "Bairro", example = "Bela Vista")
	private String bairro;

	@Column(name = "cep")
	@Schema(description = "CEP", example = "01310-100")
	private String cep;

	public Endereco() {
	}

	public Endereco(String logradouro, String numero, String bairro, String cep) {
		this.logradouro = logradouro;
		this.numero = numero;
		this.bairro = bairro;
		this.cep = cep;
	}

	public String getLogradouro() { return logradouro; }
	public void setLogradouro(String logradouro) { this.logradouro = logradouro; }
	public String getNumero() { return numero; }
	public void setNumero(String numero) { this.numero = numero; }
	public String getBairro() { return bairro; }
	public void setBairro(String bairro) { this.bairro = bairro; }
	public String getCep() { return cep; }
	public void setCep(String cep) { this.cep = cep; }
}
