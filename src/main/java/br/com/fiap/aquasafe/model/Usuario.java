package br.com.fiap.aquasafe.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "fk_pessoa")
	private Pessoa pessoa;

	@Column(name = "rm")
	private String rm;

	@Column(name = "senha")
	private String senha;

	@Column(name = "permissao")
	private String permissao;

	@Column(name = "data_criacao")
	private LocalDate dataCriacao;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private StatusUsuarioEnum status;

	public Usuario() {
	}

	public Usuario(Long id, Pessoa pessoa, String rm, String senha,
			String permissao, LocalDate dataCriacao, StatusUsuarioEnum status) {
		this.id = id;
		this.pessoa = pessoa;
		this.rm = rm;
		this.senha = senha;
		this.permissao = permissao;
		this.dataCriacao = dataCriacao;
		this.status = status;
	}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public Pessoa getPessoa() { return pessoa; }
	public void setPessoa(Pessoa pessoa) { this.pessoa = pessoa; }
	public String getRm() { return rm; }
	public void setRm(String rm) { this.rm = rm; }
	public String getSenha() { return senha; }
	public void setSenha(String senha) { this.senha = senha; }
	public String getPermissao() { return permissao; }
	public void setPermissao(String permissao) { this.permissao = permissao; }
	public LocalDate getDataCriacao() { return dataCriacao; }
	public void setDataCriacao(LocalDate dataCriacao) { this.dataCriacao = dataCriacao; }
	public StatusUsuarioEnum getStatus() { return status; }
	public void setStatus(StatusUsuarioEnum status) { this.status = status; }
}
