package br.com.pwbe.conecta_saude.dto;

import br.com.pwbe.conecta_saude.enums.Especialidade;
import br.com.pwbe.conecta_saude.enums.TipoUsuario;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        TipoUsuario tipo,
        Especialidade especialidade,
        Long unidadeSaudeId
) {}