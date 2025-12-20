package com.workintech.manager;

import com.workintech.user.Admin;
import com.workintech.user.Librarian;
import com.workintech.user.User;
import com.workintech.util.IdGenerator;

import java.util.HashMap;
import java.util.Map;

public class UserManager {

    private static UserManager instance;
    private Map<Long, User> users = new HashMap<>();

    private UserManager() {
        Admin admin = new Admin(1L, "admin", "123");
        Librarian librarian = new Librarian(2L, "veli", "123");
        users.put(2L, librarian);
        users.put(admin.getId(), admin);
    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public Map<Long, User> getUsers() {
        return users;
    }

    public User findUser(String username) {
        for (Map.Entry<Long, User> entry : users.entrySet()) {
            if (entry.getValue().getUserName().equals(username)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public void listUsers() {
        for (Map.Entry<Long, User> entry : users.entrySet()) {
            System.out.println("id: " + entry.getKey() + " | " + entry.getValue());
        }
    }

    public String createAdmin(Admin admin) {
        if (!users.containsKey(admin.getId())) {
            users.putIfAbsent(admin.getId(), admin);
            return "ekleme başarılı";
        } else {
            return "Bu id de user var";
        }
    }

    public String deleteAdmin(Admin admin) {
        if (users.containsKey(admin.getId())) {
            users.remove(admin.getId());
            return "silme başarılı";
        } else {
            return "Bu id de user yok";
        }
    }

    public String createLibrarian(Librarian librarian) {
        if (!users.containsKey(librarian.getId())) {
            users.putIfAbsent(librarian.getId(), librarian);
            return "ekleme başarılı";
        } else {
            return "Bu id de user var";
        }
    }

    public String deleteLibrarian(Librarian librarian) {
        if (users.containsKey(librarian.getId())) {
            users.remove(librarian.getId());
            return "silme başarılı";
        } else {
            return "Bu id de user yok";
        }
    }
}
