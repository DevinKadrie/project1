package frontcontroller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import models.User;


public class FrontController {
    Javalin app;
    Dispatcher dispatcher;
    public FrontController(Javalin app){
        this.app = app;
        this.app.before("/E/*", FrontController::checkIsEmployee);
        this.app.before("/F/*", FrontController::checkIsFManager);
        this.app.before("/*", (context) ->{

            User user = context.sessionAttribute("user");
            if(user != null){
                System.out.println(user);
            }else{
                System.out.println(user);
            }

        });
        dispatcher = new Dispatcher(app);
    }
    //TODO: fill these in later, just have them here to remember i need them
    private static void checkIsEmployee(Context context){
        //if not employee, redirect to either login page, or f_manager homepage
        User user = context.sessionAttribute("user");
        //System.out.println(user);
        if(user == null){
            context.redirect("/login");
        }else if(user.getRole().equals("f_manager")){
            context.redirect("/F/reimbursements");
        }else{
            //context.sessionAttribute("user", null);
            //context.redirect("/login");
            System.out.println("logged in");
        }
    }
    private static void checkIsFManager(Context context){
        //if not f_manager, redirect to either login page, or employee homepage
        //if not employee, redirect to either login page, or f_manager homepage
        User user = context.sessionAttribute("user");
        if(user == null){
            context.redirect("/login");
        }else if(user.getRole().equals("employee") && context.path().contains("/F/")){
            context.redirect("/E/reimbursements");
        }else if(user.getRole().equals("f_manager") && context.path().contains("/E/")){
            context.redirect("/F/reimbursements");
        }
    }
    private static void checkIsLoggedIn(Context context, String username, String password){
        //call this in the other two to see if there is any login at all. if not, reroute to login page
    }
}
