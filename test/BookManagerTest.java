import com.workintech.book.Book;
import com.workintech.book.category.Category;
import com.workintech.book.category.Novel;
import com.workintech.manager.BookManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookManagerTest {

    private BookManager bookManager;
    private Category testCategory;

    @BeforeEach
    void setUp() {
        // Reset BookManager instance before each test
        // Note: Since it's a singleton, we need to clear the books map manually
        bookManager = BookManager.getInstance();
        bookManager.getBooks().clear();
        testCategory = new Novel(1L, "Test Novel", "Fiction");
    }

    @Test
    void testAddBook() {
        Book book = new Book(1L, "Test Book", testCategory, 29.99, "Test Author");
        String result = bookManager.addBook(book);

        assertEquals("Book added successfully", result);
        assertEquals(1, bookManager.getBooks().size());
    }

    @Test
    void testFindBookById() {
        Book book = new Book(1L, "Test Book", testCategory, 29.99, "Test Author");
        bookManager.addBook(book);

        Book found = bookManager.findBook(1L);
        assertNotNull(found);
        assertEquals("Test Book", found.getName());
    }

    @Test
    void testFindBookByName() {
        Book book = new Book(1L, "Test Book", testCategory, 29.99, "Test Author");
        bookManager.addBook(book);

        Book found = bookManager.findBook("Test Book");
        assertNotNull(found);
        assertEquals(1L, found.getId());
    }

    @Test
    void testFindBookByAuthor() {
        Book book = new Book(1L, "Test Book", testCategory, 29.99, "Test Author");
        bookManager.addBook(book);

        Book found = bookManager.findBookByAuthor("Test Author");
        assertNotNull(found);
        assertEquals("Test Book", found.getName());
    }

    @Test
    void testDeleteBook() {
        Book book = new Book(1L, "Test Book", testCategory, 29.99, "Test Author");
        bookManager.addBook(book);

        String result = bookManager.deleteBook(book);
        assertEquals("book removed!", result);
        assertEquals(0, bookManager.getBooks().size());
    }

    @Test
    void testListByAuthor() {
        Book book1 = new Book(1L, "Book 1", testCategory, 29.99, "Author A");
        Book book2 = new Book(2L, "Book 2", testCategory, 39.99, "Author A");
        Book book3 = new Book(3L, "Book 3", testCategory, 19.99, "Author B");

        bookManager.addBook(book1);
        bookManager.addBook(book2);
        bookManager.addBook(book3);

        var books = bookManager.listByAuthor("Author A");
        assertEquals(2, books.size());
    }
}
