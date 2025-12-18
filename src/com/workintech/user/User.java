package com.workintech.user;

public abstract class User {
    private Long id;
    private String userName;
    private String password;

    public User(Long id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String changeUserName(String name){
        if(name != null){
            userName= name;
            return name;
        }else{
            return "Name cannot be null";
        }
    }

    public String changePassword(String password){
        if(password != null){
            this.password= password;
            return password;
        }else{
            return "password cannot be null";
        }
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


}
