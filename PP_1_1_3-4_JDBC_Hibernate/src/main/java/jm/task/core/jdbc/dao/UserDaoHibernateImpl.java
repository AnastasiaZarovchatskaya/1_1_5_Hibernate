package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }
    private Transaction transaction = null;

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {

            session.beginTransaction();
            String sql = "CREATE  TABLE IF NOT EXISTS Users (" +
                    "ID INT PRIMARY KEY AUTO_INCREMENT," +
                    "NAME VARCHAR (30) NOT NULL, LASTNAME VARCHAR(30) NOT NULL," +
                    "AGE INT)";
            session.createSQLQuery(sql).executeUpdate(); // выполняется запрос и сохраняется в базу данных
            session.getTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {

            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS Users").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            User user = new User(name,lastName,age);
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = null;

        try (Session session = Util.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
           list = session.createQuery("FROM User ", User.class).getResultList();
            transaction.commit(); // подстверждение транзакции и ее сохранение
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // откат назад
            }
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = Util.getSessionFactory().openSession()){

            transaction = session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE  Users").executeUpdate();
            transaction.commit();
        }catch (Exception e) {
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
