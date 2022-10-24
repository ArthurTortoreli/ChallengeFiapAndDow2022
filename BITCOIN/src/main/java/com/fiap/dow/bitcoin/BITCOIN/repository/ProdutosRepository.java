package com.fiap.dow.bitcoin.BITCOIN.repository;

import com.fiap.dow.bitcoin.BITCOIN.vo.ProdutosVO;
import com.fiap.dow.bitcoin.BITCOIN.vo.ReciclagemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProdutosRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String SQLPRODUTOS = "select * from tb_produtos";
    private final String SQLPRODUTOSBYID = "SELECT ID, NOME, URL_IMAGEM, VALOR_PONTOS FROM TB_PRODUTOS WHERE ID = ?";

    public List<ProdutosVO> getProdutos() {
        return jdbcTemplate.query(SQLPRODUTOS, (rs, rowNum) -> {
            ProdutosVO produtosVO = new ProdutosVO();
            produtosVO.setId(rs.getLong("ID"));
            produtosVO.setNome(rs.getString("NOME"));
            produtosVO.setUrlImagem(rs.getString("URL_IMAGEM"));
            produtosVO.setValorPontos(rs.getLong("VALOR_PONTOS"));
            return produtosVO;
        });
    }

    public ProdutosVO getProdutosById(Long idProduto) {
        List<ProdutosVO> produtos = jdbcTemplate.query(SQLPRODUTOSBYID, (rs, rowNum) -> {
            ProdutosVO produtosVO = new ProdutosVO();
            produtosVO.setId(rs.getLong("ID"));
            produtosVO.setNome(rs.getString("NOME"));
            produtosVO.setUrlImagem(rs.getString("URL_IMAGEM"));
            produtosVO.setValorPontos(rs.getLong("VALOR_PONTOS"));
            return produtosVO;
        }, idProduto);

        if (produtos.isEmpty()) {
            return null;
        }
        return produtos.get(0);
    }
}
