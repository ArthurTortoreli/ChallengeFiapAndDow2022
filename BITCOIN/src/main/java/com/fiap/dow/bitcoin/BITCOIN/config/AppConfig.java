package com.fiap.dow.bitcoin.BITCOIN.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class AppConfig {

    @Bean
    public JdbcTemplate setJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DataSource setDataSource() throws SQLException {
        EmbeddedDatabaseBuilder databaseBuilder = new EmbeddedDatabaseBuilder();
        databaseBuilder.setType(EmbeddedDatabaseType.HSQL);
        databaseBuilder.addScript("classpath:bd.sql");

        return databaseBuilder.build();
    }
}
