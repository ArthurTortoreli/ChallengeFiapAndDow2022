package com.fiap.dow.bitcoin.BITCOIN.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioVO {

    private Long id;
    private String username;
    private String password;
    private String cpf;
    private Long pontosAcumulados;
    private Long nivel;
    private Long experiencia;

}
