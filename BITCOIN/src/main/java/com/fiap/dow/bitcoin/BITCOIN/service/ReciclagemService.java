package com.fiap.dow.bitcoin.BITCOIN.service;

import com.fiap.dow.bitcoin.BITCOIN.repository.ReciclagemRepository;
import com.fiap.dow.bitcoin.BITCOIN.repository.UsuarioRepository;
import com.fiap.dow.bitcoin.BITCOIN.vo.ReciclagemVO;
import com.fiap.dow.bitcoin.BITCOIN.vo.UsuarioVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ReciclagemService {

    @Autowired
    ReciclagemRepository reciclagemRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    public void cadastraReciclagem(ReciclagemVO reciclagemVO) {
        try {
            boolean isValidFuncionario = reciclagemRepository.isValid(reciclagemVO.getIdFuncionario(), "FUNCIONARIO");
            boolean isValidPontoDeColeta = reciclagemRepository.isValid(reciclagemVO.getIdPontoDeColeta(), "PONTO DE COLETA");

            if (isValidFuncionario && isValidPontoDeColeta) {
                UsuarioVO usuarioVO = usuarioRepository.getUsuario(reciclagemVO.getIdFuncionario());
                Long pontosGerados = calculaPontosGerados(reciclagemVO);
                reciclagemVO.setPontosGerados(pontosGerados);

                reciclagemRepository.insert(reciclagemVO);
                Long pontosFinais = pontosGerados + usuarioVO.getPontosAcumulados();
                usuarioRepository.atualizaPontos(pontosFinais, reciclagemVO.getIdFuncionario());
                atualizaNivel(reciclagemVO.getIdFuncionario(), pontosGerados);
            } else {
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private void atualizaNivel(Long idFuncionario, Long pontosGerados) {
        UsuarioVO usuarioVO = usuarioRepository.getUsuario(idFuncionario);
        Long novaExperiencia = usuarioVO.getExperiencia() + pontosGerados;

        if (novaExperiencia >= 1000L && usuarioVO.getNivel() == 1) {
            usuarioRepository.sobeNivel(novaExperiencia, idFuncionario, 2L);
        } else if (novaExperiencia >= 10000L && usuarioVO.getNivel() == 2) {
            usuarioRepository.sobeNivel(novaExperiencia, idFuncionario, 3L);
        } else if (novaExperiencia >= 20000L && usuarioVO.getNivel() == 3){
            usuarioRepository.sobeNivel(novaExperiencia, idFuncionario, 4L);
        } else {
            usuarioRepository.sobeExperiencia(novaExperiencia, idFuncionario);
        }
    }

    private Long calculaPontosGerados(ReciclagemVO reciclagemVO) {
        if (Objects.equals(reciclagemVO.getMaterial(), "PLASTICO")) {
            Long quantoVale = 50L;
            Long quantidadeLong = reciclagemVO.getQuantidade().longValue();

            return quantidadeLong * quantoVale;
        } else if (Objects.equals(reciclagemVO.getMaterial(), "METAL")) {
            Long quantoVale = 20L;
            Long quantidadeLong = reciclagemVO.getQuantidade().longValue();

            return quantidadeLong * quantoVale;
        }
        return 0L;
    }
}
