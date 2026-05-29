package br.com.fiap.aquasafe.dto;

import java.time.LocalDate;

import br.com.fiap.aquasafe.model.Endereco;
import br.com.fiap.aquasafe.model.StatusUsuarioEnum;

public record UsuarioRequestDTO(
		String nome,
		String cpf,
		LocalDate dataNascimento,
		String emailPessoal,
		Endereco endereco,
		String rm,
		String senha,
		String permissao,
		LocalDate dataCriacao,
		StatusUsuarioEnum status) {
}