package dao;

import models.Reimbursement;
import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public interface ReimbursementDAO {
    public List<Reimbursement> readAllReimbursements();
    public List<Reimbursement> readReimbursementsByUser(User user);
    public List<Reimbursement> readPendingReimbursements();
    void createReimbursement(Reimbursement reimbursement);
    void changeReimbursementStatus(int id, int newStatus, User resolver);
}
