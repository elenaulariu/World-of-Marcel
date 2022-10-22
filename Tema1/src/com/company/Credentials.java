package com.company;

// Implementam clasa folosind principiul incapsularii
public class Credentials {
    private String email, password;

    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void set(String s1, String s2) {
        email = s1;
        password = s2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String s) {
        email = s;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String s) {
        password = s;
    }
}