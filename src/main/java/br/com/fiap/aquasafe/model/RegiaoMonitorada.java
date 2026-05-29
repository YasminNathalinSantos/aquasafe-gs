package br.com.fiap.aquasafe.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Schema(description = "Entidade que representa uma regiao geografica monitorada pelo AquaSafe")
@Entity
@Table(name = "tb_regiao")
public class RegiaoMonitorada {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "O nome da regiao e um campo obrigatorio")
	@Size(min = 2, max = 100)
	@Column(name = "nome")
	private String nome;

	@NotEmpty(message = "A cidade e um campo obrigatorio")
	@Column(name = "cidade")
	private String cidade;

	@NotEmpty(message = "O estado e um campo obrigatorio")
	@Column(name = "estado")
	private String estado;

	@Enumerated(EnumType.STRING)
	@Column(name = "nivel_risco_atual")
	private NivelRiscoEnum nivelRiscoAtual;

	@Column(name = "latitude")
	private Double latitude;

	@Column(name = "longitude")
	private Double longitude;

	@OneToMany(mappedBy = "regiao",
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	@JsonIgnore
	private List<Sensor> sensores = new ArrayList<>();

	@OneToMany(mappedBy = "regiao",
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	@JsonIgnore
	private List<Alerta> alertas = new ArrayList<>();

	public void transferirRegiao(RegiaoMonitorada regiao) {
		this.nome = regiao.getNome();
		this.cidade = regiao.getCidade();
		this.estado = regiao.getEstado();
		this.nivelRiscoAtual = regiao.getNivelRiscoAtual();
		this.latitude = regiao.getLatitude();
		this.longitude = regiao.getLongitude();
	}

	public RegiaoMonitorada() {
	}

	public RegiaoMonitorada(Long id, String nome, String cidade,
			String estado, NivelRiscoEnum nivelRiscoAtual,
			Double latitude, Double longitude) {
		this.id = id;
		this.nome = nome;
		this.cidade = cidade;
		this.estado = estado;
		this.nivelRiscoAtual = nivelRiscoAtual;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public NivelRiscoEnum getNivelRiscoAtual() {
		return nivelRiscoAtual;
	}

	public void setNivelRiscoAtual(NivelRiscoEnum nivelRiscoAtual) {
		this.nivelRiscoAtual = nivelRiscoAtual;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public List<Sensor> getSensores() {
		return sensores;
	}

	public void setSensores(List<Sensor> sensores) {
		this.sensores = sensores;
	}

	public List<Alerta> getAlertas() {
		return alertas;
	}

	public void setAlertas(List<Alerta> alertas) {
		this.alertas = alertas;
	}
}