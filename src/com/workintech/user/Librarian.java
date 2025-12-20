package com.workintech.user;

public class Librarian extends User{

    public Librarian(Long id, String userName, String password) {
        super(id, userName, password);

    }

    @Override
    public String toString() {
        return "Librarian" + super.toString();
    }

}
