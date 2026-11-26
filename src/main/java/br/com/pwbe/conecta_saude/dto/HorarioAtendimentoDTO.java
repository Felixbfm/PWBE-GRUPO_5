package br.com.pwbe.conecta_saude.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class HorarioAtendimentoDTO {

    private Long id;
    private Long profissionalId;
    private Long unidadeSaudeId;
    private DayOfWeek diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFim;

    public HorarioAtendimentoDTO() {}

    public HorarioAtendimentoDTO(Long id, Long profissionalId, Long unidadeSaudeId,
                                 DayOfWeek diaSemana, LocalTime horaInicio, LocalTime horaFim) {
        this.id = id;
        this.profissionalId = profissionalId;
        this.unidadeSaudeId = unidadeSaudeId;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProfissionalId() { return profissionalId; }
    public void setProfissionalId(Long profissionalId) { this.profissionalId = profissionalId; }

    public Long getUnidadeSaudeId() { return unidadeSaudeId; }
    public void setUnidadeSaudeId(Long unidadeSaudeId) { this.unidadeSaudeId = unidadeSaudeId; }

    public DayOfWeek getDiaSemana() { return diaSemana; }
    public void setDiaSemana(DayOfWeek diaSemana) { this.diaSemana = diaSemana; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFim() { return horaFim; }
    public void setHoraFim(LocalTime horaFim) { this.horaFim = horaFim; }
}