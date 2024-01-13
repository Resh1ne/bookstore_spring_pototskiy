package com.belhard.bookstore.data.connection.impl;

import com.belhard.bookstore.data.connection.DataSource;
import jakarta.annotation.PostConstruct;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.sql.Connection;

@Log4j2
@Component
public class DataSourceImpl implements DataSource, Closeable {
    private CustomConnectionPool connectionPool;
    @Value("${my.app.db.dev.password}")
    private String password;
    @Value("${my.app.db.dev.user}")
    private String user;
    @Value("${my.app.db.dev.url}")
    private String url;
    @Value("${my.app.db.dev.driver}")
    private String driver;

    @Setter
    private int poolSize = 4;

    @PostConstruct
    public void init() {
        connectionPool = new CustomConnectionPool(driver, url, user, password, poolSize);
        log.info("Connection pool initialized");
    }

    @Override
    public Connection getConnection() {
        if (connectionPool == null) {
            connectionPool = new CustomConnectionPool(driver, url, user, password, poolSize);
            log.info("Connection pool initialized");
        }
        return connectionPool.getConnection();
    }

    @Override
    public void close() {
        if (connectionPool != null) {
            connectionPool.destroyPool();
            connectionPool = null;
            log.info("Connection pool destroyed");
        }
    }
}
