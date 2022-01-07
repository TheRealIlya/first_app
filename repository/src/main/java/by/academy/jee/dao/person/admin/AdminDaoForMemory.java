package by.academy.jee.dao.person.admin;

import by.academy.jee.dao.person.PersonDao;
import by.academy.jee.database.Database;
import by.academy.jee.model.person.Admin;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AdminDaoForMemory implements PersonDao<Admin> {

    private static volatile AdminDaoForMemory instance;

    private AdminDaoForMemory() {
        //singleton
    }

    public static AdminDaoForMemory getInstance() {
        if (instance == null) {
            synchronized (AdminDaoForMemory.class) {
                if (instance == null) {
                    instance = new AdminDaoForMemory();
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
