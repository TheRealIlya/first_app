package by.academy.jee.dao.person.teacher;

import by.academy.jee.dao.person.PersonDao;
import by.academy.jee.exception.DaoException;
import by.academy.jee.model.person.Teacher;
import by.academy.jee.model.person.role.Role;
import by.academy.jee.util.DataBaseUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import static by.academy.jee.constant.Constant.ERROR_NO_SUCH_TEACHER;
import static by.academy.jee.constant.Constant.ERROR_NO_TEACHERS_IN_DATABASE;
import static by.academy.jee.constant.Constant.INSERT_SALARIES_POSTGRES;
import static by.academy.jee.constant.Constant.LOGIN_FILTER_POSTGRES;
import static by.academy.jee.constant.Constant.INSERT_TEACHER_POSTGRES;
import static by.academy.jee.constant.Constant.R_TITLE;
import static by.academy.jee.constant.Constant.SELECT_ALL_TEACHERS_POSTGRES;
import static by.academy.jee.constant.Constant.S_SALARIES_KEY;
import static by.academy.jee.constant.Constant.S_VALUE;
import static by.academy.jee.constant.Constant.USER_CREATE_TRANSACTION_ERROR;
import static by.academy.jee.constant.Constant.U_AGE;
import static by.academy.jee.constant.Constant.U_ID;
import static by.academy.jee.constant.Constant.U_LOGIN;
import static by.academy.jee.constant.Constant.U_NAME;
import static by.academy.jee.constant.Constant.U_PASSWORD;
import static by.academy.jee.constant.Constant.U_SALT;

@Component
@RequiredArgsConstructor
public class TeacherDaoForPostgres implements PersonDao<Teacher> {

    private static final Logger log = LoggerFactory.getLogger(TeacherDaoForPostgres.class);
    private static final String SELECT_ONE_TEACHER = SELECT_ALL_TEACHERS_POSTGRES + LOGIN_FILTER_POSTGRES;
    private final DataSource dataSource;

    @Override
    public Teacher create(Teacher teacher) {

        Connection con = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        try {
            con = dataSource.getConnection();
            ps1 = con.prepareStatement(INSERT_TEACHER_POSTGRES);
            con.setAutoCommit(false);
            ps1.setString(1, teacher.getLogin());
            ps1.setBytes(2, teacher.getPwd());
            ps1.setBytes(3, teacher.getSalt());
            ps1.setString(4, teacher.getName());
            ps1.setInt(5, teacher.getAge());
            if (ps1.executeUpdate() <= 0) {
                DataBaseUtil.rollBack(con);
                log.error(USER_CREATE_TRANSACTION_ERROR);
                throw new DaoException(USER_CREATE_TRANSACTION_ERROR);
            }
            for (int i = 1; i < 13; i++) {
                ps2 = con.prepareStatement(INSERT_SALARIES_POSTGRES);
                ps2.setInt(1, i);
                ps2.setDouble(2, teacher.getSalaries().get(i));
                ps2.setString(3, teacher.getLogin());
                if (ps2.executeUpdate() <= 0) {
                    DataBaseUtil.rollBack(con);
                    log.error(USER_CREATE_TRANSACTION_ERROR);
                    throw new DaoException(USER_CREATE_TRANSACTION_ERROR);
                }
            }
            con.commit();
            return teacher;
        } catch (SQLException e) {
            DataBaseUtil.rollBack(con);
            log.error(e.getMessage(), e);
            throw new DaoException(e.getMessage(), e);
        } finally {
            DataBaseUtil.closeQuietly(ps2, ps1, con);
        }
    }

    @Override
    public Teacher read(int id) {
        return null; //TODO
    }

    @Override
    public Teacher read(String name) {

        List<Teacher> result;
        ResultSet rs = null;
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ONE_TEACHER)) {
            ps.setString(1, name);
            rs = ps.executeQuery();
            result = resultSetToTeachers(rs);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new DaoException(e.getMessage(), e);
        } finally {
            DataBaseUtil.closeQuietly(rs);
        }
        return result.stream().findFirst()
                .orElseThrow(() -> new DaoException(ERROR_NO_SUCH_TEACHER));
    }

    @Override
    public Teacher update(Teacher newT) {
        return null; //TODO
    }

    @Override
    public boolean delete(String name) {
        return false; //TODO
    }

    @Override
    public List<Teacher> readAll() {

        List<Teacher> result;
        ResultSet rs = null;
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL_TEACHERS_POSTGRES)) {
            rs = ps.executeQuery();
            result = resultSetToTeachers(rs);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new DaoException(e.getMessage(), e);
        } finally {
            DataBaseUtil.closeQuietly(rs);
        }
        if (result.size() == 0) {
            log.error(ERROR_NO_TEACHERS_IN_DATABASE);
            throw new DaoException(ERROR_NO_TEACHERS_IN_DATABASE);
        }
        return result;
    }

    private List<Teacher> resultSetToTeachers(ResultSet rs) throws SQLException {

        String previousLogin = null;
        List<Teacher> result = new ArrayList<>();
        Map<Integer, Double> salaries = new ConcurrentHashMap<>();
        while (rs.next()) {
            int id = rs.getInt(U_ID);
            String login = rs.getString(U_LOGIN);
            byte[] pwd = rs.getBytes(U_PASSWORD);
            byte[] salt = rs.getBytes(U_SALT);
            String name = rs.getString(U_NAME);
            int age = rs.getInt(U_AGE);
            Role role = Role.valueOf(rs.getString(R_TITLE));
            int month = rs.getInt(S_SALARIES_KEY);
            double salary = rs.getDouble(S_VALUE);
            if (!login.equals(previousLogin)) {
                salaries = new ConcurrentHashMap<>();
                Teacher teacher = new Teacher()
                        .withId(id)
                        .withLogin(login)
                        .withPwd(pwd)
                        .withSalt(salt)
                        .withName(name)
                        .withAge(age)
                        .withRole(role)
                        .withSalaries(salaries);
                result.add(teacher);
                previousLogin = login;
            }
            salaries.put(month, salary);
        }
        result.removeIf(teacher -> !Role.TEACHER.equals(teacher.getRole()));
        return result;
    }
}
