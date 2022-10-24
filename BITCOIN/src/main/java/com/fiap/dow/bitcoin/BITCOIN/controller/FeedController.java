package com.fiap.dow.bitcoin.BITCOIN.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/usuario/feed", "usuario/home/feed"})
public class FeedController {

    @GetMapping("/texto1")
    public String getText1() {
        return "usuario/feed/texto1";
    }

    @GetMapping("/texto2")
    public String getText2() {
        return "usuario/feed/texto2";
    }

    @GetMapping("/texto3")
    public String getText3() {
        return "usuario/feed/texto3";
    }
}
