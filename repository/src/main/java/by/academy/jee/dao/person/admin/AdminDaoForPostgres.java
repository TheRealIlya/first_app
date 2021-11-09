package by.academy.jee.dao.person.admin;

import by.academy.jee.dao.person.PersonDao;
import by.academy.jee.exception.PersonDaoException;
import by.academy.jee.model.person.Admin;
import by.academy.jee.model.person.role.Role;
import by.academy.jee.util.DataBaseUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static by.academy.jee.constant.Constant.LOGIN_FILTER_POSTGRES;
import static by.academy.jee.constant.Constant.INSERT_ADMIN_POSTGRES;
import static by.academy.jee.constant.Constant.R_TITLE;
import static by.academy.jee.constant.Constant.SELECT_ALL_ADMINS_POSTGRES;
import static by.academy.jee.constant.Constant.U_AGE;
import static by.academy.jee.constant.Constant.U_ID;
import static by.academy.jee.constant.Constant.U_LOGIN;
import static by.academy.jee.constant.Constant.U_NAME;
import static by.academy.jee.constant.Constant.U_PASSWORD;
import static by.academy.jee.constant.Constant.U_SALT;

public class AdminDaoForPostgres implements PersonDao<Admin> {

    private static final Logger log = LoggerFactory.getLogger(AdminDaoForPostgres.class);
    private static final String SELECT_ONE_ADMIN = SELECT_ALL_ADMINS_POSTGRES + LOGIN_FILTER_POSTGRES;
    private final DataSource dataSource;

    private static volatile AdminDaoForPostgres instance;

    private AdminDaoForPostgres(DataSource dataSource) {
        //singleton
        this.dataSource = dataSource;
    }

    public static AdminDaoForPostgres getInstance(DataSource dataSource) {
        if (instance == null) {
            synchronized (AdminDaoForPostgres.class) {
                if (instance == null) {
                    instance = new AdminDaoForPostgres(dataSource);
                }
            }
        }
        return instance;
    }

    @Override
    public boolean create(Admin admin) {

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_ADMIN_POSTGRES)) {
            ps.setString(1, admin.getLogin());
            ps.setBytes(2, admin.getPwd());
            ps.setBytes(3, admin.getSalt());
            ps.setString(4, admin.getName());
            ps.setInt(5, admin.getAge());
            ps.execute();
        } catch (SQLException e) {
            logAndThrowMyException(e);
        }
        return true;
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
             PreparedStatement ps = con.prepareStatement(SELECT_ONE_ADMIN)) {
            ps.setString(1, name);
            rs = ps.executeQuery();
            result = resultSetToAdmins(rs);
        } catch (SQLException e) {
            logAndThrowMyException(e);
        } finally {
            DataBaseUtil.closeQuietly(rs);
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

    private void logAndThrowMyException(Exception e) {
        log.error(e.getMessage(), e);
        throw new PersonDaoException(e.getMessage(), e);
    }

    private List<Admin> resultSetToAdmins(ResultSet rs) throws SQLException {

        List<Admin> result = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt(U_ID);
            String login = rs.getString(U_LOGIN);
            byte[] pwd = rs.getBytes(U_PASSWORD);
            byte[] salt = rs.getBytes(U_SALT);
            String name = rs.getString(U_NAME);
            int age = rs.getInt(U_AGE);
            Role role = Role.valueOf(rs.getString(R_TITLE));
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
        result.removeIf(admin -> !Role.ADMIN.equals(admin.getRole()));
        return result;
    }
}
