package com.workintech.reader;


import com.workintech.book.Book;
import com.workintech.library.Invoice;

import java.util.HashSet;
import java.util.Set;

public class Reader{
    private Long id;
    private String name;
    private String contactInfo;
    private static final Integer bookLimit = 5;
    private Set<Book> books = new HashSet<>();
    private Set<Invoice> invoices = new HashSet<>();

    public Reader(Long id, String name, String contactInfo) {
        this.id = id;
        this.name = name;
        this.contactInfo = contactInfo;
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

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }
}
