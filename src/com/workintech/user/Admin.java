package com.workintech.user;

import com.workintech.library.Library;

import java.util.HashMap;
import java.util.Map;

public class Admin extends User{
    private Librarian librarian;
    private Map<Long,User> users = Library.getUsers();
    public Admin(Long id, String userName, String password) {
        super(id, userName, password);
    }

    public String createAdmin(Admin admin){

        if(!users.containsKey(admin.getId())){
            users.putIfAbsent(admin.getId(),admin);
            return "ekleme başarılı";
        }else{
            return "Bu id de user var";
        }
    }

    public String deleteAdmin(Admin admin){
        if(users.containsKey(admin.getId())){
            users.remove(admin.getId());
            return "silme başarılı";
        }else{
            return "Bu id de user yok";
        }
    }

    public String createLibrarian(Librarian librarian){
        if(!users.containsKey(librarian.getId())){
            users.putIfAbsent(librarian.getId(),librarian);
            return "ekleme başarılı";
        }else{
            return "Bu id de user var";
        }
    }
    public String deleteLibrarian(Librarian librarian){
        if(users.containsKey(librarian.getId())){
            users.remove(librarian.getId());
            return "silme başarılı";
        }else{
            return "Bu id de user yok";
        }
    }

    @Override
    public String toString() {
        return "Admin" + super.toString();
    }
}
