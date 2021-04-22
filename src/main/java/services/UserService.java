package services;

import models.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    User getUserById(int id);
    List<User> getUsersByRole(String role);
    void createUser(User user);
    User getUserByUsername(String username);
}
