package br.com.pwbe.conecta_saude.repository;

import br.com.pwbe.conecta_saude.enums.StatusAgendamento;
import br.com.pwbe.conecta_saude.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByProfissionalIdAndDataConsultaAndStatusNot(
            Long profissionalId, LocalDate dataConsulta, StatusAgendamento status);
    boolean existsByProfissionalIdAndDataConsultaAndHoraInicioAndStatusNot(
            Long profissionalId, LocalDate dataConsulta, LocalTime horaInicio, StatusAgendamento status);
    List<Agendamento> findByPacienteIdOrderByDataConsultaDescHoraInicioDesc(Long pacienteId);
    List<Agendamento> findByProfissionalIdAndDataConsultaOrderByHoraInicioAsc(
            Long profissionalId, LocalDate dataConsulta);
    List<Agendamento> findByProfissionalIdAndDataConsultaGreaterThanEqualOrderByDataConsultaAscHoraInicioAsc(
            Long profissionalId, LocalDate dataConsulta);
    List<Agendamento> findByUnidadeSaudeIdOrderByDataConsultaAscHoraInicioAsc(Long unidadeId);
}