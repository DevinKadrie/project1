package maindriver;
import frontcontroller.FrontController;
import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;

//TODO: make sure that you have two different services for the correct user type.
//      To fetch with header changes and to post, use extra fields in the fetch method
//      -this will be in the form of a JS object that contains the method and body and the header config

//TODO: make sure that you can actually filter the results sent to the users

//TODO: use session attribute to send warnings and create modals
public class main {
    public static void main(String[] args) {
        Javalin app = Javalin.create((config) ->{
            config.addStaticFiles("/client");
        }).start(9005);

        app.get("/hello", (context) ->{
           context.result("hello world").status(200);
        });

        FrontController fc = new FrontController(app);
    }
}
