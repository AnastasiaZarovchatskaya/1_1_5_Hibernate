package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        try (PreparedStatement preparedStatement =
                    Util.getConnection().prepareStatement(
                "CREATE  TABLE IF NOT EXISTS User (" +
                    "ID INT PRIMARY KEY AUTO_INCREMENT," +
                    "NAME VARCHAR (30) NOT NULL, LASTNAME VARCHAR(30) NOT NULL," +
                    "AGE INT)")) {

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Ошибка создания таблицы");
            e.getStackTrace();
        }

    }

    public void dropUsersTable() {
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement("DROP TABLE IF EXISTS User")){

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Ошибка при удалении таблицы" + e);
            e.getStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement("INSERT INTO User(NAME, LASTNAME, AGE) VALUES(?,?,?)")){

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate(); //сохранение в базу данных
        } catch (SQLException e) {
            System.out.println("Ошибка при добавление пользователя " + e);
            e.getStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement("DELETE FROM User WHERE ID = ?")){

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка при удаление пользователя" + e);
            e.getStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();

        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement("SELECT * FROM User");
             ResultSet resultSet = preparedStatement.executeQuery()){

                while (resultSet.next()) {
                    User user = new User();

                    user.setId(resultSet.getLong("ID"));
                    user.setName(resultSet.getString("NAME"));
                    user.setLastName(resultSet.getString("LASTNAME"));
                    user.setAge(resultSet.getByte("AGE"));

                    list.add(user);

                }

        } catch (SQLException e) {
            System.out.println("Ошибка при получении всех пользователей" + e);
            e.getStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement("TRUNCATE TABLE User")){

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении всех данных из таблицы");
            e.getStackTrace();
        }

    }
}
