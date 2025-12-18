package com.workintech.user;

import com.workintech.library.Library;

import java.util.Map;

public class Athentication {
    private String name;
    private String password;

    public Athentication(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String checkNamePassword(String name, String password){
        Long id=-1L;
        for(Map.Entry<Long,User> entry: Library.getUsers().entrySet()){
            if(name.equals(entry.getValue().getUserName())){
               id =  entry.getValue().getId();
               break;
            }
        }
        if(id==-1){
            return "no users found";
        }
        User user = Library.getUsers().get(id);

        if(user.getUserName().equals(name)){
            if(user.getPassword().equals(password)){
                return "user logged in";
            }else {
                return "wrong password";
            }
        }else {
            return "no users found";
        }
    }
}
