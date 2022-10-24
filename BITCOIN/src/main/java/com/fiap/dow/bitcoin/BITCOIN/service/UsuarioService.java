package com.fiap.dow.bitcoin.BITCOIN.service;

import com.fiap.dow.bitcoin.BITCOIN.vo.UsuarioVO;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private static final Long NIVEL2EXPERIENCE = 1000L;
    private static final Long NIVEL3EXPERIENCE = 10000L;
    private static final Long NIVELMAIORQUE3EXPERIENCE = 30000L;

    public Long getPercentage(UsuarioVO usuarioVO) {
        long percentage = 0L;

        if (usuarioVO.getNivel() == 1) {
            percentage = (long) ((float)usuarioVO.getExperiencia() / NIVEL2EXPERIENCE*100);
        } else if (usuarioVO.getNivel() == 2) {
            percentage = (long) ((float)usuarioVO.getExperiencia() / NIVEL3EXPERIENCE*100);
        } else if (usuarioVO.getNivel() == 3) {
            percentage = (long) ((float) usuarioVO.getExperiencia() / NIVELMAIORQUE3EXPERIENCE*100);
        }

        return percentage;
    }
}
