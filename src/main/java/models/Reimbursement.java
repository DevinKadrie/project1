package models;

import java.sql.Timestamp;


//TODO:
//      figure out how to store the blob reciept
public class Reimbursement {
    private int r_id;
    private double amount;

    //sql Timestamp
    private Timestamp submitted;

    //sql Timestamp
    private Timestamp resolved;

    private String description;
    //private ? receipt;
    private User r_author;
    private User r_resolver;
    private String status;
    private String type;

    public Reimbursement() {
    }

    public Reimbursement(int r_id, double amount, Timestamp submitted, Timestamp resolved, String description, User r_author, User r_resolver, String status, String type) {
        this.r_id = r_id;
        this.amount = amount;
        this.submitted = submitted;
        this.resolved = resolved;
        this.description = description;
        this.r_author = r_author;
        this.r_resolver = r_resolver;
        this.status = status;
        this.type = type;
    }

    public Reimbursement(double amount, Timestamp submitted, Timestamp resolved, String description, User r_author, User r_resolver, String status, String type) {
        this.amount = amount;
        this.submitted = submitted;
        this.resolved = resolved;
        this.description = description;
        this.r_author = r_author;
        this.r_resolver = r_resolver;
        this.status = status;
        this.type = type;
    }

    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Timestamp getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Timestamp submitted) {
        this.submitted = submitted;
    }

    public Timestamp getResolved() {
        return resolved;
    }

    public void setResolved(Timestamp resolved) {
        this.resolved = resolved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getR_author() {
        return r_author;
    }

    public void setR_author(User r_author) {
        this.r_author = r_author;
    }

    public User getR_resolver() {
        return r_resolver;
    }

    public void setR_resolver(User r_resolver) {
        this.r_resolver = r_resolver;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "\nReimbursement{" +
                "r_id=" + r_id +
                ", amount=" + amount +
                ", submitted=" + submitted +
                ", resolved=" + resolved +
                ", description='" + description + '\'' +
                ", r_author=" + r_author.getFirstName() + " " + r_author.getLastName()+
                ", r_resolver=" + r_resolver.getFirstName() + " " + r_resolver.getLastName()+
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
