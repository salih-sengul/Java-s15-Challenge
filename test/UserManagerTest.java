

import com.workintech.manager.UserManager;
import com.workintech.user.Admin;
import com.workintech.user.Librarian;
import com.workintech.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {

    private UserManager userManager;

    @BeforeEach
    void setUp() {
        // Reset UserManager instance before each test
        userManager = UserManager.getInstance();
        userManager.getUsers().clear();

        // Re-add default users
        Admin admin = new Admin(1L, "admin", "123");
        Librarian librarian = new Librarian(2L, "veli", "123");
        userManager.getUsers().put(1L, admin);
        userManager.getUsers().put(2L, librarian);
    }

    @Test
    void testCreateAdmin() {
        Admin newAdmin = new Admin(3L, "newadmin", "pass");
        String result = userManager.createAdmin(newAdmin);

        assertEquals("ekleme başarılı", result);
        assertEquals(3, userManager.getUsers().size());
    }

    @Test
    void testCreateAdminDuplicate() {
        Admin duplicateAdmin = new Admin(1L, "admin2", "pass");
        String result = userManager.createAdmin(duplicateAdmin);

        assertEquals("Bu id de user var", result);
        assertEquals(2, userManager.getUsers().size());
    }

    @Test
    void testDeleteAdmin() {
        Admin adminToDelete = new Admin(1L, "admin", "123");
        String result = userManager.deleteAdmin(adminToDelete);

        assertEquals("silme başarılı", result);
        assertEquals(1, userManager.getUsers().size());
    }

    @Test
    void testCreateLibrarian() {
        Librarian newLibrarian = new Librarian(3L, "newlib", "pass");
        String result = userManager.createLibrarian(newLibrarian);

        assertEquals("ekleme başarılı", result);
        assertEquals(3, userManager.getUsers().size());
    }

    @Test
    void testDeleteLibrarian() {
        Librarian librarianToDelete = new Librarian(2L, "veli", "123");
        String result = userManager.deleteLibrarian(librarianToDelete);

        assertEquals("silme başarılı", result);
        assertEquals(1, userManager.getUsers().size());
    }

    @Test
    void testFindUser() {
        User found = userManager.findUser("admin");

        assertNotNull(found);
        assertEquals("admin", found.getUserName());
        assertTrue(found instanceof Admin);
    }

    @Test
    void testFindUserNotFound() {
        User found = userManager.findUser("nonexistent");

        assertNull(found);
    }
}
