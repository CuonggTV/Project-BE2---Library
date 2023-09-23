package views;

import config.Mysql;
import utils.OperationHelper;

import java.sql.*;
import java.util.Scanner;

public class BookView {
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
            String query = "INSERT INTO book (id, bookName, author, borrewedFee, copiesOwned) " +
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
        do {
            System.out.println("Input the ID of the book you want to delete which MUST BE in list : ");
            id = sc.nextInt();
        } while(checkUniquenessOfID(id));
        delete(id);
    }
    public void showInfoOfAllBooks()
    {
        try {
            Connection connection = Mysql.getConnected();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from book");
            while (resultSet.next())
            {
                System.out.print(resultSet.getString("id") + "\t\t\t");
                System.out.print(resultSet.getString("bookName") + "\t\t\t");
                System.out.print(resultSet.getString("author") + "\t\t\t");
                System.out.print(resultSet.getString("borrewedFee") + "\t\t\t");
                System.out.println(resultSet.getString("copiesOwned") + "\n");
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
    }
}

