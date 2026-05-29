package br.com.fiap.aquasafe.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Schema(description = "Entidade que representa um sensor fisico instalado em uma regiao monitorada")
@Entity
@Table(name = "tb_sensor")
public class Sensor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "O codigo do sensor e um campo obrigatorio")
	@Size(min = 3, max = 20, message = "O codigo deve ter ao menos 3 e no maximo 20 caracteres")
	@Column(name = "codigo", unique = true)
	@Schema(description = "Codigo unico do sensor", example = "SNS-001")
	private String codigo;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_sensor")
	@Schema(description = "Tipo do sensor (NIVEL_AGUA, CHUVA, PRESSAO)", example = "NIVEL_AGUA")
	private TipoSensorEnum tipoSensor;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	@Schema(description = "Status operacional (ATIVO, INATIVO, MANUTENCAO)", example = "ATIVO")
	private StatusSensorEnum status;

	@ManyToOne
	@JoinColumn(name = "fk_regiao")
	private RegiaoMonitorada regiao;

	@JsonIgnore
	@OneToMany(mappedBy = "sensor")
	private List<LeituraSensor> leituras;

	public void transferirSensor(Sensor sensor) {
		this.codigo = sensor.getCodigo();
		this.tipoSensor = sensor.getTipoSensor();
		this.status = sensor.getStatus();
		this.regiao = sensor.getRegiao();
	}

	public Sensor() {
	}

	public Sensor(Long id, String codigo, TipoSensorEnum tipoSensor,
			StatusSensorEnum status, RegiaoMonitorada regiao) {
		this.id = id;
		this.codigo = codigo;
		this.tipoSensor = tipoSensor;
		this.status = status;
		this.regiao = regiao;
	}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getCodigo() { return codigo; }
	public void setCodigo(String codigo) { this.codigo = codigo; }
	public TipoSensorEnum getTipoSensor() { return tipoSensor; }
	public void setTipoSensor(TipoSensorEnum tipoSensor) { this.tipoSensor = tipoSensor; }
	public StatusSensorEnum getStatus() { return status; }
	public void setStatus(StatusSensorEnum status) { this.status = status; }
	public RegiaoMonitorada getRegiao() { return regiao; }
	public void setRegiao(RegiaoMonitorada regiao) { this.regiao = regiao; }
	public List<LeituraSensor> getLeituras() { return leituras; }
	public void setLeituras(List<LeituraSensor> leituras) { this.leituras = leituras; }
}