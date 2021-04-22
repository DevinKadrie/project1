package dao;

import models.User;

import java.util.List;

public interface UserDAO{
    List<User> readUsers();
    User readUserById(int id);
    List<User> readUsersByRole(String type);
    void createUser(User user);
    User readUserByUsername(String username);
}
