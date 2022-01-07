package by.academy.jee.dao.group;

import by.academy.jee.dao.DaoDataSource;
import by.academy.jee.dao.RepositoryType;
import by.academy.jee.util.ThreadLocalForRepositoryType;

public class GroupDaoFactory {

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
//    private GroupDaoFactory() {
//        //factory
//    }
//
//    public static GroupDao getGroupDao() {
//
//        switch (TYPE) {
//            case MEMORY:
//
//            case POSTGRES:
//
//            case JPA:
//            default:
//                return GroupDaoForJpa.getInstance();
//        }
//    }
}
