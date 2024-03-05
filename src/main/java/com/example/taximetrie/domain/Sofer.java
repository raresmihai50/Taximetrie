package com.example.taximetrie.domain;

public class Sofer extends Persoana{
    protected String indicativMasina;
    public Sofer(String username, String name, String indicativMasina){
        super(username,name);
        this.indicativMasina = indicativMasina;
    }

    public String getIndicativMasina() {
        return indicativMasina;
    }
}
