package config;

import java.util.Objects;

public class Book {
    private int id;
    private String bookName;
    private String author;
    private float borrowedFee;
    private int copiesOwned;

    public Book(int id, String bookName, String author, float borrowedFee, int copiesOwned) {
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.borrowedFee = borrowedFee;
        this.copiesOwned = copiesOwned;
    }

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
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

    public String toString()
    {
        String result = "";

        result = "Name: " + this.bookName + "\nAuthor: "
                + this.author + "\nBorrow Fee: " + this.borrowedFee
                + "\nCopies Owned: " + this.copiesOwned;

        return result;
    }


    public boolean equals(Object obj)
    {
        if(obj == null)
        {
            return false;
        }
        if(this.getClass() != obj.getClass())
        {
            return false;
        }
        if(this == obj)
        {
            return true;
        }

        return Objects.equals(this.id, ((Book)obj).id);
    }
}
