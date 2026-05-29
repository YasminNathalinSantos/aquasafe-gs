package br.com.fiap.aquasafe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.com.fiap.aquasafe.model.LeituraSensor;
import br.com.fiap.aquasafe.repository.LeituraRepository;

@Service
public class LeituraCachingService {

	@Autowired
	private LeituraRepository repL;

	@Cacheable(value = "todasLeituras")
	public List<LeituraSensor> findAll() {
		return repL.findAll();
	}

	@Cacheable(value = "leituraPorId", key = "#id")
	public Optional<LeituraSensor> findById(Long id) {
		return repL.findById(id);
	}

	@Cacheable(value = "leiturasPorSensor", key = "#sensorId")
	public List<LeituraSensor> buscarPorSensor(Long sensorId) {
		return repL.buscarPorSensor(sensorId);
	}

	@Cacheable(value = "leiturasPorRegiao", key = "#regiaoId")
	public List<LeituraSensor> buscarPorRegiao(Long regiaoId) {
		return repL.buscarPorRegiao(regiaoId);
	}

	@CacheEvict(value = { "todasLeituras", "leituraPorId",
			"leiturasPorSensor", "leiturasPorRegiao" }, allEntries = true)
	public void removerCache() {
		System.out.println("Cache de leituras removido");
	}
}
