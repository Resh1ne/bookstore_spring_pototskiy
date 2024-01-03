package com.belhard.bookstore.data.connection;

import java.io.Closeable;
import java.sql.Connection;

public interface DataSource extends Closeable {
    Connection getConnection();
}
