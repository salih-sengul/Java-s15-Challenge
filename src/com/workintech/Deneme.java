package com.workintech;

import com.workintech.library.Library;
import com.workintech.user.Admin;
import com.workintech.user.Librarian;
import com.workintech.user.User;

public class Deneme {
    public static void main(String[] args) {
        Admin admin = new Admin(1L, "ab", "12");
        User user = new Admin(2L, "bb", "11");
        User librarian = new Librarian(2L, "bb", "11");

        /*System.out.println(user.getClass());
        System.out.println(user instanceof User);
        System.out.println(user instanceof Admin);*/
       /* System.out.println(admin.getClass().getSimpleName());
        System.out.println(admin.getClass().getName());
        System.out.println(admin.getClass().getTypeName());*/

        /*System.out.println(librarian.getClass());
        System.out.println(librarian instanceof Librarian);
        System.out.println(librarian instanceof User);
        System.out.println(librarian instanceof Admin);*/

        //Library.listUsers();
        System.out.println(Library.findBook("bookName"));


    }
}
