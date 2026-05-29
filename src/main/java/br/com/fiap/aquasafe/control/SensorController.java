package br.com.fiap.aquasafe.control;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.aquasafe.dto.SensorDTO;
import br.com.fiap.aquasafe.dto.SensorRequestDTO;
import br.com.fiap.aquasafe.model.LeituraSensor;
import br.com.fiap.aquasafe.model.Sensor;
import br.com.fiap.aquasafe.model.StatusSensorEnum;
import br.com.fiap.aquasafe.repository.LeituraRepository;
import br.com.fiap.aquasafe.repository.RegiaoRepository;
import br.com.fiap.aquasafe.repository.SensorRepository;
import br.com.fiap.aquasafe.service.SensorCachingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Sensores", description = "Endpoints para gestao de sensores")
@RestController
@RequestMapping(value = "/sensores")
public class SensorController {

	@Autowired
	private SensorRepository repS;

	@Autowired
	private RegiaoRepository repR;

	@Autowired
	private LeituraRepository repL;

	@Autowired
	private SensorCachingService cacheS;

	@Operation(summary = "Listar todos os sensores (caching)", tags = "Sensores")
	@GetMapping(value = "/todos")
	public List<Sensor> listarTodos() {
		return cacheS.findAll();
	}

	@Operation(summary = "Buscar sensor por ID com HATEOAS", tags = "Sensores")
	@GetMapping(value = "/{id}")
	public ResponseEntity<EntityModel<SensorDTO>> buscarPorId(@PathVariable Long id) {
		Optional<Sensor> op = cacheS.findById(id);

		if (op.isPresent()) {
			SensorDTO dto = new SensorDTO(op.get());

			Link selfLink = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder.methodOn(SensorController.class).buscarPorId(id))
					.withSelfRel();
			Link todosLink = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder.methodOn(SensorController.class).listarTodos())
					.withRel("todos-sensores");

			return ResponseEntity.ok(EntityModel.of(dto, selfLink, todosLink));
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Sensor com id " + id + " nao encontrado");
		}
	}

	@Operation(summary = "Buscar sensores por regiao", tags = "Sensores")
	@GetMapping(value = "/regiao/{regiaoId}")
	public List<Sensor> buscarPorRegiao(@PathVariable Long regiaoId) {
		return cacheS.buscarPorRegiao(regiaoId);
	}

	@Operation(summary = "Buscar sensores por status", tags = "Sensores")
	@GetMapping(value = "/status")
	public List<Sensor> buscarPorStatus(@RequestParam StatusSensorEnum status) {
		return cacheS.buscarPorStatus(status);
	}

	@Operation(summary = "Cadastrar novo sensor", tags = "Sensores")
	@PostMapping(value = "/cadastrar")
	public ResponseEntity<SensorDTO> cadastrar(@RequestBody SensorRequestDTO dto) {
		Sensor sensor = new Sensor();
		sensor.setCodigo(dto.codigo());
		sensor.setTipoSensor(dto.tipoSensor());
		sensor.setStatus(dto.status());

		if (dto.regiaoId() != null) {
			repR.findById(dto.regiaoId())
				.ifPresentOrElse(
					sensor::setRegiao,
					() -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND,
							"Regiao com id " + dto.regiaoId() + " nao encontrada"); });
		}

		repS.save(sensor);
		cacheS.removerCache();
		return ResponseEntity.status(HttpStatus.CREATED).body(new SensorDTO(sensor));
	}

	@Operation(summary = "Atualizar sensor (passa so o que quer mudar)", tags = "Sensores")
	@PutMapping(value = "/{id}")
	public ResponseEntity<SensorDTO> atualizar(
			@PathVariable Long id,
			@RequestBody SensorRequestDTO dto) {

		Optional<Sensor> op = cacheS.findById(id);

		if (op.isPresent()) {
			Sensor sensor = op.get();

			if (dto.codigo() != null) sensor.setCodigo(dto.codigo());
			if (dto.tipoSensor() != null) sensor.setTipoSensor(dto.tipoSensor());
			if (dto.status() != null) sensor.setStatus(dto.status());

			if (dto.regiaoId() != null) {
				repR.findById(dto.regiaoId())
					.ifPresentOrElse(
						sensor::setRegiao,
						() -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND,
								"Regiao com id " + dto.regiaoId() + " nao encontrada"); });
			}

			repS.save(sensor);
			cacheS.removerCache();
			return ResponseEntity.ok(new SensorDTO(sensor));
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Sensor com id " + id + " nao encontrado");
		}
	}

	@Operation(summary = "Remover sensor e suas leituras", tags = "Sensores")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<SensorDTO> remover(@PathVariable Long id) {
		Optional<Sensor> op = cacheS.findById(id);

		if (op.isPresent()) {
			Sensor sensor = op.get();

			// 1. Deletar leituras do sensor antes
			List<LeituraSensor> leituras = repL.buscarPorSensor(id);
			repL.deleteAll(leituras);

			// 2. Deletar o sensor
			repS.delete(sensor);
			cacheS.removerCache();
			return ResponseEntity.ok(new SensorDTO(sensor));
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Sensor com id " + id + " nao encontrado");
		}
	}
}