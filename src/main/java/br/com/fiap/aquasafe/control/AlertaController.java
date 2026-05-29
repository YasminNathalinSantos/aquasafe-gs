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

import br.com.fiap.aquasafe.dto.AlertaDTO;
import br.com.fiap.aquasafe.dto.AlertaRequestDTO;
import br.com.fiap.aquasafe.model.Alerta;
import br.com.fiap.aquasafe.projection.AlertaProjection;
import br.com.fiap.aquasafe.repository.AlertaRepository;
import br.com.fiap.aquasafe.repository.RegiaoRepository;
import br.com.fiap.aquasafe.service.AlertaCachingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Alertas", description = "Endpoints para gestao de alertas de risco")
@RestController
@RequestMapping(value = "/alertas")
public class AlertaController {

	@Autowired
	private AlertaRepository repA;

	@Autowired
	private RegiaoRepository repR;

	@Autowired
	private AlertaCachingService cacheA;

	@Operation(summary = "Listar todos os alertas (caching)", tags = "Alertas")
	@GetMapping(value = "/todos")
	public List<Alerta> listarTodos() {
		return cacheA.findAll();
	}

	@Operation(summary = "Listar alertas ativos (caching)", tags = "Alertas")
	@GetMapping(value = "/ativos")
	public List<Alerta> listarAtivos() {
		return cacheA.buscarAlertasAtivos();
	}

	@Operation(summary = "Resumo de alertas ativos (projection)", tags = "Alertas")
	@GetMapping(value = "/ativos/resumo")
	public List<AlertaProjection> resumoAlertasAtivos() {
		return cacheA.buscarResumoAlertasAtivos();
	}

	@Operation(summary = "Buscar alerta por ID com HATEOAS", tags = "Alertas")
	@GetMapping(value = "/{id}")
	public ResponseEntity<EntityModel<AlertaDTO>> buscarPorId(@PathVariable Long id) {
		Optional<Alerta> op = cacheA.findById(id);

		if (op.isPresent()) {
			AlertaDTO dto = new AlertaDTO(op.get());

			Link selfLink = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder.methodOn(AlertaController.class).buscarPorId(id))
					.withSelfRel();
			Link ativosLink = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder.methodOn(AlertaController.class).listarAtivos())
					.withRel("alertas-ativos");

			return ResponseEntity.ok(EntityModel.of(dto, selfLink, ativosLink));
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Alerta com id " + id + " nao encontrado");
		}
	}

	@Operation(summary = "Gerar novo alerta", tags = "Alertas")
	@PostMapping(value = "/gerar")
	public ResponseEntity<AlertaDTO> gerar(@RequestBody AlertaRequestDTO dto) {
		Alerta alerta = new Alerta();

		if (dto.regiaoId() != null) {
			repR.findById(dto.regiaoId())
				.ifPresentOrElse(
					alerta::setRegiao,
					() -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND,
							"Regiao com id " + dto.regiaoId() + " nao encontrada"); });
		}

		if (dto.tipoAlerta() != null) alerta.setTipoAlerta(dto.tipoAlerta());
		if (dto.nivelRisco() != null) alerta.setNivelRisco(dto.nivelRisco());
		if (dto.mensagem() != null) alerta.setMensagem(dto.mensagem());
		if (dto.ativo() != null) alerta.setAtivo(dto.ativo());
		alerta.setDataHora(dto.dataHora() != null ? dto.dataHora() : LocalDateTime.now());

		repA.save(alerta);
		cacheA.removerCache();
		return ResponseEntity.status(HttpStatus.CREATED).body(new AlertaDTO(alerta));
	}

	@Operation(summary = "Encerrar alerta (ativo = 0)", tags = "Alertas")
	@PutMapping(value = "/{id}/encerrar")
	public ResponseEntity<AlertaDTO> encerrar(@PathVariable Long id) {
		Optional<Alerta> op = cacheA.findById(id);

		if (op.isPresent()) {
			Alerta alerta = op.get();
			alerta.setAtivo(0);
			repA.save(alerta);
			cacheA.removerCache();
			return ResponseEntity.ok(new AlertaDTO(alerta));
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Alerta com id " + id + " nao encontrado");
		}
	}

	@Operation(summary = "Atualizar alerta (passa so o que quer mudar)", tags = "Alertas")
	@PutMapping(value = "/{id}")
	public ResponseEntity<AlertaDTO> atualizar(
			@PathVariable Long id,
			@RequestBody AlertaRequestDTO dto) {

		Optional<Alerta> op = cacheA.findById(id);

		if (op.isPresent()) {
			Alerta alerta = op.get();

			if (dto.regiaoId() != null) {
				repR.findById(dto.regiaoId())
					.ifPresentOrElse(
						alerta::setRegiao,
						() -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND,
								"Regiao com id " + dto.regiaoId() + " nao encontrada"); });
			}

			if (dto.tipoAlerta() != null) alerta.setTipoAlerta(dto.tipoAlerta());
			if (dto.nivelRisco() != null) alerta.setNivelRisco(dto.nivelRisco());
			if (dto.mensagem() != null) alerta.setMensagem(dto.mensagem());
			if (dto.dataHora() != null) alerta.setDataHora(dto.dataHora());
			if (dto.ativo() != null) alerta.setAtivo(dto.ativo());

			repA.save(alerta);
			cacheA.removerCache();
			return ResponseEntity.ok(new AlertaDTO(alerta));
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Alerta com id " + id + " nao encontrado");
		}
	}

	@Operation(summary = "Remover alerta", tags = "Alertas")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<AlertaDTO> remover(@PathVariable Long id) {
		Optional<Alerta> op = cacheA.findById(id);

		if (op.isPresent()) {
			repA.delete(op.get());
			cacheA.removerCache();
			return ResponseEntity.ok(new AlertaDTO(op.get()));
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Alerta com id " + id + " nao encontrado");
		}
	}
}