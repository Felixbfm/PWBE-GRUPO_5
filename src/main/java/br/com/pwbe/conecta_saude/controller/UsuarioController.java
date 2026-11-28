package br.com.pwbe.conecta_saude.controller;

import br.com.pwbe.conecta_saude.dto.CadastroUsuarioDTO;
import br.com.pwbe.conecta_saude.dto.LoginDTO;
import br.com.pwbe.conecta_saude.dto.UsuarioResponseDTO;
import br.com.pwbe.conecta_saude.enums.TipoUsuario;
import br.com.pwbe.conecta_saude.service.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/cadastro")
    public String cadastro(Model model) {
        model.addAttribute("usuario", new CadastroUsuarioDTO());
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrar(
            @ModelAttribute("usuario") CadastroUsuarioDTO dto,
            Model model) {

        try {
            dto.setTipo(TipoUsuario.PACIENTE);
            dto.setEspecialidade(null);
            dto.setUnidadeSaudeId(null);

            usuarioService.cadastrar(dto);

            return "redirect:/usuarios/login";

        } catch (IllegalArgumentException e) {
            dto.setSenha(null);
            dto.setConfirmacaoSenha(null);
            model.addAttribute("erro", e.getMessage());
            return "cadastro";
        }
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("login", new LoginDTO());
        return "login";
    }

    @PostMapping("/login")
    public String autenticar(
            @ModelAttribute("login") LoginDTO dto,
            HttpServletRequest request,
            Model model) {

        try {
            UsuarioResponseDTO usuario =
                    usuarioService.autenticar(dto);

            HttpSession session = request.getSession();
            request.changeSessionId();

            session.setAttribute("usuarioLogado", usuario);

            return "redirect:/";

        } catch (IllegalArgumentException e) {
            dto.setSenha(null);
            model.addAttribute("erro", e.getMessage());
            return "login";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}