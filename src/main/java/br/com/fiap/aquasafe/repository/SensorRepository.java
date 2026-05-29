package br.com.fiap.aquasafe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fiap.aquasafe.model.Sensor;
import br.com.fiap.aquasafe.model.StatusSensorEnum;

public interface SensorRepository extends JpaRepository<Sensor, Long> {

	// JPQL - sensores por regiao
	@Query("from Sensor s where s.regiao.id = :regiaoId")
	List<Sensor> buscarPorRegiao(Long regiaoId);

	// JPQL - sensores por status
	@Query("from Sensor s where s.status = :status")
	List<Sensor> buscarPorStatus(StatusSensorEnum status);

}
