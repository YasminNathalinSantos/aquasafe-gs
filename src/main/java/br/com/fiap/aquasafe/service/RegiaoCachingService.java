package br.com.fiap.aquasafe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.fiap.aquasafe.model.NivelRiscoEnum;
import br.com.fiap.aquasafe.model.RegiaoMonitorada;
import br.com.fiap.aquasafe.projection.RegiaoProjection;
import br.com.fiap.aquasafe.repository.RegiaoRepository;

@Service
public class RegiaoCachingService {

	@Autowired
	private RegiaoRepository repR;

	@Cacheable(value = "todasRegioes")
	public List<RegiaoMonitorada> findAll() {
		return repR.findAll();
	}

	@Cacheable(value = "regiaoPorId", key = "#id")
	public Optional<RegiaoMonitorada> findById(Long id) {
		return repR.findById(id);
	}

	@Cacheable(value = "regioesPorNivel", key = "#nivel")
	public List<RegiaoMonitorada> buscarPorNivelRisco(NivelRiscoEnum nivel) {
		return repR.buscarPorNivelRisco(nivel);
	}

	@Cacheable(value = "resumoRegioes")
	public List<RegiaoProjection> buscarResumoRegioes() {
		return repR.buscarResumoRegioes();
	}

	@Cacheable(value = "regioesPaginadas", key = "#pr")
	public Page<RegiaoMonitorada> findAll(PageRequest pr) {
		return repR.findAll(pr);
	}

	@CacheEvict(value = { "todasRegioes", "regiaoPorId", "regioesPorNivel",
			"resumoRegioes", "regioesPaginadas" }, allEntries = true)
	public void removerCache() {
		System.out.println("Cache de regioes removido");
	}
}
