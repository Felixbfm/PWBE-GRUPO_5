package br.com.pwbe.conecta_saude.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class AgendamentoRequestDTO {

    private Long pacienteId;
    private Long unidadeSaudeId;
    private Long profissionalId;
    private LocalDate dataConsulta;
    private LocalTime horaInicio;
    private String observacao;

    public AgendamentoRequestDTO() {}

    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }

    public Long getUnidadeSaudeId() { return unidadeSaudeId; }
    public void setUnidadeSaudeId(Long unidadeSaudeId) { this.unidadeSaudeId = unidadeSaudeId; }

    public Long getProfissionalId() { return profissionalId; }
    public void setProfissionalId(Long profissionalId) { this.profissionalId = profissionalId; }

    public LocalDate getDataConsulta() { return dataConsulta; }
    public void setDataConsulta(LocalDate dataConsulta) { this.dataConsulta = dataConsulta; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
}