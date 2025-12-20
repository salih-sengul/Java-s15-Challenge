package com.workintech.manager;

import com.workintech.user.User;

import java.util.Map;

public class AuthenticationManager {

    private static AuthenticationManager instance;
    private UserManager userManager;

    private AuthenticationManager() {
        this.userManager = UserManager.getInstance();
    }

    public static AuthenticationManager getInstance() {
        if (instance == null) {
            instance = new AuthenticationManager();
        }
        return instance;
    }

    public String authenticate(String name, String password) {
        Long id = -1L;
        for (Map.Entry<Long, User> entry : userManager.getUsers().entrySet()) {
            if (name.equals(entry.getValue().getUserName())) {
                id = entry.getValue().getId();
                break;
            }
        }
        if (id == -1) {
            return "no users found";
        }
        User user = userManager.getUsers().get(id);

        if (user.getUserName().equals(name)) {
            if (user.getPassword().equals(password)) {
                return "user logged in";
            } else {
                return "wrong password";
            }
        } else {
            return "no users found";
        }
    }

    public String getUserRole(String username) {
        User user = userManager.findUser(username);
        if (user == null) {
            return null;
        }
        return user.getClass().getSimpleName();
    }
}
