package br.com.fiap.aquasafe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fiap.aquasafe.model.Alerta;
import br.com.fiap.aquasafe.projection.AlertaProjection;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {

	// JPQL - alertas ativos
	@Query("from Alerta a where a.ativo = 1 order by a.dataHora desc")
	List<Alerta> buscarAlertasAtivos();

	// JPQL - alertas por regiao
	@Query("from Alerta a where a.regiao.id = :regiaoId order by a.dataHora desc")
	List<Alerta> buscarPorRegiao(Long regiaoId);

	// SQL Nativo - projection resumo de alertas ativos
	@Query(nativeQuery = true,
		value = "SELECT r.nome regiao_nome, a.tipo_alerta tipo_alerta, "
				+ "a.nivel_risco nivel_risco, a.mensagem mensagem "
				+ "FROM tb_alerta a "
				+ "INNER JOIN tb_regiao r ON (a.fk_regiao = r.id) "
				+ "WHERE a.ativo = 1 "
				+ "ORDER BY a.data_hora DESC")
	List<AlertaProjection> buscarResumoAlertasAtivos();

}
