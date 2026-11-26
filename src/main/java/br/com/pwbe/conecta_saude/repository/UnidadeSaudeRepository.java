package br.com.pwbe.conecta_saude.repository;

import br.com.pwbe.conecta_saude.model.UnidadeSaude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnidadeSaudeRepository extends JpaRepository<UnidadeSaude, Long> {
    List<UnidadeSaude> findByNomeContainingIgnoreCase(String nome);
}