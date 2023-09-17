package config;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookList {
    private List<Book> books;

    public BookList() {
    }

    public void addBook(Book book) {
        if (this.books == null) {
            this.books = new ArrayList<>();
        }
        this.books.add(book);
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
