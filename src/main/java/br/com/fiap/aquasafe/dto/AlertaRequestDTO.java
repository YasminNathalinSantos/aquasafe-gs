package br.com.fiap.aquasafe.dto;

import java.time.LocalDateTime;

import br.com.fiap.aquasafe.model.NivelRiscoEnum;
import br.com.fiap.aquasafe.model.TipoAlertaEnum;

public record AlertaRequestDTO(
		Long regiaoId,
		TipoAlertaEnum tipoAlerta,
		NivelRiscoEnum nivelRisco,
		String mensagem,
		LocalDateTime dataHora,
		Integer ativo) {
}