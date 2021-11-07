package by.academy.jee.dao;

import by.academy.jee.exception.DaoDataSourceException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.slf4j.LoggerFactory;

public class DaoDataSource implements DataSource {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(DaoDataSource.class);

    @Override
    public Connection getConnection() throws SQLException {
        Properties repositoryProperties = new Properties();
        try {
            repositoryProperties.load(Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("repository.properties"));
            String url = repositoryProperties.getProperty("postgres.url");
            String user = repositoryProperties.getProperty("postgres.user");
            String password = repositoryProperties.getProperty("postgres.password");
            String driver = repositoryProperties.getProperty("postgres.driver");
            Class.forName(driver);
            return DriverManager.getConnection(url, user, password);
        } catch (IOException | ClassNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new DaoDataSourceException(e.getMessage(), e);
        }
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new UnsupportedOperationException();
    }
}
