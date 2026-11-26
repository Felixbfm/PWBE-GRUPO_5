package br.com.pwbe.conecta_saude.service;

import br.com.pwbe.conecta_saude.dto.AtualizarLotacaoDTO;
import br.com.pwbe.conecta_saude.dto.UnidadeDetalhesDTO;
import br.com.pwbe.conecta_saude.dto.UnidadeResumoDTO;
import br.com.pwbe.conecta_saude.dto.UsuarioResponseDTO;
import br.com.pwbe.conecta_saude.enums.Especialidade;
import br.com.pwbe.conecta_saude.enums.StatusLotacao;
import br.com.pwbe.conecta_saude.enums.TipoUsuario;
import br.com.pwbe.conecta_saude.model.UnidadeSaude;
import br.com.pwbe.conecta_saude.model.Usuario;
import br.com.pwbe.conecta_saude.repository.UnidadeSaudeRepository;
import br.com.pwbe.conecta_saude.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class UnidadeSaudeService {

    private final UnidadeSaudeRepository unidadeRepository;
    private final UsuarioRepository usuarioRepository;

    public UnidadeSaudeService(UnidadeSaudeRepository unidadeRepository,
                               UsuarioRepository usuarioRepository) {
        this.unidadeRepository = unidadeRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<UnidadeResumoDTO> listar(String termoPesquisa) {
        List<UnidadeSaude> unidades = (termoPesquisa == null || termoPesquisa.isBlank())
                ? unidadeRepository.findAll()
                : unidadeRepository.findByNomeContainingIgnoreCase(termoPesquisa);

        return unidades.stream().map(this::toResumoDTO).toList();
    }

    public UnidadeDetalhesDTO buscarDetalhes(Long id) {
        UnidadeSaude unidade = buscarEntidade(id);
        List<Usuario> profissionais =
                usuarioRepository.findByUnidadeSaudeIdAndTipo(id, TipoUsuario.PROFISSIONAL);

        List<Especialidade> especialidades = profissionais.stream()
                .map(Usuario::getEspecialidade)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        List<UsuarioResponseDTO> profissionaisDTO = profissionais.stream()
                .map(p -> new UsuarioResponseDTO(
                        p.getId(),
                        p.getNome(),
                        p.getEmail(),
                        p.getTipo(),
                        p.getEspecialidade(),
                        id))
                .toList();

        return new UnidadeDetalhesDTO(
                unidade.getId(),
                unidade.getNome(),
                unidade.getEndereco(),
                unidade.getTelefone(),
                unidade.getCapacidadeEspera(),
                unidade.getQuantidadePessoasEsperando(),
                calcularStatusLotacao(unidade),
                unidade.getUltimaAtualizacao(),
                especialidades,
                profissionaisDTO
        );
    }

    public UnidadeResumoDTO atualizarLotacao(Long unidadeId, AtualizarLotacaoDTO dto) {
        if (dto.getQuantidadePessoasEsperando() == null || dto.getQuantidadePessoasEsperando() < 0) {
            throw new IllegalArgumentException("A quantidade de pessoas aguardando não pode ser negativa.");
        }

        UnidadeSaude unidade = buscarEntidade(unidadeId);
        unidade.setQuantidadePessoasEsperando(dto.getQuantidadePessoasEsperando());
        unidade.setUltimaAtualizacao(LocalDateTime.now());

        return toResumoDTO(unidadeRepository.save(unidade));
    }

    public StatusLotacao calcularStatusLotacao(UnidadeSaude unidade) {
        Integer esperando = unidade.getQuantidadePessoasEsperando();
        Integer capacidade = unidade.getCapacidadeEspera();

        if (esperando == null || capacidade == null || capacidade <= 0) {
            return StatusLotacao.DESCONHECIDO;
        }

        double taxa = (double) esperando / capacidade;
        if (taxa <= 0.35) return StatusLotacao.TRANQUILO;
        if (taxa <= 0.70) return StatusLotacao.MODERADO;
        if (taxa <= 1.00) return StatusLotacao.CHEIO;
        return StatusLotacao.SUPERLOTADO;
    }

    private UnidadeSaude buscarEntidade(Long id) {
        return unidadeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Unidade de saúde não encontrada."));
    }

    private UnidadeResumoDTO toResumoDTO(UnidadeSaude u) {
        return new UnidadeResumoDTO(
                u.getId(),
                u.getNome(),
                u.getEndereco(),
                u.getCapacidadeEspera(),
                u.getQuantidadePessoasEsperando(),
                calcularStatusLotacao(u),
                u.getUltimaAtualizacao()
        );
    }
}