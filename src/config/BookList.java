package config;

import utils.OperationHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookList {
    private List<Book> books;

    public BookList() {
    }

//    public void addBook(Book book) {
//        if (this.books == null) {
//            this.books = new ArrayList<>();
//        }
//        this.books.add(book);
//    }

    boolean check = true;
    public boolean checkUniquenessOfID(int id)
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

    public void insertIntoReservationTable(String bookName, String author, float borrowedFee, int copiedOwned)
    {
        Scanner sc = new Scanner(System.in);
        String status;
        try {
            System.out.println("Input the status of your new book: ");
            status = sc.nextLine();
        } catch (Exception e) {}
        //Continue.....
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

        bookName = OperationHelper.inputFromKeyBoard(("Input Name of Book: "));
        author = OperationHelper.inputFromKeyBoard("Input Name of author: ");
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

    public void deleteBook(Book book) {
        if (this.books == null) {
            return;
        }
        if (this.books.contains(book)) {
            this.books.remove(book);
            if (this.books.isEmpty()) {
                this.books = null;
            }
        }
    }

    public void deleteBook(int id) throws RuntimeException {
        Book selectedBook = this.getBook(id);
        this.deleteBook(selectedBook);
    }

    public Book getBook(int id) throws RuntimeException {
        if (this.books == null) {
            throw new RuntimeException("Book list is not available!");
        }
        for (Book book : this.books) {
            if (book.getId() == id) {
                return book;
            }
        }
        throw new RuntimeException("A book with ID: " + id + " is not available!");
    }


    public void showAllBooksWithTheirStatus() {
        try {
            Connection connection = Mysql.getConnected();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT b.bookName, r.status FROM book b JOIN reservation r ON b.id = r.bookID");

            while (resultSet.next()) {
                System.out.print(resultSet.getString("bookName") + "\t");
                System.out.println(resultSet.getString("status"));
            }

            resultSet.close();
            statement.close();
            Mysql.closeConnection();
        } catch (SQLException e) {
            // Handle SQLException appropriately
            e.printStackTrace();
        }
    }
}
