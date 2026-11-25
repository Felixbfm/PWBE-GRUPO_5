package br.com.pwbe.conecta_saude.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.pwbe.conecta_saude.model.Usuario;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long>{
    //Métodos de procura personalizados:
    List<Usuario> findByNomeContaining(String nome);
}
