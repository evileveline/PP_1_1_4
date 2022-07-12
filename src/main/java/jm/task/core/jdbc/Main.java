package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.util.Util;


public class Main {
    public static void main(String[] args) {

        UserDaoHibernateImpl userDao = new UserDaoHibernateImpl();

        userDao.createUsersTable();

        userDao.saveUser("Ivan", "Petrov", (byte) 20);
        userDao.saveUser("Alexey", "Stepanov", (byte) 25);
        userDao.saveUser("Oleg", "Popov", (byte) 31);
        userDao.saveUser("Artem", "Subbotin", (byte) 38);

        System.out.println(userDao.getAllUsers());

        userDao.getAllUsers();
        userDao.removeUserById(1);
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
