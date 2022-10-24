package com.fiap.dow.bitcoin.BITCOIN.controller;

import com.fiap.dow.bitcoin.BITCOIN.repository.UsuarioRepository;
import com.fiap.dow.bitcoin.BITCOIN.service.UsuarioService;
import com.fiap.dow.bitcoin.BITCOIN.vo.ProdutosVO;
import com.fiap.dow.bitcoin.BITCOIN.vo.ReciclagemVO;
import com.fiap.dow.bitcoin.BITCOIN.vo.UsuarioVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/home")
    public String getHomeAfterLogin(Model model, Principal principal) {
        System.out.println(principal.getName());
        Long idFuncionario = usuarioRepository.getUsuarioByUsername(principal.getName());
        if (idFuncionario == 0L) {
            throw new RuntimeException();
        }
        model.addAttribute("id", idFuncionario);
        return "usuario/home";
    }

    @RequestMapping("/pontos/")
    public String getPontos(Principal principal, Model model){
        try {
            Long pontos = usuarioRepository.getPontos(principal.getName());
            Long id = usuarioRepository.getUsuarioByUsername(principal.getName());
            if (id == 0L) {
                throw new RuntimeException();
            }
            UsuarioVO usuario = usuarioRepository.getUsuario(id);
            Long percentage = usuarioService.getPercentage(usuario);
            List<ProdutosVO> produtosJaComprados = usuarioRepository.getHistoricoDeCompras(usuario.getId());
            model.addAttribute("pontos", pontos);
            model.addAttribute("nivel", usuario.getNivel());
            model.addAttribute("experiencia", usuario.getExperiencia());
            model.addAttribute("produtosJaComprados", produtosJaComprados);
            model.addAttribute("percentage", percentage);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
        return "usuario/pontos";
    }

    @RequestMapping("/historico/")
    public String getHistorico (Principal principal, Model model) {
        try {
            List<ReciclagemVO> reciclagensVO = usuarioRepository.getHistorico(principal.getName());
            model.addAttribute("reciclagensVO", reciclagensVO);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
        return "usuario/historico";
    }
}
