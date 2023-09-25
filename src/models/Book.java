package models;

import config.Mysql;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Book {
    private int id;
    private String name;
    private String author;
    private float borrowedFee;
    private int copiesOwned;

    public Book() {
    }

    public Book(int id, String name, String author, float borrowedFee, int copiesOwned) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.borrowedFee = borrowedFee;
        this.copiesOwned = copiesOwned;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public float getBorrowedFee() {
        return borrowedFee;
    }

    public void setBorrowedFee(float borrowedFee) {
        this.borrowedFee = borrowedFee;
    }

    public int getCopiesOwned() {
        return copiesOwned;
    }

    public void setCopiesOwned(int copiesOwned) {
        this.copiesOwned = copiesOwned;
    }

    public void setBookByID(int bookID) throws SQLException {
        String sqlString = "select * from book where id = "+ bookID ;
        ResultSet resultSet = Mysql.statement.executeQuery(sqlString);

        while (resultSet.next()) {
            id = resultSet.getInt("id");
            name = resultSet.getString("name");
            author = resultSet.getString("author");
            borrowedFee = resultSet.getFloat("borrowedFee");
            copiesOwned = resultSet.getInt("copiesOwned");
        }
    }
}
