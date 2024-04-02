package jm.task.core.jdbc;


import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {

    public static void main(String[] args) {
        UserServiceImpl service = new UserServiceImpl();

        service.createUsersTable();

        service.saveUser("Marina", "Salnikova", (byte) 23);
        service.saveUser("Ivan", "Semenov", (byte) 25);
        service.saveUser("Sergey", "Ivanov", (byte) 31);
        service.saveUser("Olga", "Sergeeva", (byte) 38);


        service.removeUserById(1);
        System.out.println(service.getAllUsers());

        service.cleanUsersTable();
        service.dropUsersTable();
    }
}

