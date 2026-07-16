package com.synaptix.isp;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class javaconnect {
    private static HikariDataSource dataSource;
    private static String url = "jdbc:postgresql://localhost:5435/isp";
    private static String user = "postgres";
    private static String password = "postgres";

    static {
        Properties prop = new Properties();

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

    public static String getDbUrl() { return url; }
    public static String getDbUser() { return user; }
    public static String getDbPassword() { return password; }

    public static class ConnectionParams {
        public String host = "localhost";
        public String port = "5435"; // match local port from default
        public String dbName = "isp";
    }

    public static ConnectionParams getConnectionParams() {
        ConnectionParams params = new ConnectionParams();
        try {
            if (url != null && url.startsWith("jdbc:postgresql://")) {
                String clean = url.substring("jdbc:postgresql://".length());
                String[] hostPortAndDb = clean.split("/");
                if (hostPortAndDb.length > 0) {
                    String hostPort = hostPortAndDb[0];
                    if (hostPort.contains(":")) {
                        String[] hp = hostPort.split(":");
                        params.host = hp[0];
                        params.port = hp[1];
                    } else {
                        params.host = hostPort;
                    }
                }
                if (hostPortAndDb.length > 1) {
                    params.dbName = hostPortAndDb[1].split("\\?")[0];
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
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

