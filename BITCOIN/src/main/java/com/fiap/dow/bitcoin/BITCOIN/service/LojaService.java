package com.fiap.dow.bitcoin.BITCOIN.service;

import com.fiap.dow.bitcoin.BITCOIN.repository.ProdutosRepository;
import com.fiap.dow.bitcoin.BITCOIN.repository.UsuarioRepository;
import com.fiap.dow.bitcoin.BITCOIN.vo.ProdutosVO;
import com.fiap.dow.bitcoin.BITCOIN.vo.UsuarioVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class LojaService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ProdutosRepository produtosRepository;

    public String compraAutorizadaERealizada(UsuarioVO usuarioVO, Long idProduto) throws SQLException {
        ProdutosVO produto = produtosRepository.getProdutosById(idProduto);

        if (produto == null) {
            return "produtoNulo";
        }

        String resultado = verificaSaldoVersusCusto(usuarioVO, produto);

        if (resultado.equals("AUTORIZADA")) {
            Long pontos = calculaPontosParaCompra(usuarioVO, produto);
            usuarioRepository.atualizaPontos(pontos, usuarioVO.getId());
            usuarioRepository.atualizaHistoricoDeCompras(usuarioVO, produto);
        }

        return resultado;
    }

    private String verificaSaldoVersusCusto(UsuarioVO usuarioVO, ProdutosVO produtosVO) {
        if (usuarioVO.getPontosAcumulados() >= produtosVO.getValorPontos()) {
            return "AUTORIZADA";
        }
        return "NEGADA";
    }

    private Long calculaPontosParaCompra(UsuarioVO usuarioVO, ProdutosVO produtosVO) {
        return usuarioVO.getPontosAcumulados() - produtosVO.getValorPontos();
    }
}
