package br.com.fiap.aquasafe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.com.fiap.aquasafe.model.Sensor;
import br.com.fiap.aquasafe.model.StatusSensorEnum;
import br.com.fiap.aquasafe.repository.SensorRepository;

@Service
public class SensorCachingService {

	@Autowired
	private SensorRepository repS;

	@Cacheable(value = "todosSensores")
	public List<Sensor> findAll() {
		return repS.findAll();
	}

	@Cacheable(value = "sensorPorId", key = "#id")
	public Optional<Sensor> findById(Long id) {
		return repS.findById(id);
	}

	@Cacheable(value = "sensoresPorRegiao", key = "#regiaoId")
	public List<Sensor> buscarPorRegiao(Long regiaoId) {
		return repS.buscarPorRegiao(regiaoId);
	}

	@Cacheable(value = "sensoresPorStatus", key = "#status")
	public List<Sensor> buscarPorStatus(StatusSensorEnum status) {
		return repS.buscarPorStatus(status);
	}

	@CacheEvict(value = { "todosSensores", "sensorPorId",
			"sensoresPorRegiao", "sensoresPorStatus" }, allEntries = true)
	public void removerCache() {
		System.out.println("Cache de sensores removido");
	}
}
