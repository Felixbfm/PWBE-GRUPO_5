package br.com.pwbe.conecta_saude.repository;

import br.com.pwbe.conecta_saude.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.pwbe.conecta_saude.model.Agendamento;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento,Long>{
}
