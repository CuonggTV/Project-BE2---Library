package models;

import config.Mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FineList extends ArrayList<Fine> {
    public FineList() {
    }

    public void getFineList(int customerID) throws SQLException {
        String sql = "SELECT * FROM fine where customerID = " + customerID;
        ResultSet resultSet = Mysql.statement.executeQuery(sql);

        while (resultSet.next()) {
            Fine fine = new Fine();
            fine.setId(resultSet.getInt("id"));
            fine.setLoanID(resultSet.getInt("loanID"));
            fine.setCustomerID(resultSet.getInt("customerID"));
            fine.setDeadline(resultSet.getString("deadline"));
            fine.setAmount(resultSet.getFloat("amount"));
            this.add(fine);
        }
    }
    public void showList() throws SQLException {
        for(Fine fine: this){
            System.out.println("-----------------------------------------------------------------");
            System.out.print(fine.getLoanID()+". ");
            System.out.println("Fine from returning \""+Book.getBookNameByID(Loan.getBookID_By_LoanID(fine.getLoanID()))+ "\" late");
            System.out.println("    Need to pay: " + fine.getAmount() + " $");
            System.out.println("    Deadline: " + fine.getDeadline());
            System.out.println("-----------------------------------------------------------------\n");
        }
    }
}
