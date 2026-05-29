package br.com.fiap.aquasafe.control;



import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.aquasafe.dto.LeituraDTO;
import br.com.fiap.aquasafe.dto.LeituraRequestDTO;
import br.com.fiap.aquasafe.model.LeituraSensor;
import br.com.fiap.aquasafe.repository.LeituraRepository;
import br.com.fiap.aquasafe.repository.SensorRepository;
import br.com.fiap.aquasafe.service.AlertaCachingService;
import br.com.fiap.aquasafe.service.LeituraCachingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Leituras", description = "Endpoints para registro e consulta de leituras de sensores")
@RestController
@RequestMapping(value = "/leituras")
public class LeituraController {

	@Autowired
	private LeituraRepository repL;

	@Autowired
	private SensorRepository repS;

	@Autowired
	private LeituraCachingService cacheL;

	@Autowired
	private AlertaCachingService cacheA;

	@Operation(summary = "Listar todas as leituras (caching)", tags = "Leituras")
	@GetMapping(value = "/todas")
	public List<LeituraSensor> listarTodas() {
		return cacheL.findAll();
	}

	@Operation(summary = "Buscar leitura por ID com HATEOAS", tags = "Leituras")
	@GetMapping(value = "/{id}")
	public ResponseEntity<EntityModel<LeituraDTO>> buscarPorId(@PathVariable Long id) {
		Optional<LeituraSensor> op = cacheL.findById(id);

		if (op.isPresent()) {
			LeituraDTO dto = new LeituraDTO(op.get());

			Link selfLink = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder.methodOn(LeituraController.class).buscarPorId(id))
					.withSelfRel();
			Link todasLink = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder.methodOn(LeituraController.class).listarTodas())
					.withRel("todas-leituras");

			return ResponseEntity.ok(EntityModel.of(dto, selfLink, todasLink));
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Leitura com id " + id + " nao encontrada");
		}
	}

	@Operation(summary = "Buscar leituras por sensor", tags = "Leituras")
	@GetMapping(value = "/sensor/{sensorId}")
	public List<LeituraSensor> buscarPorSensor(@PathVariable Long sensorId) {
		return cacheL.buscarPorSensor(sensorId);
	}

	@Operation(summary = "Buscar leituras por regiao", tags = "Leituras")
	@GetMapping(value = "/regiao/{regiaoId}")
	public List<LeituraSensor> buscarPorRegiao(@PathVariable Long regiaoId) {
		return cacheL.buscarPorRegiao(regiaoId);
	}

	@Operation(summary = "Registrar nova leitura (dispara alerta automatico se nivel > 85%)", tags = "Leituras")
	@PostMapping(value = "/registrar")
	public ResponseEntity<LeituraDTO> registrar(@RequestBody LeituraRequestDTO dto) {
		LeituraSensor leitura = new LeituraSensor();

		if (dto.sensorId() != null) {
			repS.findById(dto.sensorId())
				.ifPresentOrElse(
					leitura::setSensor,
					() -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND,
							"Sensor com id " + dto.sensorId() + " nao encontrado"); });
		}

		if (dto.nivelAgua() != null) leitura.setNivelAgua(dto.nivelAgua());
		if (dto.volumeChuva() != null) leitura.setVolumeChuva(dto.volumeChuva());
		leitura.setDataHora(dto.dataHora() != null ? dto.dataHora() : LocalDateTime.now());

		repL.save(leitura);
		cacheL.removerCache();


		cacheA.gerarAlertaSeNecessario(leitura);

		return ResponseEntity.status(HttpStatus.CREATED).body(new LeituraDTO(leitura));
	}

	@Operation(summary = "Atualizar leitura (passa so o que quer mudar)", tags = "Leituras")
	@PutMapping(value = "/{id}")
	public ResponseEntity<LeituraDTO> atualizar(
			@PathVariable Long id,
			@RequestBody LeituraRequestDTO dto) {

		Optional<LeituraSensor> op = cacheL.findById(id);

		if (op.isPresent()) {
			LeituraSensor leitura = op.get();

			if (dto.sensorId() != null) {
				repS.findById(dto.sensorId())
					.ifPresentOrElse(
						leitura::setSensor,
						() -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND,
								"Sensor com id " + dto.sensorId() + " nao encontrado"); });
			}

			if (dto.nivelAgua() != null) leitura.setNivelAgua(dto.nivelAgua());
			if (dto.volumeChuva() != null) leitura.setVolumeChuva(dto.volumeChuva());
			if (dto.dataHora() != null) leitura.setDataHora(dto.dataHora());

			repL.save(leitura);
			cacheL.removerCache();
			return ResponseEntity.ok(new LeituraDTO(leitura));
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Leitura com id " + id + " nao encontrada");
		}
	}

	@Operation(summary = "Remover leitura", tags = "Leituras")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<LeituraDTO> remover(@PathVariable Long id) {
		Optional<LeituraSensor> op = cacheL.findById(id);

		if (op.isPresent()) {
			repL.delete(op.get());
			cacheL.removerCache();
			return ResponseEntity.ok(new LeituraDTO(op.get()));
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Leitura com id " + id + " nao encontrada");
		}
	}
}