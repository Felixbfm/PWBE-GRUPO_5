package br.com.pwbe.conecta_saude.dto;

import br.com.pwbe.conecta_saude.enums.Especialidade;
import br.com.pwbe.conecta_saude.enums.StatusAgendamento;
import java.time.LocalDate;
import java.time.LocalTime;

public record AgendamentoResponseDTO(
        Long id,
        String nomePaciente,
        String nomeProfissional,
        String nomeUnidade,
        Especialidade especialidade,
        LocalDate dataConsulta,
        LocalTime horaInicio,
        LocalTime horaFim,
        StatusAgendamento status,
        String observacao
) {}