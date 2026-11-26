package br.com.pwbe.conecta_saude.dto;

import br.com.pwbe.conecta_saude.enums.Especialidade;
import br.com.pwbe.conecta_saude.enums.TipoUsuario;

public class CadastroUsuarioDTO {

    private String nome;
    private String email;
    private String senha;
    private String confirmacaoSenha;
    private TipoUsuario tipo;
    private Especialidade especialidade;
    private Long unidadeSaudeId;

    public CadastroUsuarioDTO() {}

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getConfirmacaoSenha() { return confirmacaoSenha; }
    public void setConfirmacaoSenha(String confirmacaoSenha) { this.confirmacaoSenha = confirmacaoSenha; }

    public TipoUsuario getTipo() { return tipo; }
    public void setTipo(TipoUsuario tipo) { this.tipo = tipo; }

    public Especialidade getEspecialidade() { return especialidade; }
    public void setEspecialidade(Especialidade especialidade) { this.especialidade = especialidade; }

    public Long getUnidadeSaudeId() { return unidadeSaudeId; }
    public void setUnidadeSaudeId(Long unidadeSaudeId) { this.unidadeSaudeId = unidadeSaudeId; }
}