package br.com.fiap.aquasafe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fiap.aquasafe.model.LeituraSensor;

public interface LeituraRepository extends JpaRepository<LeituraSensor, Long> {

	// JPQL - leituras por sensor
	@Query("from LeituraSensor l where l.sensor.id = :sensorId order by l.dataHora desc")
	List<LeituraSensor> buscarPorSensor(Long sensorId);

	// JPQL - leituras por regiao
	@Query("from LeituraSensor l where l.sensor.regiao.id = :regiaoId order by l.dataHora desc")
	List<LeituraSensor> buscarPorRegiao(Long regiaoId);

	// JPQL - ultima leitura por sensor
	@Query("from LeituraSensor l where l.sensor.id = :sensorId order by l.dataHora desc")
	List<LeituraSensor> buscarUltimaLeituraPorSensor(Long sensorId);

}
