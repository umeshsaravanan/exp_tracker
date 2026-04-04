package com.expenseTracker.config;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    @Primary
    public DataSource dataSource(DataSourceProperties properties) {
        String databaseUrl = System.getenv("DATABASE_URL");
        if (databaseUrl != null && databaseUrl.startsWith("postgres")) {
            return buildRenderPostgresDataSource(databaseUrl);
        }
        return properties.initializeDataSourceBuilder().build();
    }

    private static DataSource buildRenderPostgresDataSource(String databaseUrl) {
        try {
            URI uri = new URI(databaseUrl);
            String userInfo = uri.getUserInfo();
            if (userInfo == null) {
                throw new IllegalArgumentException("DATABASE_URL missing user info");
            }
            int colon = userInfo.indexOf(':');
            String username = URLDecoder.decode(
                    colon > 0 ? userInfo.substring(0, colon) : userInfo,
                    StandardCharsets.UTF_8);
            String password = URLDecoder.decode(
                    colon > 0 ? userInfo.substring(colon + 1) : "",
                    StandardCharsets.UTF_8);

            String host = uri.getHost();
            int port = uri.getPort() > 0 ? uri.getPort() : 5432;
            String db = uri.getPath().replaceFirst("^/", "");
            if (db.isEmpty()) {
                throw new IllegalArgumentException("DATABASE_URL missing database name");
            }

            String query = uri.getRawQuery();
            String jdbcUrl = "jdbc:postgresql://" + host + ":" + port + "/" + db;
            if (query != null && !query.isEmpty()) {
                jdbcUrl += "?" + query;
            } else {
                jdbcUrl += "?sslmode=require";
            }

            HikariDataSource ds = new HikariDataSource();
            ds.setJdbcUrl(jdbcUrl);
            ds.setUsername(username);
            ds.setPassword(password);
            ds.setDriverClassName("org.postgresql.Driver");
            return ds;
        } catch (Exception e) {
            throw new IllegalStateException("Invalid DATABASE_URL", e);
        }
    }
}
