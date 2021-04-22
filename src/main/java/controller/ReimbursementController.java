package controller;

import io.javalin.http.Context;
import services.ReimbursementService;
import services.ReimbursementServiceImpl;


public class ReimbursementController {
    private static ReimbursementService reimbursementService = new ReimbursementServiceImpl();
    public static void getReimbursements(Context context){
        context.json(reimbursementService.getAllReimbursements());
    }
}
