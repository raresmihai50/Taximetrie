package com.example.taximetrie.service;

import com.example.taximetrie.controllers.ClientController;
import com.example.taximetrie.controllers.TaximetristController;
import com.example.taximetrie.domain.Comanda;
import com.example.taximetrie.domain.Persoana;
import com.example.taximetrie.domain.Sofer;
import com.example.taximetrie.repository.RepoComanda;
import com.example.taximetrie.repository.RepoPersoana;
import com.example.taximetrie.repository.RepoSofer;
import javafx.fxml.FXML;

import java.time.LocalDateTime;
import java.util.*;

public class Service {
    private RepoPersoana repo_pers;
    private RepoSofer repo_sofer;
    private RepoComanda repo_com;
    private List<ClientController> clientControllers = new ArrayList<>();
    private List<TaximetristController> taximetristControllers = new ArrayList<>();

    public Service(RepoPersoana repo_pers, RepoSofer repo_sofer, RepoComanda repo_com) {
        this.repo_pers = repo_pers;
        this.repo_sofer = repo_sofer;
        this.repo_com = repo_com;
    }

    public void test() {
        repo_com.findAll();
        repo_sofer.findAll();
        repo_pers.findAll();
    }

    public Sofer findSoferByUsername(String username) {
        return repo_sofer.findOneByUsername(username);
    }

    public Persoana findPersoanaByUsername(String username) {
        return repo_pers.findOneByUsername(username);
    }

    public void addTaximetristController(TaximetristController tax) {
        taximetristControllers.add(tax);
    }

    public void addClientController(ClientController pers) {
        clientControllers.add(pers);
    }

    public Comanda createComanda(Long id_client, Long id_sofer, String locatie, String nume_persoana) {
        Comanda com = new Comanda(repo_pers.findOne(id_client), repo_sofer.findOne(id_sofer), LocalDateTime.now(), locatie, nume_persoana);
        Random rand = new Random();
        com.setId(rand.nextLong(100000));
        return com;
    }

    public Optional<Comanda> saveComandaToDatabase(Comanda com) {
        return repo_com.save(com);
    }

    public List<Comanda> findAllComenzi() {
        return repo_com.findAll();
    }

    public List<Comanda> findAllComenziLive() {
        return repo_com.findAllComenziLive();
    }

    public Comanda createComandaFaraSofer(Long id_client, String locatie, String nume_persoana) {
        Comanda com = new Comanda(repo_pers.findOne(id_client), null, LocalDateTime.now(), locatie, nume_persoana);
        Random rand = new Random();
        com.setId(rand.nextLong(100000));
        return com;
    }

    public void addComandaLive(Comanda com) {
        repo_com.addComandaLive(com);
    }
    public void removeComandaLive(Comanda com){
        repo_com.removeComandaLive(com);
    }

    public void notifica_soferi() {
        for (TaximetristController tc : taximetristControllers) {
            tc.loadComenziTable();
        }
    }

    public void notifica_client(Comanda comCuSofer, String timp) {
        for(ClientController cc : clientControllers){
            if (Objects.equals(cc.pers.getId(), comCuSofer.getPersoana().getId())){
                cc.loadCursa(comCuSofer,timp);
            }
        }
    }
    public void addComandaInDataBase(Comanda comanda){
        repo_com.save(comanda);
    }
    public void acceptComanda(Persoana pers){
        for(Comanda cl : findAllComenziLive()){
            if(Objects.equals(cl.getPersoana().getId(), pers.getId())){
                saveComandaToDatabase(cl);
                removeComandaLive(cl);
                notifica_soferi();
                return;
            }
        }
    }
}
