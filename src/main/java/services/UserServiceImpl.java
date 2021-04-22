package services;

import dao.UserDAO;
import dao.UserDAOImpl;
import models.User;

import java.util.List;

public class UserServiceImpl implements UserService{
    private static UserDAO userDAO = new UserDAOImpl();

    @Override
    public List<User> getUsers(){
        return userDAO.readUsers();
    }

    @Override
    public User getUserById(int id){
        return userDAO.readUserById(id);
    }

    @Override
    public List<User> getUsersByRole(String role){
        return userDAO.readUsersByRole(role);
    }

    @Override
    public void createUser(User user){
        userDAO.createUser(user);
    }

    @Override
    public User getUserByUsername(String username){
        return userDAO.readUserByUsername(username);
    }
}
