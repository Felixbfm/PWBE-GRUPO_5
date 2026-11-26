package br.com.pwbe.conecta_saude.dto;

public class AtualizarLotacaoDTO {

    private Integer quantidadePessoasEsperando;

    public AtualizarLotacaoDTO() {}

    public Integer getQuantidadePessoasEsperando() { return quantidadePessoasEsperando; }
    public void setQuantidadePessoasEsperando(Integer quantidadePessoasEsperando) {
        this.quantidadePessoasEsperando = quantidadePessoasEsperando;
    }
}