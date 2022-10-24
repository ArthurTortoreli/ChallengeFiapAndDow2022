package com.fiap.dow.bitcoin.BITCOIN.repository;

import com.fiap.dow.bitcoin.BITCOIN.vo.UsuarioVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class LoginRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public boolean getUsuarios(UsuarioVO usuarioVO) {
        String SQL = "SELECT * FROM TB_USUARIOS WHERE NOME= ? AND SENHA= ?";
        List<UsuarioVO> returnedList = jdbcTemplate.query(SQL, (rs, rowNum) -> {
            UsuarioVO usuario = new UsuarioVO();
            usuario.setId(rs.getLong("ID"));
            usuario.setCpf(rs.getString("CPF"));
            usuario.setNivel(rs.getLong("NIVEL"));
            usuario.setUsername(rs.getString("NOME"));
            usuario.setPontosAcumulados(rs.getLong("PONTOS_ACUMULADOS"));
            return usuario;
        }, usuarioVO.getUsername(), usuarioVO.getPassword());
        return !returnedList.isEmpty();
    }

    public boolean getUsuarioExistente(String cpf) {
        String SQLCPF = "SELECT * FROM TB_USUARIOS WHERE CPF = ?";
        List<UsuarioVO> returnedList = jdbcTemplate.query(SQLCPF, (rs, rowNum) -> {
            UsuarioVO usuario = new UsuarioVO();
            usuario.setId(rs.getLong("ID"));
            usuario.setCpf(rs.getString("CPF"));
            usuario.setNivel(rs.getLong("NIVEL"));
            usuario.setUsername(rs.getString("NOME"));
            usuario.setPontosAcumulados(rs.getLong("PONTOS_ACUMULADOS"));
            return usuario;
        }, cpf);
        return !returnedList.isEmpty();
    }

    public void cadastro(UsuarioVO usuarioVO) throws SQLException {
        String SQLCADASTRO = "INSERT INTO TB_USUARIOS" +
                "(ID, NOME, SENHA, CPF, NIVEL, PONTOS_ACUMULADOS)" +
                " VALUES (SQ_ID_FUNCIONARIO.NEXTVAL, ?, ?, ?, 1, 0)";
        int affectedRows = jdbcTemplate.update(SQLCADASTRO,
                usuarioVO.getUsername(),
                usuarioVO.getPassword(),
                usuarioVO.getCpf());

        if (affectedRows == 0) {
            throw new SQLException();
        }

        String SQLCADASTROUSERS = "INSERT INTO USERS (USERNAME, PASSWORD, ENABLED) " +
                "VALUES (?, ?, 'TRUE')";
        int secondAffectedRows = jdbcTemplate.update(SQLCADASTROUSERS,
                usuarioVO.getUsername(),
                usuarioVO.getPassword());

        if (secondAffectedRows == 0) {
            throw new SQLException();
        }

        String SQLCADASTROAUTHORITIES = "INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) " +
                "VALUES (?, 'USER')";
        int thirdAffectedRows = jdbcTemplate.update(SQLCADASTROAUTHORITIES,
                usuarioVO.getUsername());

        if (thirdAffectedRows == 0) {
            throw new SQLException();
        }
    }
}
