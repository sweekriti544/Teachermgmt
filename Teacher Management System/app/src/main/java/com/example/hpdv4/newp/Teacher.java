package com.example.hpdv4.newp;

public class Teacher {

    private int id;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    private String username;
    private String address;
    private String faculty;

    public Teacher(int id, String username, String address, String faculty,String password) {
        this.id = id;
        this.username = username;
        this.address = address;
        this.faculty = faculty;
        this.password=password;
    }
}
