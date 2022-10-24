package com.fiap.dow.bitcoin.BITCOIN.repository;

import com.fiap.dow.bitcoin.BITCOIN.vo.ProdutosVO;
import com.fiap.dow.bitcoin.BITCOIN.vo.ReciclagemVO;
import com.fiap.dow.bitcoin.BITCOIN.vo.UsuarioVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.util.List;

@Repository
public class UsuarioRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void atualizaPontos (Long pontos, Long idFuncionario) {
        String SQLUPDATEPONTOS = "UPDATE TB_USUARIOS SET PONTOS_ACUMULADOS = ? WHERE ID = ?";
        int affectedRows = jdbcTemplate.update(SQLUPDATEPONTOS,
                pontos,
                idFuncionario);

        if (affectedRows == 0) {
            throw new RuntimeException();
        }
    }

    public UsuarioVO getUsuario(Long idFuncionario) {
        String SQLGETUSUARIO = "SELECT NOME, NIVEL, CPF, PONTOS_ACUMULADOS, EXPERIENCIA, ID " +
                "FROM TB_USUARIOS WHERE ID = ?";
        List<UsuarioVO> usuarioVO = jdbcTemplate.query(SQLGETUSUARIO, (rs, rowNum) -> {
            UsuarioVO usuario = new UsuarioVO();
            usuario.setUsername(rs.getString("NOME"));
            usuario.setNivel(rs.getLong("NIVEL"));
            usuario.setCpf(rs.getString("CPF"));
            usuario.setPontosAcumulados(rs.getLong("PONTOS_ACUMULADOS"));
            usuario.setExperiencia(rs.getLong("EXPERIENCIA"));
            usuario.setId(rs.getLong("ID"));
            return usuario;
        }, idFuncionario);

        return usuarioVO.get(0);
    }

    public void sobeNivel(Long novaExperiencia, Long idFuncionario, Long novoNivel) {
        String SQLUPDATENIVEL = "UPDATE TB_USUARIOS SET EXPERIENCIA = ?, NIVEL = ? WHERE ID = ?";
        int affectedRows = jdbcTemplate.update(SQLUPDATENIVEL,
                novaExperiencia,
                novoNivel,
                idFuncionario);

        if (affectedRows == 0) {
            throw new RuntimeException();
        }
    }

    public void sobeExperiencia(Long novaExperiencia, Long idFuncionario) {
        String SQLUPDATEEXPERIENCIA = "UPDATE TB_USUARIOS SET EXPERIENCIA = ? WHERE ID = ?";
        int affectedRows = jdbcTemplate.update(SQLUPDATEEXPERIENCIA,
                novaExperiencia,
                idFuncionario);

        if (affectedRows == 0) {
            throw new RuntimeException();
        }
    }

    public Long getPontos(String idFuncionario) {
        String SQLPONTOS = "SELECT PONTOS_ACUMULADOS FROM TB_USUARIOS WHERE NOME = ?";
        return jdbcTemplate.query(SQLPONTOS, (rs, rowNum) -> rs.getLong("PONTOS_ACUMULADOS"), idFuncionario).get(0);
    }

    public List<ReciclagemVO> getHistorico(String idFuncionario) {
        String SQLHISTORICO = "SELECT TR.ID, TR.PONTOS_GERADOS, TR.MATERIAL, TR.QUANTIDADE, TR.DATA FROM TB_RECICLAGEM AS TR INNER JOIN " +
                "TB_USUARIOS AS TU ON TU.ID = TR.ID_FUNCIONARIO WHERE TU.NOME = ?";
        return jdbcTemplate.query(SQLHISTORICO, (rs, rowNum) -> {
            ReciclagemVO reciclagemVO = new ReciclagemVO();
            reciclagemVO.setId(rs.getLong("ID"));
            reciclagemVO.setPontosGerados(rs.getLong("PONTOS_GERADOS"));
            reciclagemVO.setMaterial(rs.getString("MATERIAL"));
            reciclagemVO.setQuantidade(rs.getBigDecimal("QUANTIDADE"));
            reciclagemVO.setData(new java.sql.Date(rs.getDate("DATA").getTime()).toLocalDate());
            return reciclagemVO;
        }, idFuncionario);
    }

    public Long getUsuarioByUsername(String name) {
        String SQL = "SELECT ID FROM TB_USUARIOS WHERE NOME = ?";
        List<Long> lista = jdbcTemplate.query(SQL, (rs, rowNum) -> rs.getLong("ID"), name);
        if (lista.size() != 0) {
            return lista.get(0);
        }
        return 0L;
    }

    public void atualizaHistoricoDeCompras(UsuarioVO usuarioVO, ProdutosVO produto) throws SQLException {
        String SQL = "INSERT INTO TB_HISTORICO_DE_COMPRAS (ID, PRODUTO, PONTOS, USUARIO) VALUES (SQ_HISTORICO_DE_COMPRAS.NEXTVAL," +
                "?, ?, ?)";

        int affectedRows = jdbcTemplate.update(SQL, produto.getNome(), produto.getValorPontos(), usuarioVO.getId());

        if (affectedRows == 0) {
            throw new SQLException();
        }
    }

    public List<ProdutosVO> getHistoricoDeCompras(Long idFuncionario) {
        String SQLHISTORICO = "SELECT * FROM TB_HISTORICO_DE_COMPRAS AS THC INNER JOIN " +
                "TB_USUARIOS AS TU ON TU.ID = THC.USUARIO WHERE THC.USUARIO = ?";
        return jdbcTemplate.query(SQLHISTORICO, (rs, rowNum) -> {
            ProdutosVO produtosVO = new ProdutosVO();
            produtosVO.setNome(rs.getString("PRODUTO"));
            produtosVO.setValorPontos(rs.getLong("PONTOS"));
            return produtosVO;
        }, idFuncionario);
    }
}
