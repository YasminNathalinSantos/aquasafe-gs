package br.com.fiap.aquasafe.dto;

import br.com.fiap.aquasafe.model.StatusSensorEnum;
import br.com.fiap.aquasafe.model.TipoSensorEnum;

public record SensorRequestDTO(
        String codigo,
        TipoSensorEnum tipoSensor,
        StatusSensorEnum status,
        Long regiaoId) {
}