package br.com.pwbe.conecta_saude.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class RemarcarAgendamentoDTO {

    private LocalDate novaDataConsulta;
    private LocalTime novaHoraInicio;

    public RemarcarAgendamentoDTO() {}

    public LocalDate getNovaDataConsulta() { return novaDataConsulta; }
    public void setNovaDataConsulta(LocalDate novaDataConsulta) { this.novaDataConsulta = novaDataConsulta; }

    public LocalTime getNovaHoraInicio() { return novaHoraInicio; }
    public void setNovaHoraInicio(LocalTime novaHoraInicio) { this.novaHoraInicio = novaHoraInicio; }
}