package com.fiap.dow.bitcoin.BITCOIN.controller;

import com.fiap.dow.bitcoin.BITCOIN.repository.ProdutosRepository;
import com.fiap.dow.bitcoin.BITCOIN.repository.UsuarioRepository;
import com.fiap.dow.bitcoin.BITCOIN.service.LojaService;
import com.fiap.dow.bitcoin.BITCOIN.vo.ProdutosVO;
import com.fiap.dow.bitcoin.BITCOIN.vo.UsuarioVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/loja")
public class LojaController {

    @Autowired
    ProdutosRepository produtosRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    LojaService lojaService;

    @GetMapping("/home")
    public String getHomeLoja(Model model) {
        List<ProdutosVO> produtos = produtosRepository.getProdutos();
        model.addAttribute("produtos", produtos);

        return "loja/home";
    }

    @RequestMapping("/comprar/{id}")
    public String buy(Principal principal, @PathVariable("id") Long idProduto) throws SQLException {
        Long id = usuarioRepository.getUsuarioByUsername(principal.getName());
        if (id == 0L) {
            throw new RuntimeException();
        }
        UsuarioVO usuario = usuarioRepository.getUsuario(id);
        String resultadoDaCompra = lojaService.compraAutorizadaERealizada(usuario, idProduto);

        if (Objects.equals(resultadoDaCompra, "AUTORIZADA")) {
            return "loja/sucesso";
        } else if (Objects.equals(resultadoDaCompra, "NEGADA")) {
            return "loja/saldoInsuficiente";
        }
        return "error";
    }
}
