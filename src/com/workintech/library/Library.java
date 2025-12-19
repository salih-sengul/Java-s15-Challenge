package com.workintech.library;

import com.workintech.book.Book;
import com.workintech.book.category.Category;
import com.workintech.reader.Reader;
import com.workintech.user.Admin;
import com.workintech.user.Librarian;
import com.workintech.user.User;

import java.util.*;

public class Library {

    private static Map<Long,Book> books = new HashMap<>();
    private static Map<Long,Reader> readers = new HashMap<>();
    private static Map<Long, User> users = new HashMap<>();
    private static Map<Long, Invoice> invoices= new HashMap<>();
    private static Map<Long, Category> categories= new HashMap<>();

    private static Library instance = new Library();

    private Library(){
        Admin admin = new Admin(1L,"admin","123");
        Librarian librarian = new Librarian(2L,"veli","123");
        users.put(2L,librarian);
        users.put(admin.getId(),admin);
    }

    public static Library getInstance(){
        return instance;
    }

    public static Map<Long, Book> getBooks() {
        return books;
    }

    public static Map<Long, Reader> getReaders() {
        return readers;
    }


    public static Map<Long, User> getUsers() {

        return users;
    }

    public static Map<Long, Invoice> getInvoices() {
        return invoices;
    }

    public static Map<Long, Category> getCategories() {
        return categories;
    }


    public static Book findBook(Long id){
        return books.get(id);
    }

    public static Book findBook(String name){
        for(Map.Entry<Long,Book> entry: books.entrySet()){
            if(entry.getValue().getName().equals(name)){
                return entry.getValue();
            }
        }
        return null;
    }

    public static Book findBookAuthor(String authorName){
        for(Map.Entry<Long,Book> entry: books.entrySet()){
            if(entry.getValue().getName().equals(authorName)){
                return entry.getValue();
            }
        }
        return null;
    }

    public static String lendBook(Book book, Reader reader){

        if(reader.getBooks().add(book) && book.getAvailable()){
            book.setAvailable(false);

           Invoice invoice = new Invoice (book.getId(),book.getId(), book.getPrice());
           invoices.put(invoice.getId(),invoice);
           book.setInvoice(invoice);
           reader.getInvoices().add(invoice);

            return "book added on the reader";
        }else{
            return "book already on the reader";
        }
    }

    public static String returnBook(Book book, Reader reader){
        if(reader.getBooks().remove(book)){
            book.setAvailable(true);
            book.getInvoice().setEndDate(new Date());
            book.setInvoice(null);

            return "book returned";
        }else{
            return "user dosent have this book";
        }
    }

    /*Map<Long,Book>
    Map<Long,Reader>
    Map<Long, User>
    Map<Long, Invoice>
    Map<Long, Category>*/

    public static long getId(Map<Long,?> map){
         if(map.isEmpty()) return 1L;
         return Collections.max(map.keySet())+1;
    }

    public static List<Book> listByCategory(Category category){
       return category.getBooks().stream().toList();
    }

    public static List<Book> listByAuthor(String authorName){
        List<Book> bookList = new ArrayList<>();

        for(Map.Entry<Long,Book> entry: books.entrySet()){
            if(entry.getValue().getAuthor().equals(authorName)){
                bookList.add(entry.getValue());
            }
        }
        return bookList;
    }

    public static String addBook(Book book){
        if(books.put(getId(books),book)==null){
            return "Book added successfully";
        }else{
            return "id is occupied";
        }
    }

    public static String deleteBook(Book book){
        if(books.containsKey(book.getId())){
            books.remove(book.getId());
            return "book removed!";
        }else{
            return "book was not in the system";
        }
    }

   /* public static String updateBook(Book oldBook,Book newBook){
        if(books.containsKey(oldBook.getId())){
            books.put(oldBook.getId(),newBook);
            return "book updated";
        }else {
            return "No books founde";
        }
    }*/

    public static void listUsers(){
        for(Map.Entry<Long, User> entry: users.entrySet()){
            System.out.println("id: " + entry.getKey()+" | "+entry.getValue());

        }
    }




}
