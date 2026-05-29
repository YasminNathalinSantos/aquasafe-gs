package br.com.fiap.aquasafe.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.com.fiap.aquasafe.model.Alerta;
import br.com.fiap.aquasafe.model.LeituraSensor;
import br.com.fiap.aquasafe.model.NivelRiscoEnum;
import br.com.fiap.aquasafe.model.TipoAlertaEnum;
import br.com.fiap.aquasafe.projection.AlertaProjection;
import br.com.fiap.aquasafe.repository.AlertaRepository;

@Service
public class AlertaCachingService {

	@Autowired
	private AlertaRepository repA;

	@Cacheable(value = "todosAlertas")
	public List<Alerta> findAll() {
		return repA.findAll();
	}

	@Cacheable(value = "alertaPorId", key = "#id")
	public Optional<Alerta> findById(Long id) {
		return repA.findById(id);
	}

	@Cacheable(value = "alertasAtivos")
	public List<Alerta> buscarAlertasAtivos() {
		return repA.buscarAlertasAtivos();
	}

	@Cacheable(value = "resumoAlertasAtivos")
	public List<AlertaProjection> buscarResumoAlertasAtivos() {
		return repA.buscarResumoAlertasAtivos();
	}

	// Regra de negocio: gera alerta automatico se nivel > 85%
	public void gerarAlertaSeNecessario(LeituraSensor leitura) {
		if (leitura.getNivelAgua() != null && leitura.getNivelAgua() > 85.0) {
			Alerta alerta = new Alerta();
			alerta.setRegiao(leitura.getSensor().getRegiao());
			alerta.setTipoAlerta(TipoAlertaEnum.CRITICO);
			alerta.setNivelRisco(NivelRiscoEnum.CRITICO);
			alerta.setMensagem("ALERTA AUTOMATICO: Nivel de agua em "
					+ leitura.getNivelAgua() + "% no sensor "
					+ leitura.getSensor().getCodigo()
					+ ". Risco critico de enchente!");
			alerta.setDataHora(LocalDateTime.now());
			alerta.setAtivo(1);
			repA.save(alerta);
			removerCache();
		}
	}

	@CacheEvict(value = { "todosAlertas", "alertaPorId",
			"alertasAtivos", "resumoAlertasAtivos" }, allEntries = true)
	public void removerCache() {
		System.out.println("Cache de alertas removido");
	}
}
