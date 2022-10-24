package com.fiap.dow.bitcoin.BITCOIN.controller;

import com.fiap.dow.bitcoin.BITCOIN.repository.UsuarioRepository;
import com.fiap.dow.bitcoin.BITCOIN.service.ReciclagemService;
import com.fiap.dow.bitcoin.BITCOIN.vo.ReciclagemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/reciclagem")
public class ReciclagemController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ReciclagemService reciclagemService;

    @GetMapping
    public String getTela(ReciclagemVO reciclagemVO, Principal principal, Model model) {
        Long idFuncionario = usuarioRepository.getUsuarioByUsername(principal.getName());
        if (idFuncionario == 0L) {
            throw new RuntimeException();
        }
        model.addAttribute("idBancoDeDados", idFuncionario);
        return "reciclagem/home";
    }

    @RequestMapping("/nova")
    public String cadastrarNovaReciclagem(ReciclagemVO reciclagemVO) {
        try {
            reciclagemService.cadastraReciclagem(reciclagemVO);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
        return "sucessoReciclagem";
    }
}
