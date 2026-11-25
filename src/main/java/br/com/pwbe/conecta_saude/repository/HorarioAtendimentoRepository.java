package br.com.pwbe.conecta_saude.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.pwbe.conecta_saude.model.HorarioAtendimento;

import java.util.List;

@Repository
public interface HorarioAtendimentoRepository extends JpaRepository<HorarioAtendimento,Long>{

}
