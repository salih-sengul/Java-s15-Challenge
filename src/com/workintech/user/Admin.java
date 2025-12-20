package com.workintech.user;

public class Admin extends User {

    public Admin(Long id, String userName, String password) {
        super(id, userName, password);
    }

    @Override
    public String toString() {
        return "Admin" + super.toString();
    }
}
