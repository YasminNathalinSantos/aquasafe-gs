package br.com.fiap.aquasafe.dto;

import java.time.LocalDateTime;

import br.com.fiap.aquasafe.model.Alerta;
import br.com.fiap.aquasafe.model.NivelRiscoEnum;
import br.com.fiap.aquasafe.model.TipoAlertaEnum;

public record AlertaDTO(
		Long id,
		Long regiaoId,
		String regiaoNome,
		TipoAlertaEnum tipoAlerta,
		NivelRiscoEnum nivelRisco,
		String mensagem,
		LocalDateTime dataHora,
		Integer ativo) {

	public AlertaDTO(Alerta alerta) {
		this(alerta.getId(),
				alerta.getRegiao() != null ? alerta.getRegiao().getId() : null,
				alerta.getRegiao() != null ? alerta.getRegiao().getNome() : null,
				alerta.getTipoAlerta(),
				alerta.getNivelRisco(),
				alerta.getMensagem(),
				alerta.getDataHora(),
				alerta.getAtivo());
	}
}
