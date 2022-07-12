package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.ArrayList;
import java.util.List;
import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {

    private static final String createUsersTable = "CREATE TABLE IF NOT EXISTS users (id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(100) NOT NULL, lastName VARCHAR(100) NULL, age INT(3) NOT NULL, PRIMARY KEY (id))";
    private static final String dropUsersTable = "DROP TABLE IF EXISTS users";
    private static final String cleanUsersTable = "TRUNCATE TABLE users";
    private Transaction transaction = null;

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(createUsersTable).executeUpdate();
            transaction.commit();
            System.out.println("Таблица создана.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(dropUsersTable).executeUpdate();
            transaction.commit();
            System.out.println("Таблица удалена.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            System.out.println("Пользователь c именем " + name + " " + lastName + " был добавлен.");
        } catch (Exception e) {
//            if (transaction != null) {  Выбрасывается java.lang.IllegalStateException: org.hibernate.resource.jdbc.internal.LogicalConnectionManagedImpl@14f40030 is closed
//                transaction.rollback(); Если не закомментить
//            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
            System.out.println("Пользователь с id = " + id + " удален из таблицы.");
        } catch (Exception e) {
//            if (transaction != null) {  Выбрасывается java.lang.IllegalStateException: org.hibernate.resource.jdbc.internal.LogicalConnectionManagedImpl@14f40030 is closed
//                transaction.rollback(); Если не закомментить
//            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            allUsers = session.createQuery("from User", User.class).list();
            transaction.commit();
            System.out.println("Список пользователей готов: ");
        } catch (Exception e) {
//            if (transaction != null) {  Выбрасывается java.lang.IllegalStateException: org.hibernate.resource.jdbc.internal.LogicalConnectionManagedImpl@14f40030 is closed
//                transaction.rollback(); Если не закомментить
//            }
            e.printStackTrace();
        }
        return allUsers;
    }


    @Override
    public void cleanUsersTable() {
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(cleanUsersTable).executeUpdate();
            transaction.commit();
            System.out.println("Все строки удалены.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}

