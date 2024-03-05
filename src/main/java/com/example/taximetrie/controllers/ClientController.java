package com.example.taximetrie.controllers;

import com.example.taximetrie.domain.Comanda;
import com.example.taximetrie.domain.Persoana;
import com.example.taximetrie.service.Service;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;

public class ClientController {
    Service serv;
    public Persoana pers;
    @FXML
    Button btn_cauta;
    @FXML
    TextField txtfld_cauta;
    @FXML
    TextField txtfld_masina;
    @FXML
    TextField txtfld_timp;
    public void setClientController(Service serv, Persoana pers){
        this.serv = serv;
        this.pers = pers;
        txtfld_masina.setEditable(false);
        txtfld_timp.setEditable(false);
    }
    public void handleCauta(){
        String locatie = txtfld_cauta.getText();
        Comanda com_fara_sofer = serv.createComandaFaraSofer(pers.getId(),locatie, pers.getNume());
        serv.addComandaLive(com_fara_sofer);
        serv.notifica_soferi();
    }

    public void loadCursa(Comanda comCuSofer, String timp) {
        txtfld_timp.setText(timp);
        txtfld_masina.setText(comCuSofer.getTaximetrist().getIndicativMasina());
    }
    public void handleCancel(){
        txtfld_masina.clear();
        txtfld_timp.clear();
    }
    public void handleAccept(){
        serv.acceptComanda(pers);
    }
}
