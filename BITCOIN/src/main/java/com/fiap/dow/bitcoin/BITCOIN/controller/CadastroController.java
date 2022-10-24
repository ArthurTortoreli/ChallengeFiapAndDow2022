package com.fiap.dow.bitcoin.BITCOIN.controller;

import com.fiap.dow.bitcoin.BITCOIN.repository.LoginRepository;
import com.fiap.dow.bitcoin.BITCOIN.vo.UsuarioVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;

@Controller
@RequestMapping("/cadastro")
public class CadastroController {

    @Autowired
    LoginRepository dao;

    @GetMapping
    public String showPage() {
        return "cadastro";
    }

    @PostMapping("/novo")
    public String signUp(UsuarioVO usuarioVO) throws SQLException {
        boolean hasUser = dao.getUsuarioExistente(usuarioVO.getCpf());

        if (hasUser) {
            return "redirect:/error";
        }
        String cryptoPassword = new BCryptPasswordEncoder().encode(usuarioVO.getPassword());
        usuarioVO.setPassword(cryptoPassword);
        dao.cadastro(usuarioVO);

        return "redirect:/login";
    }
}
