package br.com.fiap.aquasafe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fiap.aquasafe.model.NivelRiscoEnum;
import br.com.fiap.aquasafe.model.RegiaoMonitorada;
import br.com.fiap.aquasafe.projection.RegiaoProjection;

public interface RegiaoRepository extends JpaRepository<RegiaoMonitorada, Long> {

	@Query("from RegiaoMonitorada r where r.nivelRiscoAtual = :nivel")
	List<RegiaoMonitorada> buscarPorNivelRisco(NivelRiscoEnum nivel);

	@Query(nativeQuery = true,
		value = "SELECT r.nome regiao_nome, r.cidade regiao_cidade, r.estado regiao_estado, "
				+ "r.nivel_risco_atual regiao_nivel_risco, COUNT(s.id) total_sensores "
				+ "FROM tb_regiao r "
				+ "LEFT JOIN tb_sensor s ON (s.fk_regiao = r.id) "
				+ "GROUP BY r.nome, r.cidade, r.estado, r.nivel_risco_atual "
				+ "ORDER BY r.nome ASC")
	List<RegiaoProjection> buscarResumoRegioes();
}