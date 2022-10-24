package com.fiap.dow.bitcoin.BITCOIN.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/usuario/locais", "usuario/home/locais", "usuario/home/feed/locais/", "usuario/feed/locais"})
public class LocaisController {

    @GetMapping("/seuze")
    public String getSeuZe() {
        return "locais/seuze";
    }

    @GetMapping("/ecoPonto")
    public String getEcoPonto() {
        return "locais/ecoponto";
    }

    @GetMapping("/pontoDeColeta")
    public String getPostoDeColeta() {
        return "locais/postoDeColeta";
    }
}
