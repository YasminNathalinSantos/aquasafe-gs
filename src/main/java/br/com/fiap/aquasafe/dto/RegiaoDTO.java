package br.com.fiap.aquasafe.dto;

import br.com.fiap.aquasafe.model.NivelRiscoEnum;
import br.com.fiap.aquasafe.model.RegiaoMonitorada;

public record RegiaoDTO(
		Long id,
		String nome,
		String cidade,
		String estado,
		NivelRiscoEnum nivelRiscoAtual,
		Double latitude,
		Double longitude) {

	public RegiaoDTO(RegiaoMonitorada regiao) {
		this(regiao.getId(),
				regiao.getNome(),
				regiao.getCidade(),
				regiao.getEstado(),
				regiao.getNivelRiscoAtual(),
				regiao.getLatitude(),
				regiao.getLongitude());
	}
}
