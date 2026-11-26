package br.com.pwbe.conecta_saude.dto;

import br.com.pwbe.conecta_saude.enums.StatusLotacao;
import java.time.LocalDateTime;

public record UnidadeResumoDTO(
        Long id,
        String nome,
        String endereco,
        Integer capacidadeEspera,
        Integer quantidadePessoasEsperando,
        StatusLotacao statusLotacao,
        LocalDateTime ultimaAtualizacao
) {}