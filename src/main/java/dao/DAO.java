package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//TODO: make a constructor in models that doesnt take id
public class DAO {
    private static String url = "jdbc:postgresql://batadase2.cktkynq8kju4.us-west-2.rds.amazonaws.com/proj1";
    private static String username = "postgres";
    private static String password = "p4ssw0rd";

//    private static String url = "jdbc:h2:./folder.theData";
//    private static String username = "sa";
//    private static String password = "sa";

    public static String getUrl() {
        return url;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    /**
     * set up the H2 database, should only be used in testing.
     * some dummy values present by default
     */
    public static void setupDB(){
        //load up h2 database
        try(Connection conn = DriverManager.getConnection(getUrl(),getUsername(), getPassword())){
            String sql = "CREATE TABLE user_roles(\n" +
                    "\trole_id INT PRIMARY KEY\n" +
                    "\t, role_name VARCHAR(10)\n" +
                    ");\n" +
                    "\n" +
                    "CREATE TABLE reimbursement_type(\n" +
                    "\ttype_id INT PRIMARY KEY\n" +
                    "\t,type VARCHAR(10)\n" +
                    ");\n" +
                    "\n" +
                    "CREATE TABLE reimbursement_status(\n" +
                    "\tstatus_id INT PRIMARY KEY\n" +
                    "\t,status VARCHAR(10)\n" +
                    ");\n" +
                    "\n" +
                    "CREATE TABLE users(\n" +
                    "\tu_id SERIAL PRIMARY KEY\n" +
                    "\t,username VARCHAR(50) UNIQUE\n" +
                    "\t,password VARCHAR(50)\n" +
                    "\t,f_name VARCHAR(100)\n" +
                    "\t,l_name VARCHAR(100)\n" +
                    "\t,email VARCHAR(150) UNIQUE \n" +
                    "\t,role_id INT REFERENCES user_roles (role_id)\n" +
                    ");\n" +
                    "\n" +
                    "CREATE TABLE reimbursements(\n" +
                    "\tr_id SERIAL PRIMARY KEY\n" +
                    "\t,amount NUMERIC(8, 2)\n" +
                    "\t,submitted TIMESTAMP \n" +
                    "\t,resolved TIMESTAMP\n" +
                    "\t,description VARCHAR(20)\n" +
                    "\t,author int REFERENCES users(u_id)\n" +
                    "\t,resolver int REFERENCES users(u_id)\n" +
                    "\t,status_id int REFERENCES reimbursement_status(status_id)\n" +
                    "\t,type_id int REFERENCES reimbursement_type(type_id)\n" +
                    ");" +
                    "INSERT INTO user_roles VALUES(0, \'f_manager\');\n" +
                    "INSERT INTO user_roles VALUES(1, \'employee\');\n" +
                    "INSERT INTO reimbursement_type VALUES(0, \'lodging\');\n" +
                    "INSERT INTO reimbursement_type VALUES(1, \'travel\');\n" +
                    "INSERT INTO reimbursement_type VALUES(2, \'food\');\n" +
                    "INSERT INTO reimbursement_type VALUES(3, \'other\');\n" +
                    "INSERT INTO reimbursement_status VALUES(0, \'pending\');\n" +
                    "INSERT INTO reimbursement_status VALUES(1, \'accepted\');\n" +
                    "INSERT INTO reimbursement_status VALUES(2, \'rejected\');" +
                    "INSERT INTO users VALUES(NULL, \'e_test\', \'password\', \'Devin\', \'Kadrie\', \'test@gmail.com\', 1);\n" +
                    "INSERT INTO users VALUES(NULL, \'f_test\', \'password\', \'Trevin\', \'anime\', \'test2@gmail.com\', 0);\n" +
                    "INSERT INTO users VALUES(NULL, \'user\', \'password\', \'John\', \'Gombar\', \'test3@gmail.com\', 1);\n" +
                    "INSERT INTO reimbursements VALUES(null, \n" +
                    "\t\t\t\t\t\t\t\t200, \n" +
                    "\t\t\t\t\t\t\t\tnull, \n" +
                    "\t\t\t\t\t\t\t\tnull, \n" +
                    "\t\t\t\t\t\t\t\t\'test description\',\n" +
                    "\t\t\t\t\t\t\t\t1,\n" +
                    "\t\t\t\t\t\t\t\t2,\n" +
                    "\t\t\t\t\t\t\t\t0,\n" +
                    "\t\t\t\t\t\t\t\t3);\n" +
                    "\n" +
                    "INSERT INTO reimbursements VALUES(null, \n" +
                    "\t\t\t\t\t\t\t\t500, \n" +
                    "\t\t\t\t\t\t\t\tnull, \n" +
                    "\t\t\t\t\t\t\t\tnull, \n" +
                    "\t\t\t\t\t\t\t\t\'test description 2\',\n" +
                    "\t\t\t\t\t\t\t\t1,\n" +
                    "\t\t\t\t\t\t\t\t2,\n" +
                    "\t\t\t\t\t\t\t\t1,\n" +
                    "\t\t\t\t\t\t\t\t2);\n" +
                    "INSERT INTO reimbursements VALUES(null, \n" +
                    "\t\t\t\t\t\t\t\t800, \n" +
                    "\t\t\t\t\t\t\t\tnull, \n" +
                    "\t\t\t\t\t\t\t\tnull, \n" +
                    "\t\t\t\t\t\t\t\t\'test description 3\',\n" +
                    "\t\t\t\t\t\t\t\t2,\n" +
                    "\t\t\t\t\t\t\t\t2,\n" +
                    "\t\t\t\t\t\t\t\t0,\n" +
                    "\t\t\t\t\t\t\t\t3);";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * remove the H2 database.
     * only use in testing
     */
    public static void teardownDB(){
        try(Connection conn = DriverManager.getConnection(getUrl(),getUsername(), getPassword())){
            String sql = "DROP TABLE reimbursements;" +
                    "DROP TABLE users;" +
                    "DROP TABLE reimbursement_status;" +
                    "DROP TABLE reimbursement_type;" +
                    "DROP TABLE user_roles;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

}
