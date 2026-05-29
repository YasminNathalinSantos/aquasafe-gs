package br.com.fiap.aquasafe.model;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Schema(description = "Entidade que representa uma pessoa fisica no sistema AquaSafe")
@Entity
@Table(name = "tb_pessoa")
public class Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "O nome e um campo obrigatorio")
	@Size(min = 2, max = 100, message = "O nome deve ter ao menos 2 e no maximo 100 caracteres")
	@Column(name = "nome")
	@Schema(description = "Nome completo da pessoa", example = "Joao Silva")
	private String nome;

	@Column(name = "cpf")
	@Schema(description = "CPF sem pontuacao", example = "12345678909")
	private String cpf;

	@Column(name = "data_nascimento")
	@Schema(description = "Data de nascimento", example = "1990-01-15")
	private LocalDate dataNascimento;

	@Column(name = "email_pessoal")
	@Schema(description = "Email pessoal", example = "joao@email.com")
	private String emailPessoal;

	// Modelagem avancada: Embedded - endereco embutido na tabela pessoa
	@Embedded
	private Endereco endereco;

	public Pessoa() {
	}

	public Pessoa(Long id, String nome, String cpf, LocalDate dataNascimento,
			String emailPessoal, Endereco endereco) {
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.emailPessoal = emailPessoal;
		this.endereco = endereco;
	}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }
	public String getCpf() { return cpf; }
	public void setCpf(String cpf) { this.cpf = cpf; }
	public LocalDate getDataNascimento() { return dataNascimento; }
	public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }
	public String getEmailPessoal() { return emailPessoal; }
	public void setEmailPessoal(String emailPessoal) { this.emailPessoal = emailPessoal; }
	public Endereco getEndereco() { return endereco; }
	public void setEndereco(Endereco endereco) { this.endereco = endereco; }
}
