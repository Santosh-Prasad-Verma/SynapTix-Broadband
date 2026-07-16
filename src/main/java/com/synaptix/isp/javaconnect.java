package com.synaptix.isp;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class javaconnect {
    private static HikariDataSource dataSource;

    static {
        Properties prop = new Properties();
        String url = "jdbc:postgresql://localhost:5435/isp";
        String user = "postgres";
        String password = "postgres";

        try (InputStream input = javaconnect.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input != null) {
                prop.load(input);
                url = prop.getProperty("db.url");
                user = prop.getProperty("db.username");
                password = prop.getProperty("db.password");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(url);
            config.setUsername(user);
            config.setPassword(password);
            config.setDriverClassName("org.postgresql.Driver");

            // Hikari Connection Pool settings
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setIdleTimeout(30000);
            config.setMaxLifetime(1800000);
            config.setConnectionTimeout(10000);

            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection ConnecrDb() {
        try {
            if (dataSource != null) {
                return dataSource.getConnection();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
