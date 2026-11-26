package br.com.pwbe.conecta_saude.repository;

import br.com.pwbe.conecta_saude.model.HorarioAtendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface HorarioAtendimentoRepository extends JpaRepository<HorarioAtendimento, Long> {
    List<HorarioAtendimento> findByProfissionalId(Long profissionalId);
    List<HorarioAtendimento> findByProfissionalIdAndDiaSemana(Long profissionalId, DayOfWeek diaSemana);
}