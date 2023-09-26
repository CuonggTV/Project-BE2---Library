package models;

import config.Mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoanList extends ArrayList<Loan> {
    public LoanList() {
    }

    public void getLoanList(int customerID) throws SQLException {
        String sql = "SELECT * FROM loan where customerID = " + customerID +" and status = 0" ;
        ResultSet resultSet = Mysql.statement.executeQuery(sql);

        while (resultSet.next()) {
            Loan loan = new Loan();
            loan.setId(resultSet.getInt("id"));
            loan.setBookID(resultSet.getInt("bookID"));
            loan.setLoanDate(resultSet.getString("loanDate"));
            loan.setReturnDate(resultSet.getString("returnDate"));
            loan.setStatus(resultSet.getBoolean("status"));
            this.add(loan);
        }
    }
    public void showList() throws SQLException {
        for(Loan loan: this){
            System.out.println("-----------------------------------------------------------------");
            System.out.print(loan.getId()+". ");
            System.out.println("You borrow: \""+ Book.getBookNameByID(loan.getBookID()) + "\"");
            System.out.println("    Return date: " + loan.getReturnDate());
            System.out.println("-----------------------------------------------------------------\n");
        }
    }
}
