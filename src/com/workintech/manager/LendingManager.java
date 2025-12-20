package com.workintech.manager;

import com.workintech.book.Book;
import com.workintech.library.Invoice;
import com.workintech.reader.Reader;
import com.workintech.util.IdGenerator;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LendingManager {

    private static LendingManager instance;
    private Map<Long, Invoice> invoices = new HashMap<>();
    private BookManager bookManager;
    private ReaderManager readerManager;

    private LendingManager() {
        this.bookManager = BookManager.getInstance();
        this.readerManager = ReaderManager.getInstance();
    }

    public static LendingManager getInstance() {
        if (instance == null) {
            instance = new LendingManager();
        }
        return instance;
    }

    public Map<Long, Invoice> getInvoices() {
        return invoices;
    }

    public String lendBook(Book book, Reader reader) {
        if (book.getAvailable()) {
            return "Kitap müsait değil";
        }

        if (reader.getBooks().size() >= reader.getBookLimit()) {
            return "Kullanıcı " + reader.getBookLimit() + " kitap limitini aştı";
        }

        if (reader.getBooks().add(book)) {
            book.setAvailable(false);

            Invoice invoice = new Invoice(IdGenerator.generateId(invoices), book.getId(), book.getPrice());
            invoices.put(invoice.getId(), invoice);

            book.setInvoice(invoice);
            reader.getInvoices().add(invoice);

            return "book added on the reader";
        } else {
            return "book already on the reader";
        }
    }

    public String returnBook(Book book, Reader reader) {
        if (reader.getBooks().remove(book)) {
            book.setAvailable(true);
            book.getInvoice().setEndDate(new Date());
            book.setInvoice(null);

            return "book returned";
        } else {
            return "user dosent have this book";
        }
    }
}
