package services;

import dao.ReimbursementDAO;
import dao.ReimbursementDAOImpl;
import models.Reimbursement;
import models.User;

import java.util.List;

public class ReimbursementServiceImpl implements ReimbursementService{
    private static ReimbursementDAO reimbursementDAO = new ReimbursementDAOImpl();
    @Override
    public List<Reimbursement> getAllReimbursements(){
        return reimbursementDAO.readAllReimbursements();
    }
    @Override
    public List<Reimbursement> getReimbursementsByUser(User user){
        return reimbursementDAO.readReimbursementsByUser(user);
    }
    @Override
    public List<Reimbursement> getPendingReimbursements(){
        return reimbursementDAO.readPendingReimbursements();
    }
    @Override
    public void createReimbursement(Reimbursement reimbursement){
        reimbursementDAO.createReimbursement(reimbursement);
    }
    @Override
    public void updateReimbursement(int id, String newStatus, User resolver){
        int newStatusId = -1;
        if(newStatus.equals("rejected")){
            newStatusId = 1;
            reimbursementDAO.changeReimbursementStatus(id, newStatusId, resolver);

        }else if(newStatus.equals("accepted")){
            newStatusId = 2;
            reimbursementDAO.changeReimbursementStatus(id, newStatusId, resolver);
        }
        if(newStatusId == -1){
            System.out.println("not able to update");
        }

    }



}
