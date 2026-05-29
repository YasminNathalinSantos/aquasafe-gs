package br.com.fiap.aquasafe.dto;

import java.time.LocalDateTime;

import br.com.fiap.aquasafe.model.LeituraSensor;

public record LeituraDTO(
		Long id,
		Long sensorId,
		String sensorCodigo,
		Double nivelAgua,
		Double volumeChuva,
		LocalDateTime dataHora,
		String nivelRiscoCalculado) {

	public LeituraDTO(LeituraSensor leitura) {
		this(leitura.getId(),
				leitura.getSensor() != null ? leitura.getSensor().getId() : null,
				leitura.getSensor() != null ? leitura.getSensor().getCodigo() : null,
				leitura.getNivelAgua(),
				leitura.getVolumeChuva(),
				leitura.getDataHora(),
				calcularRisco(leitura.getNivelAgua()));
	}

	private static String calcularRisco(Double nivel) {
		if (nivel == null) return "INDEFINIDO";
		if (nivel < 40.0) return "BAIXO";
		if (nivel < 70.0) return "MEDIO";
		if (nivel < 85.0) return "ALTO";
		return "CRITICO";
	}
}
