package views;

import config.Mysql;
import utils.OperationHelper;

import java.sql.*;
import java.util.Scanner;

public class AdminView {
    Scanner sc = new Scanner(System.in);

    public void showAllBooksWithTheirStatus() {
        String sql = "SELECT\n" +
                "    b.bookName AS book_name,\n" +
                "    CASE WHEN l.bookID IS NULL THEN 'Not' ELSE 'Borrowed' END AS status\n" +
                "FROM\n" +
                "    book b\n" +
                "LEFT JOIN\n" +
                "    loan l ON b.id = l.bookID;";
        try {

            ResultSet resultSet = Mysql.statement.executeQuery(sql);
            System.out.println("Book's Name\t\t\t\t\t" + "Status\n");
            while (resultSet.next()) {
                System.out.print(resultSet.getString("book_name") + "\t\t\t\t\t");
                System.out.println(resultSet.getString("status"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addBook() {
        Scanner sc = new Scanner(System.in);
        int id, copiesOwned;
        String bookName, author;
        float borrowedFee;
        String status;

        do {
            id = OperationHelper.inputInteger("Input ID: ");
            if (!checkUniquenessOfID(id))
            {
                System.out.println("Cannot input the existed book's ID! \nPlease input a new book's ID again: ");
            }
        } while (!checkUniquenessOfID(id));

        bookName = OperationHelper.inputString(("Input Name of Book: "));
        author = OperationHelper.inputString("Input Name of author: ");
        borrowedFee = OperationHelper.inputFloat("Input borrowed Fee: ");
        copiesOwned = OperationHelper.inputInteger("Input Copied Owned: ");

        try {
            Connection connection = Mysql.getConnected();
            Statement statement = connection.createStatement();
            String query = "INSERT INTO book (id, bookName, author, borrowedFee, copiesOwned) " +
                    "VALUES (" + id + ", '" + bookName + "', '" + author + "', " + borrowedFee + ", " + copiesOwned + ")";
            statement.executeUpdate(query);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Your new book with id: " + id + " has been added! ");
        this.showInfoOfAllBooks();
    }

    private boolean checkUniquenessOfID(int id)
    {
        try
        {
            Connection connection = Mysql.getConnected();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select id from book where id = " + id);

            if (resultSet.next())
            {
                return false;
            }
        } catch (SQLException e) {}
        return true;
    }

    private void delete(int id) {
        String sql = "DELETE FROM book WHERE id = ?";
        try {
            PreparedStatement statement = Mysql.connection.prepareStatement(sql);
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("A book with ID: " + id + " has been deleted!");
                showInfoOfAllBooks();
            } else {
                System.out.println("No book found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteBook()
    {
        showInfoOfAllBooks();
        int id = 0;
        String input;
        do {
            System.out.println("Input the ID of the book you want to delete which MUST BE in list : ");
            input = OperationHelper.inputString("Input the ID of the book you want to delete which MUST BE in list : ");
            if (OperationHelper.isNumeric(input,"Must be a number or none.")){
                id = Integer.parseInt(input);
            }
            else if(input.equals("none")){
                return;
            }
        } while(checkUniquenessOfID(id));
        delete(id);
    }
    public void showInfoOfAllBooks()
    {
        try {
            Connection connection = Mysql.getConnected();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from book b");
            while (resultSet.next())
            {
                System.out.println("+------------------------------------------------------------------+");
                System.out.print("ID: " + resultSet.getString("id") + "\t");
                System.out.println("Name of book: " + resultSet.getString("bookName"));
                System.out.print("Author: " + resultSet.getString("author") + "\t\t\t");
                System.out.print("Borrowed Fee: " + resultSet.getString("borrowedFee") + "\t\t\t\n");
                System.out.println("Copies Owned: " + resultSet.getString("copiesOwned"));
                System.out.println("+------------------------------------------------------------------+\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //Show Customer And Their Status (borrrowed or Not)
    public void showAllCustomerAndTheirStatus()
    {
        String sql = "SELECT\n" +
                "    c.firstName AS customer_name,\n" +
                "    CASE WHEN r.customerID IS NULL THEN 'Not' ELSE 'Borrowed' END AS status\n" +
                "FROM\n" +
                "    Customer c\n" +
                "LEFT JOIN\n" +
                "    Reservation r ON c.id = r.customerID;";
        try {
            ResultSet resultSet = Mysql.statement.executeQuery(sql);
            System.out.println("Customer Name\t\t\tStatus");
            while (resultSet.next()) {
                String customerName = resultSet.getString("customer_name");
                String status = resultSet.getString("status");

                System.out.printf("%s\t\t\t\t\t%s\n", customerName, status);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        int choice = 0;
        System.out.println("Do you want to see more detail?\n1. Yes\n2.No");
        choice = OperationHelper.inputIntegerWithRange("Input your choice: ", 1, 2);
        switch (choice)
        {
            case 1:
                showDetail();
                break;
            case 2:
                break;
        }
    }

    //show customer with fine
    public void showAllCustomerWithFine()
    {
        int i = 0;
        String sql = "select c.firstName, c.middleName, c.lastName, c.phoneNumber, f.deadline, f.amount from customer c join fine f on c.id = f.customerID";
        try {
            ResultSet resultSet = Mysql.statement.executeQuery(sql);
            while (resultSet.next()) {
                System.out.println(++i + "/");
                System.out.println("+---------------------------------------------------------------------------------------------------------------+");
                System.out.println("Customer: " + resultSet.getString("firstName") + " " + resultSet.getString("middleName") + " " + resultSet.getString("lastName") + "\t\t_Phone Number: " + resultSet.getString("phoneNumber"));
                System.out.println("Deadline: " + resultSet.getString("deadline") + "  \t_Amount: " + resultSet.getString("amount"));
                System.out.println("+---------------------------------------------------------------------------------------------------------------+\n");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void showAllCustomerWithLoan()
    {
        int i = 0;
        String sql = "select c.firstName, c.middleName, c.lastName, c.phoneNumber, l.loanDate, l.returnDate from customer c join loan l on c.id = l.customerID";
        try {
            ResultSet resultSet = Mysql.statement.executeQuery(sql);
            while (resultSet.next()) {
                System.out.println(++i + "/");
                System.out.println("+---------------------------------------------------------------------------------------------------------------+");
                System.out.println("_Customer: " + resultSet.getString("firstName") + " " + resultSet.getString("middleName") + " " + resultSet.getString("lastName") + "\t\t_Phone Number: " + resultSet.getString("phoneNumber"));
                System.out.println("_Loan Date: " + resultSet.getString("loanDate") + " \t\t_Return Date: " + resultSet.getString("returnDate"));
                System.out.println("+---------------------------------------------------------------------------------------------------------------+\n");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void showAllCustomerWithReservation()
    {
        int i = 0;
        String sql = "select c.firstName, c.middleName, c.lastName, c.phoneNumber, r.status, r.dateCreated from customer c join reservation r on c.id = r.customerID";
        try {
            ResultSet resultSet = Mysql.statement.executeQuery(sql);
            while (resultSet.next()) {
                System.out.println(++i + "/");
                System.out.println("+---------------------------------------------------------------------------------------------------------------+");
                System.out.println("Customer: " + resultSet.getString("firstName") + " " + resultSet.getString("middleName") + " " + resultSet.getString("lastName") + "\t\t_Phone Number: " + resultSet.getString("phoneNumber"));
                System.out.println("Status: " + resultSet.getString("status") + "  \t_Date Created: " + resultSet.getString("dateCreated"));
                System.out.println("+---------------------------------------------------------------------------------------------------------------+\n");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void addBookAmount() {
        showInfoOfAllBooks();
        System.out.println("Input the ID of the book that you want to add its amount: ");
        int id = sc.nextInt();
        String sql = "SELECT copiesOwned, bookName FROM book WHERE id = " + id;
        try {
            ResultSet resultSet3 = Mysql.statement.executeQuery(sql);
            if (resultSet3.next()) {
                int copiesOwned = resultSet3.getInt("copiesOwned");
                String bookName = resultSet3.getString("bookName");
                System.out.println("+---------------------------------------------------------------------------------------------------------------+");
                System.out.println("Current amount of book with ID " + id + " is " + copiesOwned);
                AmountOfBook(id, copiesOwned, bookName);
                System.out.println("+---------------------------------------------------------------------------------------------------------------+\n");
            } else {
                System.out.println("Book with ID " + id + " does not exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void AmountOfBook(int id, int currentCopiesOwned, String bookName) {

        int amountOfBook = 0;
        try {
            do {
                System.out.println("Input the amount of book you want to add: ");
                amountOfBook = sc.nextInt();
            } while (amountOfBook < 0);
            int newCopiesOwned = currentCopiesOwned + amountOfBook;
            String updateSql = "UPDATE book SET copiesOwned = " + newCopiesOwned + " WHERE id = " + id;
            int rowsAffected = Mysql.statement.executeUpdate(updateSql);

            if (rowsAffected > 0) {
                System.out.println("New amount of book with ID " + id + " (" + bookName + ") is " + newCopiesOwned);
            } else {
                System.out.println("Update failed. Please check the ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showDetail()
    {
        String sql = "SELECT t.firstName as firstName, GROUP_CONCAT(t.bookName SEPARATOR '\\n') AS books\n" +
                "FROM (\n" +
                "    SELECT customer.firstName, book.bookName\n" +
                "    FROM loan\n" +
                "    INNER JOIN customer ON loan.customerID = customer.id\n" +
                "    INNER JOIN book ON loan.bookID = book.id\n" +
                "    ORDER BY customer.firstName\n" +
                ") t\n" +
                "GROUP BY t.firstName;";
        try {
            ResultSet rs = Mysql.statement.executeQuery(sql);
            while (rs.next()) {
                String customerName = rs.getString("firstName");
                String books = rs.getString("books");

                // Hiển thị thông tin khách hàng và sách
                System.out.println("Customer Name: " + customerName);
                System.out.println("Books: \n" + books);
                System.out.println("-----------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

