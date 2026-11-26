package br.com.pwbe.conecta_saude.repository;

import br.com.pwbe.conecta_saude.enums.Especialidade;
import br.com.pwbe.conecta_saude.enums.TipoUsuario;
import br.com.pwbe.conecta_saude.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByNomeContaining(String nome);
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
    List<Usuario> findByUnidadeSaudeIdAndTipo(Long unidadeId, TipoUsuario tipo);
    List<Usuario> findByUnidadeSaudeIdAndEspecialidade(Long unidadeId, Especialidade especialidade);
}