package br.com.fiap.aquasafe.dto;

import br.com.fiap.aquasafe.model.NivelRiscoEnum;

public record RegiaoRequestDTO(
		String nome,
		String cidade,
		String estado,
		NivelRiscoEnum nivelRiscoAtual,
		Double latitude,
		Double longitude) {
}