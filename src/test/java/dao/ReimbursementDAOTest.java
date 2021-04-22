package dao;

import models.Reimbursement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReimbursementDAOTest {
    @BeforeEach
    void setUp() {
        DAO.setupDB();
    }

    @AfterEach
    void tearDown() {
        DAO.teardownDB();
    }

    @Test
    void readReimbursements() {
        List<Reimbursement> result = ReimbursementDAOImpl.readReimbursements();
        List<Reimbursement> expected = new ArrayList<>();


        expected.add(new Reimbursement(1, 200, null, null, "test description", 1, 2, "pending", "travel"));
        expected.add(new Reimbursement(2, 500, null, null, "test description 2", 1, 2, "accepted", ""));
        expected.add(new Reimbursement(3, 800, null, null, "test description 3", 2, 2, 0, 3));
        for(Reimbursement r : expected){
            System.out.println(r);
        }
        assertEquals(expected.size(), result.size());
        for(int i = 0; i < expected.size(); i++){
            Reimbursement currentExpected;
            Reimbursement currentResult;
            currentExpected = expected.get(i);
            currentResult = result.get(i);
            assertEquals(currentExpected.getAmount(), currentResult.getAmount());
            assertEquals(currentExpected.getR_id(), currentResult.getR_id());
            assertEquals(currentExpected.getSubmitted(), currentResult.getSubmitted());
            assertEquals(currentExpected.getResolved(), currentResult.getResolved());
            assertEquals(currentExpected.getDescription(), currentResult.getDescription());
            assertEquals(currentExpected.getR_author(), currentResult.getR_author());
            assertEquals(currentExpected.getR_resolver(), currentResult.getR_resolver());
            assertEquals(currentExpected.getStatus(), currentResult.getStatus());
            assertEquals(currentExpected.getType(), currentResult.getType());
        }
    }

    @Test
    void deleteReimbursement() {

    }

    @Test
    void changeReimbursementStatus() {
    }

}