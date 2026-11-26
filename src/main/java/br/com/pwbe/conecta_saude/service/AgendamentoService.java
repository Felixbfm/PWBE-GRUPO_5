package br.com.pwbe.conecta_saude.service;

import br.com.pwbe.conecta_saude.dto.AgendamentoRequestDTO;
import br.com.pwbe.conecta_saude.dto.AgendamentoResponseDTO;
import br.com.pwbe.conecta_saude.dto.RemarcarAgendamentoDTO;
import br.com.pwbe.conecta_saude.enums.StatusAgendamento;
import br.com.pwbe.conecta_saude.model.Agendamento;
import br.com.pwbe.conecta_saude.model.HorarioAtendimento;
import br.com.pwbe.conecta_saude.model.UnidadeSaude;
import br.com.pwbe.conecta_saude.model.Usuario;
import br.com.pwbe.conecta_saude.repository.AgendamentoRepository;
import br.com.pwbe.conecta_saude.repository.HorarioAtendimentoRepository;
import br.com.pwbe.conecta_saude.repository.UnidadeSaudeRepository;
import br.com.pwbe.conecta_saude.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final HorarioAtendimentoRepository horarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final UnidadeSaudeRepository unidadeRepository;

    public AgendamentoService(AgendamentoRepository agendamentoRepository,
                              HorarioAtendimentoRepository horarioRepository,
                              UsuarioRepository usuarioRepository,
                              UnidadeSaudeRepository unidadeRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.horarioRepository = horarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.unidadeRepository = unidadeRepository;
    }

    public List<LocalTime> listarHorariosDisponiveis(Long profissionalId, LocalDate data) {
        DayOfWeek diaSemana = data.getDayOfWeek();
        List<HorarioAtendimento> expedientes =
                horarioRepository.findByProfissionalIdAndDiaSemana(profissionalId, diaSemana);

        if (expedientes.isEmpty()) {
            return List.of();
        }

        Set<LocalTime> horariosOcupados = agendamentoRepository
                .findByProfissionalIdAndDataConsultaAndStatusNot(
                        profissionalId, data, StatusAgendamento.CANCELADO)
                .stream()
                .map(Agendamento::getHoraInicio)
                .collect(Collectors.toSet());

        List<LocalTime> disponiveis = new ArrayList<>();
        for (HorarioAtendimento exp : expedientes) {
            LocalTime atual = exp.getHoraInicio();
            while (atual != null && exp.getHoraFim() != null
                    && !atual.plusMinutes(30).isAfter(exp.getHoraFim())) {
                if (!horariosOcupados.contains(atual)) {
                    disponiveis.add(atual);
                }
                atual = atual.plusMinutes(30);
            }
        }
        return disponiveis;
    }

    public AgendamentoResponseDTO agendar(AgendamentoRequestDTO dto) {
        if (dto.getDataConsulta() == null || dto.getHoraInicio() == null) {
            throw new IllegalArgumentException("Data e horário da consulta são obrigatórios.");
        }

        LocalDateTime dataHoraConsulta = LocalDateTime.of(dto.getDataConsulta(), dto.getHoraInicio());
        if (dataHoraConsulta.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Não é possível agendar em uma data/horário passado.");
        }

        boolean ocupado = agendamentoRepository.existsByProfissionalIdAndDataConsultaAndHoraInicioAndStatusNot(
                dto.getProfissionalId(), dto.getDataConsulta(), dto.getHoraInicio(), StatusAgendamento.CANCELADO);

        if (ocupado) {
            throw new IllegalStateException("Este horário já está ocupado para o profissional selecionado.");
        }

        Usuario paciente = usuarioRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado."));
        Usuario profissional = usuarioRepository.findById(dto.getProfissionalId())
                .orElseThrow(() -> new IllegalArgumentException("Profissional não encontrado."));
        UnidadeSaude unidade = unidadeRepository.findById(dto.getUnidadeSaudeId())
                .orElseThrow(() -> new IllegalArgumentException("Unidade de saúde não encontrada."));

        Agendamento agendamento = new Agendamento();
        agendamento.setPaciente(paciente);
        agendamento.setProfissional(profissional);
        agendamento.setUnidadeSaude(unidade);
        agendamento.setDataConsulta(dto.getDataConsulta());
        agendamento.setHoraInicio(dto.getHoraInicio());
        agendamento.setHoraFim(dto.getHoraInicio().plusMinutes(30));
        agendamento.setDataCriacao(LocalDate.now());
        agendamento.setObservacao(dto.getObservacao());
        agendamento.setStatus(StatusAgendamento.AGENDADO);

        return toResponseDTO(agendamentoRepository.save(agendamento));
    }

    public AgendamentoResponseDTO remarcar(Long agendamentoId, RemarcarAgendamentoDTO dto) {
        if (dto.getNovaDataConsulta() == null || dto.getNovaHoraInicio() == null) {
            throw new IllegalArgumentException("Nova data e horário são obrigatórios.");
        }

        Agendamento agendamento = buscarEntidade(agendamentoId);

        if (agendamento.getStatus() == StatusAgendamento.CANCELADO
                || agendamento.getStatus() == StatusAgendamento.CONCLUIDO) {
            throw new IllegalStateException("Não é possível remarcar uma consulta cancelada ou concluída.");
        }

        LocalDateTime novaDataHora = LocalDateTime.of(dto.getNovaDataConsulta(), dto.getNovaHoraInicio());
        if (novaDataHora.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Não é possível remarcar para uma data/horário passado.");
        }

        boolean ocupado = agendamentoRepository.existsByProfissionalIdAndDataConsultaAndHoraInicioAndStatusNot(
                agendamento.getProfissional().getId(),
                dto.getNovaDataConsulta(),
                dto.getNovaHoraInicio(),
                StatusAgendamento.CANCELADO);

        if (ocupado) {
            throw new IllegalStateException("O novo horário escolhido já está ocupado.");
        }

        agendamento.setDataConsulta(dto.getNovaDataConsulta());
        agendamento.setHoraInicio(dto.getNovaHoraInicio());
        agendamento.setHoraFim(dto.getNovaHoraInicio().plusMinutes(30));

        return toResponseDTO(agendamentoRepository.save(agendamento));
    }

    public AgendamentoResponseDTO cancelar(Long agendamentoId) {
        Agendamento agendamento = buscarEntidade(agendamentoId);
        agendamento.setStatus(StatusAgendamento.CANCELADO);
        return toResponseDTO(agendamentoRepository.save(agendamento));
    }

    public AgendamentoResponseDTO concluir(Long agendamentoId) {
        Agendamento agendamento = buscarEntidade(agendamentoId);
        agendamento.setStatus(StatusAgendamento.CONCLUIDO);
        return toResponseDTO(agendamentoRepository.save(agendamento));
    }

    public List<AgendamentoResponseDTO> listarPorPaciente(Long pacienteId) {
        return agendamentoRepository.findByPacienteIdOrderByDataConsultaDescHoraInicioDesc(pacienteId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public List<AgendamentoResponseDTO> listarPorProfissionalEDia(Long profissionalId, LocalDate data) {
        return agendamentoRepository
                .findByProfissionalIdAndDataConsultaOrderByHoraInicioAsc(profissionalId, data)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public List<AgendamentoResponseDTO> listarProximosDoProfissional(Long profissionalId) {
        return agendamentoRepository
                .findByProfissionalIdAndDataConsultaGreaterThanEqualOrderByDataConsultaAscHoraInicioAsc(
                        profissionalId, LocalDate.now())
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public List<AgendamentoResponseDTO> listarPorUnidade(Long unidadeId) {
        return agendamentoRepository.findByUnidadeSaudeIdOrderByDataConsultaAscHoraInicioAsc(unidadeId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    private Agendamento buscarEntidade(Long id) {
        return agendamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado."));
    }

    private AgendamentoResponseDTO toResponseDTO(Agendamento a) {
        return new AgendamentoResponseDTO(
                a.getId(),
                a.getPaciente().getNome(),
                a.getProfissional().getNome(),
                a.getUnidadeSaude().getNome(),
                a.getProfissional().getEspecialidade(),
                a.getDataConsulta(),
                a.getHoraInicio(),
                a.getHoraFim(),
                a.getStatus(),
                a.getObservacao()
        );
    }
}