package br.com.fiap.aquasafe.model;

import java.time.LocalDateTime;

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
import jakarta.persistence.Table;

@Schema(description = "Entidade que representa um alerta de risco")
@Entity
@Table(name = "tb_alerta")
public class Alerta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "fk_regiao")
	@JsonIgnore
	private RegiaoMonitorada regiao;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_alerta")
	private TipoAlertaEnum tipoAlerta;

	@Enumerated(EnumType.STRING)
	@Column(name = "nivel_risco")
	private NivelRiscoEnum nivelRisco;

	@Column(name = "mensagem")
	private String mensagem;

	@Column(name = "data_hora")
	private LocalDateTime dataHora;

	@Column(name = "ativo")
	private Integer ativo;

	public void transferirAlerta(Alerta alerta) {
		this.regiao = alerta.getRegiao();
		this.tipoAlerta = alerta.getTipoAlerta();
		this.nivelRisco = alerta.getNivelRisco();
		this.mensagem = alerta.getMensagem();
		this.dataHora = alerta.getDataHora();
		this.ativo = alerta.getAtivo();
	}

	public Alerta() {
	}

	public Alerta(Long id, RegiaoMonitorada regiao,
			TipoAlertaEnum tipoAlerta, NivelRiscoEnum nivelRisco,
			String mensagem, LocalDateTime dataHora, Integer ativo) {
		this.id = id;
		this.regiao = regiao;
		this.tipoAlerta = tipoAlerta;
		this.nivelRisco = nivelRisco;
		this.mensagem = mensagem;
		this.dataHora = dataHora;
		this.ativo = ativo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RegiaoMonitorada getRegiao() {
		return regiao;
	}

	public void setRegiao(RegiaoMonitorada regiao) {
		this.regiao = regiao;
	}

	public TipoAlertaEnum getTipoAlerta() {
		return tipoAlerta;
	}

	public void setTipoAlerta(TipoAlertaEnum tipoAlerta) {
		this.tipoAlerta = tipoAlerta;
	}

	public NivelRiscoEnum getNivelRisco() {
		return nivelRisco;
	}

	public void setNivelRisco(NivelRiscoEnum nivelRisco) {
		this.nivelRisco = nivelRisco;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public Integer getAtivo() {
		return ativo;
	}

	public void setAtivo(Integer ativo) {
		this.ativo = ativo;
	}
}