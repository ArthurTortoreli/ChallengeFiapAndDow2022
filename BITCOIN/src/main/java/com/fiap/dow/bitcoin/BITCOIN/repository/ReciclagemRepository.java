package com.fiap.dow.bitcoin.BITCOIN.repository;

import com.fiap.dow.bitcoin.BITCOIN.vo.PontosDeColetaVO;
import com.fiap.dow.bitcoin.BITCOIN.vo.ReciclagemVO;
import com.fiap.dow.bitcoin.BITCOIN.vo.UsuarioVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public class ReciclagemRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String SQLFUNCIONARIO = "SELECT ID FROM TB_USUARIOS WHERE ID = ?";
    private final String SQLPONTODECOLETA = "SELECT ID FROM TB_PONTOS_DE_RECICLAGEM WHERE ID = ?";
    private final String SQLINSERT = "INSERT INTO TB_RECICLAGEM " +
            "(ID, ID_FUNCIONARIO, ID_PONTO_DE_RECICLAGEM, MATERIAL, QUANTIDADE, PONTOS_GERADOS, DATA) " +
            "VALUES (SQ_ID_RECICLAGEM.NEXTVAL, ?, ?, ?, ?, ?, ?);";

    public boolean isValid(Long id, String tipo) {
        if (tipo == "FUNCIONARIO") {
            List<UsuarioVO> returnedList = jdbcTemplate.query(SQLFUNCIONARIO, (rs, rowNum) -> {
                UsuarioVO usuario = new UsuarioVO();
                usuario.setId(rs.getLong("ID"));
                return usuario;
            }, id);
            return !returnedList.isEmpty();
        } else if (tipo == "PONTO DE COLETA") {
            List<PontosDeColetaVO> returnedList = jdbcTemplate.query(SQLPONTODECOLETA, (rs, rowNum) -> {
                PontosDeColetaVO pontosDeColetaVO = new PontosDeColetaVO();
                pontosDeColetaVO.setId(rs.getLong("ID"));
                return pontosDeColetaVO;
            }, id);
            return !returnedList.isEmpty();
        }
        return false;
    }

    public void insert(ReciclagemVO reciclagemVO) {
        int affectedRows = jdbcTemplate.update(SQLINSERT,
                reciclagemVO.getIdFuncionario(),
                reciclagemVO.getIdPontoDeColeta(),
                reciclagemVO.getMaterial(),
                reciclagemVO.getQuantidade(),
                reciclagemVO.getPontosGerados(),
                LocalDate.now()
                );

        if (affectedRows == 0) {
            throw new RuntimeException();
        }
    }
}
