package by.academy.jee.dao.person;

import by.academy.jee.dao.DaoDataSource;
import by.academy.jee.dao.RepositoryType;
import by.academy.jee.dao.person.admin.AdminDaoForJpa;
import by.academy.jee.dao.person.admin.AdminDaoForMemory;
import by.academy.jee.dao.person.admin.AdminDaoForPostgres;
import by.academy.jee.dao.person.student.StudentDaoForJpa;
import by.academy.jee.dao.person.teacher.TeacherDaoForJpa;
import by.academy.jee.dao.person.teacher.TeacherDaoForMemory;
import by.academy.jee.dao.person.teacher.TeacherDaoForPostgres;
import by.academy.jee.model.person.role.Role;
import by.academy.jee.util.ThreadLocalForRepositoryType;
import static by.academy.jee.constant.Constant.ADMIN;
import static by.academy.jee.constant.Constant.STUDENT;
import static by.academy.jee.constant.Constant.TEACHER;

public class PersonDaoFactory {

//    private static DaoDataSource dataSource;
//    private static final RepositoryType TYPE;
//    private static final ThreadLocalForRepositoryType repositoryTypeHelper = ThreadLocalForRepositoryType.getInstance();
//
//    static {
//
//        TYPE = repositoryTypeHelper.get();
//        if (TYPE == RepositoryType.POSTGRES) {
//            dataSource = repositoryTypeHelper.getDataSource();
//        }
//    }
//
//    private PersonDaoFactory() {
//        //factory
//    }
//
//    public static PersonDao getPersonDao(Role role) {
//
//        switch (TYPE) {
//            case MEMORY:
//                return getMemoryDao(role);
//            case POSTGRES:
//                return getPostgresDao(role);
//            case JPA:
//            default:
//                return getJpaDao(role);
//        }
//    }
//
//    private static PersonDao getMemoryDao(Role role) {
//
//        switch (role.toString()) {
//            case ADMIN:
//                return AdminDaoForMemory.getInstance();
//            case TEACHER:
//                return TeacherDaoForMemory.getInstance();
//            case STUDENT:
//            default:
//                return null;
//        }
//    }
//
//    private static PersonDao getPostgresDao(Role role) {
//
//        switch (role.toString()) {
//            case ADMIN:
//                return AdminDaoForPostgres.getInstance(dataSource);
//            case TEACHER:
//                return TeacherDaoForPostgres.getInstance(dataSource);
//            case STUDENT:
//            default:
//                return null;
//        }
//    }
//
//    private static PersonDao getJpaDao(Role role) {
//
//        switch (role.toString()) {
//            case ADMIN:
//                return AdminDaoForJpa.getInstance();
//            case TEACHER:
//                return TeacherDaoForJpa.getInstance();
//            case STUDENT:
//            default:
//                return StudentDaoForJpa.getInstance();
//        }
//    }
}
