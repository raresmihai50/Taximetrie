package com.example.taximetrie.domain;

import java.time.LocalDateTime;

public class Comanda extends Entity<Long>{
    Persoana persoana;
    Sofer taximetrist;
    LocalDateTime data;
    String locatie;
    String nume_persoana;

    public Comanda(Persoana persoana, Sofer taximetrist, LocalDateTime data, String locatie, String nume_persoana){
        this.persoana = persoana;
        this.taximetrist = taximetrist;
        this.data = data;
        this.locatie = locatie;
        this.nume_persoana = nume_persoana;
    }

    public LocalDateTime getData() {
        return data;
    }

    public Persoana getPersoana() {
        return persoana;
    }

    public Sofer getTaximetrist() {
        return taximetrist;
    }

    public String getLocatie(){return locatie;}
    public String getNume_persoana(){return nume_persoana;}
    public void setSofer(Sofer sofer){
        this.taximetrist = sofer;
    }
}
