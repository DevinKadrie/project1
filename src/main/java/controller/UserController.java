package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import models.Reimbursement;
import models.User;
import services.ReimbursementService;
import services.ReimbursementServiceImpl;
import services.UserService;
import services.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class UserController {
    private static UserService userService = new UserServiceImpl();
    private static ReimbursementService reimbursementService = new ReimbursementServiceImpl();
    public static void login(Context context) throws JsonProcessingException {

        String username = context.formParam("username");
        String password = context.formParam("password");

        User user = userService.getUserByUsername(username);
        context.sessionAttribute("user", user);
        //User user1 = context.sessionAttribute("user");
        if( user != null) {
            if (!password.equals(user.getPassword())) {
                context.result("no correct password");
                context.redirect("/login");
            }
            if (user.equals(new User())) {
                //user does not exist
                context.result("User does not exist");
                context.redirect("/login");
            } else {

                context.sessionAttribute("user", user);

                //valid login redirect to employee or f_manager homepage

                if (user.getRole().equals("employee")) {
                    //employee page
                    System.out.println("hello employee");
                    context.redirect("/E/reimbursements");
                } else if (user.getRole().equals("f_manager")) {
                    //manager page
                    System.out.println("hello manager");
                    context.redirect("/F/reimbursements");
                }
            }
        }
    }

    public static void register(Context context) {
        //get this info from the page
        //make sure the info from context is valid to put in the db.(do this in the service)
        User user = context.bodyAsClass(User.class);
        //System.out.println(userService.getUserByUsername(user.getUsername()));
        if (!userService.getUserByUsername(user.getUsername()).equals(new User())) {
            context.result("failed to register this account. ");
            //TODO: find a way to send that the resource was not created so i can display it in my page later
            context.redirect("/login");
        } else {
            userService.createUser(user);
            //context.result("Successfully registered account with username: " + user.getUsername() + " and password: " + user.getPassword());
            context.redirect("/login");
        }
    }
    public static void getReimbursements(Context context){
        //TODO
//        get session attribute username, then pull from db to access
//        if(type == f_manager) then get all of them and separate them into resolved/unresolved
//        if(type == employee) then get all of the reimbursements from the user
//        context.json(reimbursementService.getReimbursementsByUser());
        User user = context.sessionAttribute("user");
        if(user.equals(new User()) || user == null){
            context.redirect("/login");
        }
        if(((User)context.sessionAttribute("user")).getRole().equals("employee")){
            //get all reimbursements associated with this employee

            List<Reimbursement> reimbursements = reimbursementService.getReimbursementsByUser(user);
            context.json(reimbursements);
        }else if(((User)context.sessionAttribute("user")).getRole().equals("f_manager")){
            //get all reimbursements, and try to separate them by resolved/unresolved

            List<Reimbursement> reimbursements = reimbursementService.getAllReimbursements();

            context.json(reimbursements);



        }else{
            System.out.println("invalid employee type");
            context.redirect("/login");
        }


    }
    public static void updateReimbursement(Context context){
        System.out.println("in update");
        Reimbursement updated = context.bodyAsClass(Reimbursement.class);
        System.out.println(updated.getR_id());
        System.out.println(updated.getStatus());
        updated.setR_resolver(context.sessionAttribute("user"));
        reimbursementService.updateReimbursement(updated.getR_id(), updated.getStatus(), updated.getR_resolver());

        //context.redirect("/F/reimbursements");
    }

    public static void createReimbursement(Context context){
        System.out.println("creating new reimbursement");

        String amount = context.formParam("amount");
        String type = context.formParam("type");
        String description = context.formParam("description");

        int intAmount = Integer.parseInt(amount);
        Reimbursement reimbursement = new Reimbursement();
        reimbursement.setAmount(intAmount);
        reimbursement.setDescription(description);
        reimbursement.setR_author(context.sessionAttribute("user"));
        reimbursement.setStatus("pending");
        reimbursement.setType(type);

        reimbursement.setR_author(context.sessionAttribute("user"));
        //System.out.println(reimbursement);
        reimbursementService.createReimbursement(reimbursement);
        context.redirect("/E/reimbursements");
    }

    public static void logout(Context context){
        User user = context.sessionAttribute("user");
        context.sessionAttribute("user", null);
        context.redirect("");
    }
}
