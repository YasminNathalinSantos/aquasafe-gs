package br.com.fiap.aquasafe.control;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.aquasafe.dto.RegiaoDTO;
import br.com.fiap.aquasafe.dto.RegiaoRequestDTO;
import br.com.fiap.aquasafe.model.Alerta;
import br.com.fiap.aquasafe.model.LeituraSensor;
import br.com.fiap.aquasafe.model.NivelRiscoEnum;
import br.com.fiap.aquasafe.model.RegiaoMonitorada;
import br.com.fiap.aquasafe.model.Sensor;
import br.com.fiap.aquasafe.projection.RegiaoProjection;
import br.com.fiap.aquasafe.repository.AlertaRepository;
import br.com.fiap.aquasafe.repository.LeituraRepository;
import br.com.fiap.aquasafe.repository.RegiaoRepository;
import br.com.fiap.aquasafe.repository.SensorRepository;
import br.com.fiap.aquasafe.service.RegiaoCachingService;
import br.com.fiap.aquasafe.service.RegiaoPaginacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Regioes Monitoradas", description = "Endpoints para gestao de regioes monitoradas")
@RestController
@RequestMapping(value = "/regioes")
public class RegiaoController {

	@Autowired
	private RegiaoRepository repR;

	@Autowired
	private SensorRepository repS;

	@Autowired
	private LeituraRepository repL;

	@Autowired
	private AlertaRepository repA;

	@Autowired
	private RegiaoCachingService cacheR;

	@Autowired
	private RegiaoPaginacaoService paginacaoR;

	@Operation(summary = "Listar todas as regioes (caching)", tags = "Regioes Monitoradas")
	@GetMapping(value = "/todas")
	public List<RegiaoMonitorada> listarTodas() {
		return cacheR.findAll();
	}

	@Operation(summary = "Listar regioes paginadas como DTO", tags = "Regioes Monitoradas")
	@GetMapping(value = "/paginadas")
	public ResponseEntity<Page<RegiaoDTO>> paginar(
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "size", defaultValue = "2") Integer size) {
		PageRequest pr = PageRequest.of(page, size);
		return ResponseEntity.ok(paginacaoR.paginar(pr));
	}

	@Operation(summary = "Buscar regiao por ID com HATEOAS", tags = "Regioes Monitoradas")
	@GetMapping(value = "/{id}")
	public ResponseEntity<EntityModel<RegiaoMonitorada>> buscarPorId(@PathVariable Long id) {
		Optional<RegiaoMonitorada> op = cacheR.findById(id);

		if (op.isPresent()) {
			RegiaoMonitorada regiao = op.get();

			Link selfLink = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder.methodOn(RegiaoController.class).buscarPorId(id))
					.withSelfRel();
			Link todasLink = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder.methodOn(RegiaoController.class).listarTodas())
					.withRel("todas-regioes");

			return ResponseEntity.ok(EntityModel.of(regiao, selfLink, todasLink));
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Regiao com id " + id + " nao encontrada");
		}
	}

	@Operation(summary = "Buscar regioes por nivel de risco", tags = "Regioes Monitoradas")
	@GetMapping(value = "/nivel-risco")
	public List<RegiaoMonitorada> buscarPorNivelRisco(@RequestParam NivelRiscoEnum nivel) {
		return cacheR.buscarPorNivelRisco(nivel);
	}

	@Operation(summary = "Buscar resumo de regioes com total de sensores (projection)", tags = "Regioes Monitoradas")
	@GetMapping(value = "/resumo")
	public List<RegiaoProjection> buscarResumo() {
		return cacheR.buscarResumoRegioes();
	}

	@Operation(summary = "Cadastrar nova regiao", tags = "Regioes Monitoradas")
	@PostMapping(value = "/cadastrar")
	public ResponseEntity<RegiaoDTO> cadastrar(@RequestBody RegiaoRequestDTO dto) {
		RegiaoMonitorada regiao = new RegiaoMonitorada();
		if (dto.nome() != null) regiao.setNome(dto.nome());
		if (dto.cidade() != null) regiao.setCidade(dto.cidade());
		if (dto.estado() != null) regiao.setEstado(dto.estado());
		if (dto.nivelRiscoAtual() != null) regiao.setNivelRiscoAtual(dto.nivelRiscoAtual());
		if (dto.latitude() != null) regiao.setLatitude(dto.latitude());
		if (dto.longitude() != null) regiao.setLongitude(dto.longitude());

		repR.save(regiao);
		cacheR.removerCache();
		return ResponseEntity.status(HttpStatus.CREATED).body(new RegiaoDTO(regiao));
	}

	@Operation(summary = "Atualizar regiao (passa so o que quer mudar)", tags = "Regioes Monitoradas")
	@PutMapping(value = "/{id}")
	public ResponseEntity<RegiaoDTO> atualizar(
			@PathVariable Long id,
			@RequestBody RegiaoRequestDTO dto) {

		Optional<RegiaoMonitorada> op = cacheR.findById(id);

		if (op.isPresent()) {
			RegiaoMonitorada regiao = op.get();

			if (dto.nome() != null) regiao.setNome(dto.nome());
			if (dto.cidade() != null) regiao.setCidade(dto.cidade());
			if (dto.estado() != null) regiao.setEstado(dto.estado());
			if (dto.nivelRiscoAtual() != null) regiao.setNivelRiscoAtual(dto.nivelRiscoAtual());
			if (dto.latitude() != null) regiao.setLatitude(dto.latitude());
			if (dto.longitude() != null) regiao.setLongitude(dto.longitude());

			repR.save(regiao);
			cacheR.removerCache();
			return ResponseEntity.ok(new RegiaoDTO(regiao));
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Regiao com id " + id + " nao encontrada");
		}
	}

	@Operation(summary = "Remover regiao e todos os seus filhos (sensores, leituras, alertas)", tags = "Regioes Monitoradas")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<RegiaoMonitorada> remover(@PathVariable Long id) {
		Optional<RegiaoMonitorada> op = cacheR.findById(id);

		if (op.isPresent()) {
			RegiaoMonitorada regiao = op.get();

			// 1. Deletar alertas da regiao
			List<Alerta> alertas = repA.buscarPorRegiao(id);
			repA.deleteAll(alertas);

			// 2. Deletar leituras e sensores da regiao
			List<Sensor> sensores = repS.buscarPorRegiao(id);
			for (Sensor sensor : sensores) {
				List<LeituraSensor> leituras = repL.buscarPorSensor(sensor.getId());
				repL.deleteAll(leituras);
			}
			repS.deleteAll(sensores);

			// 3. Deletar a regiao
			repR.delete(regiao);
			cacheR.removerCache();

			return ResponseEntity.ok(regiao);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Regiao com id " + id + " nao encontrada");
		}
	}
}