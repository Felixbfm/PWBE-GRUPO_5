package br.com.pwbe.conecta_saude.service;

import br.com.pwbe.conecta_saude.dto.CadastroUsuarioDTO;
import br.com.pwbe.conecta_saude.dto.LoginDTO;
import br.com.pwbe.conecta_saude.dto.UsuarioResponseDTO;
import br.com.pwbe.conecta_saude.enums.Especialidade;
import br.com.pwbe.conecta_saude.enums.TipoUsuario;
import br.com.pwbe.conecta_saude.model.Usuario;
import br.com.pwbe.conecta_saude.repository.UnidadeSaudeRepository;
import br.com.pwbe.conecta_saude.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UnidadeSaudeRepository unidadeSaudeRepository;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          UnidadeSaudeRepository unidadeSaudeRepository) {
        this.usuarioRepository = usuarioRepository;
        this.unidadeSaudeRepository = unidadeSaudeRepository;
    }

    public UsuarioResponseDTO cadastrar(CadastroUsuarioDTO dto) {
        if (dto.getSenha() == null || !dto.getSenha().equals(dto.getConfirmacaoSenha())) {
            throw new IllegalArgumentException("A senha e a confirmação de senha não coincidem.");
        }
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com este e-mail.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setTipo(dto.getTipo() != null ? dto.getTipo() : TipoUsuario.PACIENTE);
        usuario.setEspecialidade(dto.getEspecialidade());

        if (dto.getUnidadeSaudeId() != null) {
            var unidade = unidadeSaudeRepository.findById(dto.getUnidadeSaudeId())
                    .orElseThrow(() -> new IllegalArgumentException("Unidade de saúde não encontrada."));
            usuario.setUnidadeSaude(unidade);
        }

        Usuario salvo = usuarioRepository.save(usuario);
        return toResponseDTO(salvo);
    }

    public UsuarioResponseDTO autenticar(LoginDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("E-mail ou senha inválidos."));

        if (!usuario.getSenha().equals(dto.getSenha())) {
            throw new IllegalArgumentException("E-mail ou senha inválidos.");
        }
        return toResponseDTO(usuario);
    }

    public List<UsuarioResponseDTO> listarProfissionaisPorUnidadeEEspecialidade(Long unidadeId,
                                                                                Especialidade especialidade) {
        return usuarioRepository
                .findByUnidadeSaudeIdAndEspecialidade(unidadeId, especialidade)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public UsuarioResponseDTO toResponseDTO(Usuario u) {
        Long unidadeId = (u.getUnidadeSaude() != null) ? u.getUnidadeSaude().getId() : null;
        return new UsuarioResponseDTO(
                u.getId(),
                u.getNome(),
                u.getEmail(),
                u.getTipo(),
                u.getEspecialidade(),
                unidadeId
        );
    }
}