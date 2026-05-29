package br.com.fiap.aquasafe.model;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Entidade que representa uma leitura registrada por um sensor")
@Entity
@Table(name = "tb_leitura")
public class LeituraSensor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "fk_sensor")
	@NotNull(message = "O sensor e um campo obrigatorio")
	private Sensor sensor;

	@DecimalMin(value = "0.0", message = "O nivel de agua nao pode ser negativo")
	@DecimalMax(value = "100.0", message = "O nivel de agua nao pode ultrapassar 100%")
	@Column(name = "nivel_agua")
	@Schema(description = "Nivel da agua em percentual (0 a 100)", example = "55.0")
	private Double nivelAgua;

	@DecimalMin(value = "0.0", message = "O volume de chuva nao pode ser negativo")
	@Column(name = "volume_chuva")
	@Schema(description = "Volume de chuva em mm", example = "28.3")
	private Double volumeChuva;

	@Column(name = "data_hora")
	@Schema(description = "Data e hora do registro", example = "2026-05-20T10:00:00")
	private LocalDateTime dataHora;

	public void transferirLeitura(LeituraSensor leitura) {
		this.sensor = leitura.getSensor();
		this.nivelAgua = leitura.getNivelAgua();
		this.volumeChuva = leitura.getVolumeChuva();
		this.dataHora = leitura.getDataHora();
	}

	public LeituraSensor() {
	}

	public LeituraSensor(Long id, Sensor sensor, Double nivelAgua,
			Double volumeChuva, LocalDateTime dataHora) {
		this.id = id;
		this.sensor = sensor;
		this.nivelAgua = nivelAgua;
		this.volumeChuva = volumeChuva;
		this.dataHora = dataHora;
	}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public Sensor getSensor() { return sensor; }
	public void setSensor(Sensor sensor) { this.sensor = sensor; }
	public Double getNivelAgua() { return nivelAgua; }
	public void setNivelAgua(Double nivelAgua) { this.nivelAgua = nivelAgua; }
	public Double getVolumeChuva() { return volumeChuva; }
	public void setVolumeChuva(Double volumeChuva) { this.volumeChuva = volumeChuva; }
	public LocalDateTime getDataHora() { return dataHora; }
	public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
}