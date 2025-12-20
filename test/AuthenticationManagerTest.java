import com.workintech.manager.AuthenticationManager;
import com.workintech.manager.UserManager;
import com.workintech.user.Admin;
import com.workintech.user.Librarian;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationManagerTest {

    private AuthenticationManager authManager;
    private UserManager userManager;

    @BeforeEach
    void setUp() {
        authManager = AuthenticationManager.getInstance();
        userManager = UserManager.getInstance();
        userManager.getUsers().clear();

        // Add test users
        Admin admin = new Admin(1L, "admin", "123");
        Librarian librarian = new Librarian(2L, "veli", "456");
        userManager.getUsers().put(1L, admin);
        userManager.getUsers().put(2L, librarian);
    }

    @Test
    void testAuthenticateSuccess() {
        String result = authManager.authenticate("admin", "123");
        assertEquals("user logged in", result);
    }

    @Test
    void testAuthenticateWrongPassword() {
        String result = authManager.authenticate("admin", "wrongpass");
        assertEquals("wrong password", result);
    }

    @Test
    void testAuthenticateUserNotFound() {
        String result = authManager.authenticate("nonexistent", "123");
        assertEquals("no users found", result);
    }

    @Test
    void testGetUserRoleAdmin() {
        String role = authManager.getUserRole("admin");
        assertEquals("Admin", role);
    }

    @Test
    void testGetUserRoleLibrarian() {
        String role = authManager.getUserRole("veli");
        assertEquals("Librarian", role);
    }

    @Test
    void testGetUserRoleNotFound() {
        String role = authManager.getUserRole("nonexistent");
        assertNull(role);
    }
}
