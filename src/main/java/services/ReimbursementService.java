package services;

import models.Reimbursement;
import models.User;

import java.util.List;

public interface ReimbursementService {
    public List<Reimbursement> getAllReimbursements();
    public List<Reimbursement> getReimbursementsByUser(User user);
    public List<Reimbursement> getPendingReimbursements();
    public void createReimbursement(Reimbursement reimbursement);
    public void updateReimbursement(int id, String newStatus, User resolver);
}
