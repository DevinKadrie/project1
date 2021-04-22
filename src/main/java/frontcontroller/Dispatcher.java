package frontcontroller;

import controller.ReimbursementController;
import controller.UserController;
import io.javalin.Javalin;
import io.javalin.http.Context;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Dispatcher {

    public Dispatcher(Javalin app){
        setupPaths(app);
    }

    private void setupPaths(Javalin app){
        //TODO: temp paths, make them more permanent when you get the front-end working
        setupUserPaths(app);

    }

    private static void setupUserPaths(Javalin app){
        app.routes(()->{
            //TODO: login should verify if they are a user and redirect them to different pages depending on if they are an employee
            //      or financial manager
           path("/login", ()->{
               post(UserController::login);
           });
//           path("/login", () ->{
//               get(UserController::);
//           });
           path("/register", () ->{
               //should be post
               post(UserController::register);
           });
           path("/api/reimbursements", () ->{
               //needs to let them logout
               get(UserController::getReimbursements);
           });
           path("/logout", () ->{
              get(UserController::logout);
           });
           path("/api/reimbursements", ()->{
               //create new reimbursement
              post(UserController::createReimbursement);
           });
           path("/api/reimbursements", ()->{
               //update old reimbursement
               put(UserController::updateReimbursement);
           });
        });
    }
}
