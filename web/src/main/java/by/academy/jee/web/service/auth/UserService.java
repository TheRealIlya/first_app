package by.academy.jee.web.service.auth;

import by.academy.jee.dao.person.PersonDao;
import by.academy.jee.exception.DaoException;
import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.auth.UserPrincipal;
import by.academy.jee.model.person.Admin;
import by.academy.jee.model.person.Person;
import by.academy.jee.model.person.Student;
import by.academy.jee.model.person.Teacher;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import static by.academy.jee.constant.CommonConstant.REPOSITORY_PROPERTIES;
import static by.academy.jee.constant.ServiceConstant.ADMIN_PREFIX;
import static by.academy.jee.constant.ServiceConstant.NO_SUCH_USER_IN_DATABASE;
import static by.academy.jee.constant.ServiceConstant.STUDENT_PREFIX;
import static by.academy.jee.constant.ServiceConstant.TEACHER_PREFIX;

@Service
@PropertySource(REPOSITORY_PROPERTIES)
@Slf4j
public class UserService implements UserDetailsService {

    private final String type;

    private PersonDao<Admin> adminDao;
    private PersonDao<Teacher> teacherDao;
    private PersonDao<Student> studentDao;
    @Autowired
    private Map<String, PersonDao> personDaoMap;

    public UserService(@Value("${repository.type}") String type) {
        this.type = StringUtils.capitalize(type);
    }

    @PostConstruct
    private void init() {

        String adminDaoTitle = ADMIN_PREFIX + type;
        String teacherDaoTitle = TEACHER_PREFIX + type;
        String studentDaoTitle = STUDENT_PREFIX + type;
        adminDao = personDaoMap.get(adminDaoTitle);
        teacherDao = personDaoMap.get(teacherDaoTitle);
        studentDao = personDaoMap.get(studentDaoTitle);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person;
        try {
            person = adminDao.read(username);
        } catch (DaoException e) {
            try {
                person = teacherDao.read(username);
            } catch (DaoException f) {
                try {
                    person = studentDao.read(username);
                } catch (DaoException g) {
                    log.error("Error - no user {} in database", username);
                    throw new UsernameNotFoundException(NO_SUCH_USER_IN_DATABASE);
                }
            }
        }
        return new UserPrincipal(person);
    }
}
