package com.fiap.dow.bitcoin.BITCOIN.vo;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class ReciclagemVO {

    private Long id;
    private Long idFuncionario;
    private Long idPontoDeColeta;
    private String material;
    private BigDecimal quantidade;
    private Long pontosGerados;
    private LocalDate data;
}
