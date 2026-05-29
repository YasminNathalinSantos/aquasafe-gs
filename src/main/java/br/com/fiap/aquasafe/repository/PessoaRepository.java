package br.com.fiap.aquasafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.aquasafe.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
