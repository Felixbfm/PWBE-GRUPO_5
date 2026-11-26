package br.com.pwbe.conecta_saude.service;

import br.com.pwbe.conecta_saude.dto.HorarioAtendimentoDTO;
import br.com.pwbe.conecta_saude.model.HorarioAtendimento;
import br.com.pwbe.conecta_saude.model.UnidadeSaude;
import br.com.pwbe.conecta_saude.model.Usuario;
import br.com.pwbe.conecta_saude.repository.HorarioAtendimentoRepository;
import br.com.pwbe.conecta_saude.repository.UnidadeSaudeRepository;
import br.com.pwbe.conecta_saude.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HorarioAtendimentoService {

    private final HorarioAtendimentoRepository horarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final UnidadeSaudeRepository unidadeRepository;

    public HorarioAtendimentoService(HorarioAtendimentoRepository horarioRepository,
                                     UsuarioRepository usuarioRepository,
                                     UnidadeSaudeRepository unidadeRepository) {
        this.horarioRepository = horarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.unidadeRepository = unidadeRepository;
    }

    public HorarioAtendimentoDTO cadastrar(HorarioAtendimentoDTO dto) {
        validarIntervalo(dto);

        Usuario profissional = usuarioRepository.findById(dto.getProfissionalId())
                .orElseThrow(() -> new IllegalArgumentException("Profissional não encontrado."));

        UnidadeSaude unidade = resolverUnidade(dto.getUnidadeSaudeId(), profissional);

        HorarioAtendimento horario = new HorarioAtendimento();
        horario.setProfissional(profissional);
        horario.setUnidadeSaude(unidade);
        horario.setDiaSemana(dto.getDiaSemana());
        horario.setHoraInicio(dto.getHoraInicio());
        horario.setHoraFim(dto.getHoraFim());

        return toDTO(horarioRepository.save(horario));
    }

    public HorarioAtendimentoDTO editar(Long id, HorarioAtendimentoDTO dto) {
        validarIntervalo(dto);

        HorarioAtendimento horario = horarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Horário não encontrado."));

        horario.setDiaSemana(dto.getDiaSemana());
        horario.setHoraInicio(dto.getHoraInicio());
        horario.setHoraFim(dto.getHoraFim());

        if (dto.getUnidadeSaudeId() != null) {
            UnidadeSaude unidade = unidadeRepository.findById(dto.getUnidadeSaudeId())
                    .orElseThrow(() -> new IllegalArgumentException("Unidade de saúde não encontrada."));
            horario.setUnidadeSaude(unidade);
        }

        return toDTO(horarioRepository.save(horario));
    }

    public List<HorarioAtendimentoDTO> listarPorProfissional(Long profissionalId) {
        return horarioRepository.findByProfissionalId(profissionalId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public void excluir(Long id) {
        if (!horarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Horário não encontrado.");
        }
        horarioRepository.deleteById(id);
    }

    private UnidadeSaude resolverUnidade(Long unidadeSaudeId, Usuario profissional) {
        if (unidadeSaudeId != null) {
            return unidadeRepository.findById(unidadeSaudeId)
                    .orElseThrow(() -> new IllegalArgumentException("Unidade de saúde não encontrada."));
        }
        if (profissional.getUnidadeSaude() != null) {
            return profissional.getUnidadeSaude();
        }
        throw new IllegalArgumentException("Informe a unidade de saúde para o horário de atendimento.");
    }

    private void validarIntervalo(HorarioAtendimentoDTO dto) {
        if (dto.getHoraInicio() == null || dto.getHoraFim() == null
                || !dto.getHoraInicio().isBefore(dto.getHoraFim())) {
            throw new IllegalArgumentException("O horário de início deve ser anterior ao horário de término.");
        }
    }

    private HorarioAtendimentoDTO toDTO(HorarioAtendimento h) {
        Long unidadeId = (h.getUnidadeSaude() != null) ? h.getUnidadeSaude().getId() : null;
        return new HorarioAtendimentoDTO(
                h.getId(),
                h.getProfissional().getId(),
                unidadeId,
                h.getDiaSemana(),
                h.getHoraInicio(),
                h.getHoraFim()
        );
    }
}