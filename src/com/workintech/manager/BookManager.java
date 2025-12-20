package com.workintech.manager;

import com.workintech.book.Book;
import com.workintech.util.IdGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookManager {

    private static BookManager instance;
    private Map<Long, Book> books = new HashMap<>();

    private BookManager() {
    }

    public static BookManager getInstance() {
        if (instance == null) {
            instance = new BookManager();
        }
        return instance;
    }

    public Map<Long, Book> getBooks() {
        return books;
    }

    public Book findBook(Long id) {
        return books.get(id);
    }

    public Book findBook(String name) {
        for (Map.Entry<Long, Book> entry : books.entrySet()) {
            if (entry.getValue().getName().equals(name)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public Book findBookByAuthor(String authorName) {
        for (Map.Entry<Long, Book> entry : books.entrySet()) {
            if (entry.getValue().getAuthor().equals(authorName)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public List<Book> listByAuthor(String authorName) {
        List<Book> bookList = new ArrayList<>();

        for (Map.Entry<Long, Book> entry : books.entrySet()) {
            if (entry.getValue().getAuthor().equals(authorName)) {
                bookList.add(entry.getValue());
            }
        }
        return bookList;
    }

    public String addBook(Book book) {
        if (books.put(IdGenerator.generateId(books), book) == null) {
            return "Book added successfully";
        } else {
            return "id is occupied";
        }
    }

    public String deleteBook(Book book) {
        if (books.containsKey(book.getId())) {
            books.remove(book.getId());
            return "book removed!";
        } else {
            return "book was not in the system";
        }
    }
}
