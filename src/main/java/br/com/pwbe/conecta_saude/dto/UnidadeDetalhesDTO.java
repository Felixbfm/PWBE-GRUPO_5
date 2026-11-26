package br.com.pwbe.conecta_saude.dto;

import br.com.pwbe.conecta_saude.enums.Especialidade;
import br.com.pwbe.conecta_saude.enums.StatusLotacao;
import java.time.LocalDateTime;
import java.util.List;

public record UnidadeDetalhesDTO(
        Long id,
        String nome,
        String endereco,
        String telefone,
        Integer capacidadeEspera,
        Integer quantidadePessoasEsperando,
        StatusLotacao statusLotacao,
        LocalDateTime ultimaAtualizacao,
        List<Especialidade> especialidades,
        List<UsuarioResponseDTO> profissionais
) {}