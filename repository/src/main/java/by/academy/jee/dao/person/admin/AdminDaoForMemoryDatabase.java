package by.academy.jee.dao.person.admin;

import by.academy.jee.dao.person.PersonDao;
import by.academy.jee.database.Database;
import by.academy.jee.model.person.Admin;

import java.util.List;

public class AdminDaoForMemoryDatabase implements PersonDao<Admin> {

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
        return Database.getAdmin(name);
    }

    @Override
    public boolean update(Admin newAdmin) {
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
}
