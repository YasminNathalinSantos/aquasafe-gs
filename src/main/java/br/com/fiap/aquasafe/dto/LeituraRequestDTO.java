package br.com.fiap.aquasafe.dto;

import java.time.LocalDateTime;

public record LeituraRequestDTO(
		Long sensorId,
		Double nivelAgua,
		Double volumeChuva,
		LocalDateTime dataHora) {
}