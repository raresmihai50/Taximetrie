package com.example.taximetrie.domain;

public class Persoana extends Entity<Long>{
    protected String username;
    protected String nume;
    public Persoana(String username, String nume){
        this.username = username;
        this.nume = nume;
    }

    public String getNume() {
        return nume;
    }

    public String getUsername() {
        return username;
    }
}
