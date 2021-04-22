package dao;

import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl extends DAO implements UserDAO {
    @Override
    public List<User> readUsers(){
        try(Connection conn = DriverManager.getConnection(getUrl(),getUsername(), getPassword())){
            List<User> users = new ArrayList<>();
            String sql = "SELECT u1.u_id, u1.username, u1.password, u1.f_name, u1.l_name, u1.email, ur.role_name FROM users u1 JOIN user_roles ur ON u1.role_id = ur.role_id\n; \n";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                users.add(
                        new User(rs.getInt("u_id"),
                                rs.getString("username"),
                                rs.getString("password"),
                                rs.getString("f_name"),
                                rs.getString("l_name"),
                                rs.getString("email"),
                                //take role from lookup table
                                rs.getString("role_name"))
                );
            }
            return users;


        }catch(SQLException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    @Override
    public User readUserByUsername(String username){
        try(Connection conn = DriverManager.getConnection(getUrl(),getUsername(), getPassword())){
            String sql = "SELECT u1.u_id, u1.username, u1.password, u1.f_name, u1.l_name, u1.email, ur.role_name FROM users u1 JOIN user_roles ur ON u1.role_id = ur.role_id WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                return new User(rs.getInt("u_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("f_name"),
                        rs.getString("l_name"),
                        rs.getString("email"),
                        //take role from lookup table
                        rs.getString("role_name"));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return new User();
    }
    @Override
    public User readUserById(int id){
        try(Connection conn = DriverManager.getConnection(getUrl(),getUsername(), getPassword())){
            String sql = "SELECT u1.u_id, u1.username, u1.password, u1.f_name, u1.l_name, u1.email, ur.role_name FROM users u1 JOIN user_roles ur ON u1.role_id = ur.role_id WHERE u_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                return new User(rs.getInt("u_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("f_name"),
                        rs.getString("l_name"),
                        rs.getString("email"),
                        //take role from lookup table
                        rs.getString("role_name"));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return new User();
    }
    @Override
    public List<User> readUsersByRole(String type){
        try(Connection conn = DriverManager.getConnection(getUrl(),getUsername(), getPassword())){
            List<User> users = new ArrayList<>();
            String sql = "SELECT u1.u_id, u1.username, u1.password, u1.f_name, u1.l_name, u1.email, ur.role_name FROM users u1 JOIN user_roles ur ON u1.role_id = ur.role_id WHERE role_name = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                users.add(
                        new User(rs.getInt("u_id"),
                                rs.getString("username"),
                                rs.getString("password"),
                                rs.getString("f_name"),
                                rs.getString("l_name"),
                                rs.getString("email"),
                                //take role from lookup table
                                rs.getString("role_name"))
                );
            }
            return users;


        }catch(SQLException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    //takes a new user without an id and places it into the db
    @Override
    public void createUser(User user){
        try(Connection conn = DriverManager.getConnection(getUrl(),getUsername(), getPassword())){
            String sql = "INSERT INTO users (username, password, f_name, l_name, email, role_id)" +
                    "VALUES(?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());
            ps.setString(5, user.getEmail());
            if(user.getRole().equals("employee")) {
                ps.setInt(6, 1);
            }else if(user.getRole().equals("f_manager")){
                ps.setInt(6,0);
            }
            ps.executeUpdate();



        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //System.out.println(readUsers());
        //User user = new User("user4", "password", "Chris", "Bonner", "CBonner@example.com", "f_manager");
        //createUser(user);
        //System.out.println(readUsers());
        //System.out.println(readUsersByRole("f_manager"));

    }
}
