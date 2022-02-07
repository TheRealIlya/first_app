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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import static by.academy.jee.constant.CommonConstant.REPOSITORY_PROPERTIES;

@Component
@PropertySource(REPOSITORY_PROPERTIES)
public class DaoDataSource implements DataSource {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(DaoDataSource.class);
    private final String url;
    private final String user;
    private final String password;
    private final String driver;

    public DaoDataSource(@Value("${postgres.url}") String url, @Value("${postgres.user}") String user,
                         @Value("${postgres.password}") String password, @Value("${postgres.driver}") String driver) {

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
