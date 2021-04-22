package dao;


import models.Reimbursement;
import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//TODO: may need to refactor this to create full objects instead of just storing ids in the params of the reimbursement
public class ReimbursementDAOImpl extends DAO implements ReimbursementDAO {

//    public static void deleteReimbursement(int id){
//        try(Connection conn = DriverManager.getConnection(getUrl(),getUsername(), getPassword())){
//            String sql = "DELETE FROM reimbursements WHERE r_id = ?";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.executeUpdate();
//
//        }catch(SQLException e){
//            e.printStackTrace();
//        }
//    }
    private static UserDAO userDAO= new UserDAOImpl();
    public  List<Reimbursement> readResolvedReimbursements(){
        List<Reimbursement>  reimbursements = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(DAO.getUrl(), DAO.getUsername(), DAO.getPassword())){
            //this reads the currently resolved reimbursements
            String sql = "SELECT r_id, amount, submitted, resolved, description, \n" +
                    "(u1.u_id, u1.username, u1.password, u1.f_name, u1.l_name, u1.email, u1.role_id) AS author,\n" +
                    "(u1.u_id, u1.username, u1.password, u1.f_name, u1.l_name, u1.email, u1.role_id) AS resolver,\n" +
                    "(rs.status) AS status,\n" +
                    "(rt.\"type\") AS \"type\"\n" +
                    "FROM (reimbursements r1 JOIN \n" +
                    "(users u1 JOIN user_roles ur1 ON u1.role_id = ur1.role_id) ON r1.author = u1.u_id) \n" +
                    "JOIN \n" +
                    "(users u2 JOIN user_roles ur2 ON u2.role_id = ur2.role_id) ON r1.resolver = u2.u_id\n" +
                    "JOIN\n" +
                    "reimbursement_status rs ON r1.status_id = rs.status_id\n" +
                    "JOIN \n" +
                    "reimbursement_type rt ON r1.type_id = rt.type_id;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){


                System.out.println("hello");
                System.out.println(rs.getArray("author"));
                System.out.println(rs.getArray("resolver"));
                Reimbursement newReimbursement = new Reimbursement(
                    rs.getInt("r_id"),
                    rs.getInt("amount"),
                    rs.getTimestamp("submitted"),
                    rs.getTimestamp("resolved"),
                    rs.getString("description"),
                    //below here needs to be taken from the table
                    rs.getObject("author", User.class),
                    rs.getObject("resolver", User.class),
                    rs.getString("status_id"),
                    rs.getString("type_id")
            );
                reimbursements.add(newReimbursement);


            }
            return reimbursements;

        }catch(SQLException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    @Override
    public  void changeReimbursementStatus(int id, int newStatus, User resolver){
        try(Connection conn = DriverManager.getConnection(DAO.getUrl(), DAO.getUsername(), DAO.getPassword())){
            String sql = "UPDATE reimbursements SET status_id = ?, resolved = CURRENT_TIMESTAMP, resolver = ?  WHERE r_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, newStatus);
            ps.setInt(2, resolver.getU_id());
            ps.setInt(3, id);
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    @Override
    public void createReimbursement(Reimbursement reimbursement){
        try(Connection conn = DriverManager.getConnection(DAO.getUrl(), DAO.getUsername(), DAO.getPassword())){
            String sql = "INSERT INTO reimbursements(amount, submitted, description, author, status_id, type_id) VALUES(?,CURRENT_TIMESTAMP,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1,reimbursement.getAmount());

            ps.setString(2, reimbursement.getDescription());
            ps.setInt(3, reimbursement.getR_author().getU_id());

            //need to set the type to be correct
            ps.setInt(4, 0);
            if(reimbursement.getType().equals("lodging")){
                ps.setInt(5, 0);
            }else if(reimbursement.getType().equals("travel")){
                ps.setInt(5, 1);
            }else if(reimbursement.getType().equals("food")){
                ps.setInt(5, 2);
            }else if(reimbursement.getType().equals("other")){
                ps.setInt(5, 3);
            }else{
                ps.setInt(5, 3);
            }
            ps.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    @Override
    public  List<Reimbursement> readAllReimbursements(){
        List<Reimbursement>  reimbursements = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(DAO.getUrl(), DAO.getUsername(), DAO.getPassword())){
            //this reads the currently resolved reimbursements
            String sql = "SELECT r.r_id , r.amount , r.submitted , r.resolved , r.description, r.author, r.resolver , rs.status , rt.\"type\" FROM reimbursements r JOIN reimbursement_status rs ON r.status_id = rs.status_id \n" +
                    "JOIN \n" +
                    "reimbursement_type rt ON r.type_id = rt.type_id;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Reimbursement newReimbursement = new Reimbursement(
                        rs.getInt("r_id"),
                        rs.getInt("amount"),
                        rs.getTimestamp("submitted"),
                        rs.getTimestamp("resolved"),
                        rs.getString("description"),
                        //below here needs to be taken from the table
                        userDAO.readUserById(rs.getInt("author")),
                        userDAO.readUserById(rs.getInt("resolver")),
                        rs.getString("status"),
                        rs.getString("type")
                );
                reimbursements.add(newReimbursement);


            }
            return reimbursements;

        }catch(SQLException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    @Override
    public List<Reimbursement> readReimbursementsByUser(User user){
        if(user.getRole().equals("f_manager")){
            return new ArrayList<>();
        }else{
            List<Reimbursement>  reimbursements = new ArrayList<>();
            try(Connection conn = DriverManager.getConnection(DAO.getUrl(), DAO.getUsername(), DAO.getPassword())){
                //this reads the currently resolved reimbursements
                String sql = "SELECT r.r_id , r.amount , r.submitted , r.resolved , r.description, r.author, r.resolver , rs.status , rt.type FROM reimbursements r JOIN reimbursement_status rs ON r.status_id = rs.status_id JOIN reimbursement_type rt ON r.type_id = rt.type_id WHERE r.author = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, user.getU_id());
                ResultSet rs = ps.executeQuery();


                while(rs.next()){
                    Reimbursement newReimbursement = new Reimbursement(
                            rs.getInt("r_id"),
                            rs.getInt("amount"),
                            rs.getTimestamp("submitted"),
                            rs.getTimestamp("resolved"),
                            rs.getString("description"),
                            //below here needs to be taken from the table
                            userDAO.readUserById(rs.getInt("author")),
                            userDAO.readUserById(rs.getInt("resolver")),
                            rs.getString("status"),
                            rs.getString("type")
                    );
                    reimbursements.add(newReimbursement);


                }
                return reimbursements;

            }catch(SQLException e){
                e.printStackTrace();
            }
            return new ArrayList<>();
        }
    }
    @Override
    public List<Reimbursement> readPendingReimbursements(){
        List<Reimbursement>  reimbursements = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(DAO.getUrl(), DAO.getUsername(), DAO.getPassword())){
            //this reads the currently resolved reimbursements
            String sql = "SELECT r.r_id , r.amount , r.submitted , r.resolved , r.description, r.author, r.resolver , rs.status , rt.\"type\" FROM reimbursements r JOIN reimbursement_status rs ON r.status_id = rs.status_id \n" +
                    "JOIN \n" +
                    "reimbursement_type rt ON r.type_id = rt.type_id" +
                    "WHERE r.status_id = 0;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();


            while(rs.next()){
                Reimbursement newReimbursement = new Reimbursement(
                        rs.getInt("r_id"),
                        rs.getInt("amount"),
                        rs.getTimestamp("submitted"),
                        rs.getTimestamp("resolved"),
                        rs.getString("description"),
                        //below here needs to be taken from the table
                        userDAO.readUserById(rs.getInt("author")),
                        userDAO.readUserById(rs.getInt("resolver")),
                        rs.getString("status"),
                        rs.getString("type")
                );
                reimbursements.add(newReimbursement);
            }
            return reimbursements;

        }catch(SQLException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static void main(String[] args) {
        ReimbursementDAO reimbursementDAO = new ReimbursementDAOImpl();
        System.out.println(reimbursementDAO.readAllReimbursements());


    }




}
