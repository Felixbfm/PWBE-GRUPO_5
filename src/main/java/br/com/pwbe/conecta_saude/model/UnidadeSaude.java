package br.com.pwbe.conecta_saude.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class UnidadeSaude {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String endereco;
    private String telefone;
    private Integer capacidadeEspera;
    private Integer quantidadePessoasEsperando;
    private LocalDateTime ultimaAtualizacao;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public String getEndereco() {return endereco;}
    public void setEndereco(String endereco) {this.endereco = endereco;}

    public String getTelefone() {return telefone;}
    public void setTelefone(String telefone) {this.telefone = telefone;}

    public Integer getCapacidadeEspera() {return capacidadeEspera;}
    public void setCapacidadeEspera(Integer capacidadeEspera) {this.capacidadeEspera = capacidadeEspera;}

    public Integer getQuantidadePessoasEsperando() {return quantidadePessoasEsperando;}
    public void setQuantidadePessoasEsperando(Integer quantidadePessoasEsperando) {this.quantidadePessoasEsperando = quantidadePessoasEsperando;}

    public LocalDateTime getUltimaAtualizacao() {return ultimaAtualizacao;}
    public void setUltimaAtualizacao(LocalDateTime ultimaAtualizacao) {this.ultimaAtualizacao = ultimaAtualizacao;}
}
