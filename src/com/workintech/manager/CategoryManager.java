package com.workintech.manager;

import com.workintech.book.Book;
import com.workintech.book.category.Category;
import com.workintech.util.IdGenerator;

import java.util.*;

public class CategoryManager {

    private static CategoryManager instance;
    private Map<Long, Category> categories = new HashMap<>();

    private CategoryManager() {
    }

    public static CategoryManager getInstance() {
        if (instance == null) {
            instance = new CategoryManager();
        }
        return instance;
    }

    public Map<Long, Category> getCategories() {
        return categories;
    }

    public String addCategory(Category category) {
        if (!categories.containsKey(category.getId())) {
            categories.put(category.getId(), category);
            return "Category added successfully";
        } else {
            return "Category with this ID already exists";
        }
    }

    public Category findCategory(Long id) {
        return categories.get(id);
    }

    public String addBookToCategory(Book book, Category category) {
        if (category.getBooks().add(book)) {
            return "Book added to " + category.getClass().getSimpleName() + " category";
        } else {
            return "Book was already in category";
        }
    }

    public String removeBookFromCategory(Book book, Category category) {
        if (category.getBooks().remove(book)) {
            return "Book deleted from category: " + category.getClass().getSimpleName();
        } else {
            return "There was no book";
        }
    }

    public TreeSet<Book> listBooksInCategoryAsc(Category category) {
        return new TreeSet<>(category.getBooks());
    }

    public TreeSet<Book> listBooksInCategoryDesc(Category category) {
        TreeSet<Book> booksDesc = new TreeSet<>(Collections.reverseOrder());
        booksDesc.addAll(category.getBooks());
        return booksDesc;
    }
}
