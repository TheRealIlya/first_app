package by.academy.jee.dao.person.admin;

import by.academy.jee.dao.person.PersonDao;
import by.academy.jee.database.Database;
import by.academy.jee.model.person.Admin;

import java.util.List;

public class AdminDaoForMemoryDatabase implements PersonDao<Admin> {

    private static volatile AdminDaoForMemoryDatabase instance;

    private AdminDaoForMemoryDatabase() {
        //singleton
    }

    public static AdminDaoForMemoryDatabase getInstance() {
        if (instance == null) {
            synchronized (AdminDaoForMemoryDatabase.class) {
                if (instance == null) {
                    instance = new AdminDaoForMemoryDatabase();
                }
            }
        }
        return instance;
    }

    @Override
    public Admin create(Admin admin) {
        Database.getInstance().addAdmin(admin);
        return admin;
    }

    @Override
    public Admin read(int id) {
        return null; //TODO
    }

    @Override
    public Admin read(String name) {
        return Database.getInstance().getAdmin(name);
    }

    @Override
    public Admin update(Admin newAdmin) {
        return null; //TODO
    }

    @Override
    public boolean delete(String name) {
        return false; //TODO
    }

    @Override
    public List<Admin> readAll() {
        return null; //TODO
    }
}
