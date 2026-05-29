package br.com.fiap.aquasafe.dto;

import br.com.fiap.aquasafe.model.Sensor;
import br.com.fiap.aquasafe.model.StatusSensorEnum;
import br.com.fiap.aquasafe.model.TipoSensorEnum;

public record SensorDTO(
		Long id,
		String codigo,
		TipoSensorEnum tipoSensor,
		StatusSensorEnum status,
		Long regiaoId,
		String regiaoNome) {

	public SensorDTO(Sensor sensor) {
		this(sensor.getId(),
				sensor.getCodigo(),
				sensor.getTipoSensor(),
				sensor.getStatus(),
				sensor.getRegiao() != null ? sensor.getRegiao().getId() : null,
				sensor.getRegiao() != null ? sensor.getRegiao().getNome() : null);
	}
}
