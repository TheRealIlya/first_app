package by.academy.jee.dao.person.admin;

import by.academy.jee.dao.DaoDataSource;
import by.academy.jee.dao.person.PersonDao;
import by.academy.jee.exception.AdminDaoException;
import by.academy.jee.model.person.Admin;
import by.academy.jee.model.person.role.Role;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdminDaoForPostgres implements PersonDao<Admin> {

    private static final Logger log = LoggerFactory.getLogger(AdminDaoForPostgres.class);
    private static final String SELECT_ALL_ADMINS = "select u.id, u.login, u.password," +
            "u.salt, u.name, u.age, r.title \n" +
            "from \"user\" u\n" +
            "join \"role\" r\n" +
            "on u.role_id = r.id; ";
    private static final String ADMIN_LOGIN_FILTER = "where u.login = ?";
    private static final String SELECT_ONE_ADMIN = SELECT_ALL_ADMINS + ADMIN_LOGIN_FILTER;
    private final DataSource dataSource = new DaoDataSource();

    @Override
    public boolean create(Admin admin) {
        return false; //TODO
    }

    @Override
    public Admin read(int id) {
        return null; //TODO
    }

    @Override
    public Admin read(String name) {

        List<Admin> result = new ArrayList<>();
        ResultSet rs = null;
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ONE_ADMIN);
        ) {
            ps.setString(1, name);
            rs = ps.executeQuery();
            result = resultSetToAdmins(rs);
        } catch (SQLException e) {
            logAndThrowMyException(e);
        } finally {
            closeQuietly(rs);
        }
        return result.stream().findFirst().orElse(null);
    }

    @Override
    public boolean update(Admin newT) {
        return false; //TODO
    }

    @Override
    public boolean delete(int id) {
        return false; //TODO
    }

    @Override
    public List<Admin> readAll() {
        return null; //TODO
    }

    private static void closeQuietly(AutoCloseable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (Exception e) {
            log.error("Couldn't close {}", closeable);
        }
    }

    private void logAndThrowMyException(Exception e) {
        log.error(e.getMessage(), e);
        throw new AdminDaoException(e.getMessage(), e);
    }

    private List<Admin> resultSetToAdmins(ResultSet rs) throws SQLException {

        List<Admin> result = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("u.id");
            String login = rs.getString("u.login");
            byte[] pwd = rs.getBytes("u.password");
            byte[] salt = rs.getBytes("u.salt");
            String name = rs.getString("u.name");
            int age = rs.getInt("u.age");
            Role role = Role.valueOf(rs.getString("r.title"));
            Admin admin = new Admin()
                    .withId(id)
                    .withLogin(login)
                    .withPwd(pwd)
                    .withSalt(salt)
                    .withName(name)
                    .withAge(age)
                    .withRole(role);
            result.add(admin);
        }
        return result;
    }
}
