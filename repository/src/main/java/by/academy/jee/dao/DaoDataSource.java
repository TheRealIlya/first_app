package by.academy.jee.dao;

import by.academy.jee.exception.DaoDataSourceException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.slf4j.LoggerFactory;

public class DaoDataSource implements DataSource {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(DaoDataSource.class);
    private final String url;
    private final String user;
    private final String password;
    private final String driver;

    private static volatile DaoDataSource instance;

    private DaoDataSource(String url, String user, String password, String driver) {

        //singleton
        this.url = url;
        this.user = user;
        this.password = password;
        this.driver = driver;
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new DaoDataSourceException(e.getMessage(), e);
        }
    }

    public static DaoDataSource getInstance(String url, String user, String password, String driver) {

        if (instance == null) {
            synchronized (DaoDataSource.class) {
                if (instance == null) {
                    instance = new DaoDataSource(url, user, password, driver);
                }
            }
        }
        return instance;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
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
