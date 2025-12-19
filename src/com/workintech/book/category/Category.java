package com.workintech.book.category;

import com.workintech.book.Book;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public abstract class Category {
    private Long id;
    private String name;
    private Set<Book> books = new HashSet<>();
    private Integer numberOfBooks;

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public Integer getNumberOfBooks() {
        return numberOfBooks;
    }

    public void setNumberOfBooks() {
        this.numberOfBooks = books.size();
    }

    public String addBook(Book book) {
        if (books.add(book)) {

            return "Book added to " + this.getClass().getSimpleName() + " category";
        } else {
            return "Book was already in catergory";
        }
    }

    public String deleteBook(Book book) {
        if (books.remove(book)) {
            return "Book deleted from category:  " + this.getClass().getSimpleName();
        } else {
            return "There was no book";
        }
    }

    public TreeSet<Book> listBookAsc() {
        return new TreeSet<>(this.books);
    }

    public TreeSet<Book> listBookDesc() {
        TreeSet<Book> booksDesc = new TreeSet<>(Collections.reverseOrder());
        booksDesc.addAll(this.books);
        return booksDesc;
    }

    @Override
    public String toString() {
        return name;
    }
}
