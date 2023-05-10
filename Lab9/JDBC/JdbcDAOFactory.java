package org.example.JDBC;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.example.DAOFactory;
import org.example.JDBC.dataSets.CSVData;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JdbcDAOFactory implements DAOFactory {
    protected static HikariDataSource dataSource;

    public static Connection getConnection() {
        try {
            return getDataSource().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static DataSource getDataSource() {
        if (null == dataSource) {
            System.out.println("No DataSource is available. We will create a new one.");
            createDataSource();
        }
        return dataSource;
    }

    private static void createDataSource() {
        HikariConfig hikariConfig = getHikariConfig();
        System.out.println("Configuration is ready.");
        System.out.println("Creating the HikariDataSource and assigning it as the global");
        dataSource = new HikariDataSource(hikariConfig);
    }

    private static HikariConfig getHikariConfig() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/lab8java");
        config.setUsername("postgres");
        config.setPassword("valea");
        config.setMaximumPoolSize(50);
        config.setAutoCommit(false);
        config.setConnectionTimeout(0);
        config.setIdleTimeout(30000);
        return config;
    }

    @Override
    public void setData() {
        CSVData data = new CSVData();
    }
}
