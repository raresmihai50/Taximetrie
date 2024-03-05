package com.example.taximetrie.controllers;

import com.example.taximetrie.domain.Comanda;
import com.example.taximetrie.domain.Persoana;
import com.example.taximetrie.domain.Sofer;
import com.example.taximetrie.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class TaximetristController {
    private Service serv;
    private Sofer sofer;
    @FXML
    Button btn_onoreaza;
    @FXML
    TextField txtfld_timp;
    @FXML
    TableView<Comanda> tbl_comenzi;
    @FXML
    TableColumn<Comanda, String> tblcol_nume;
    @FXML
    TableColumn<Comanda, String> tblcol_locatie;
    @FXML
    TextField txtfld_client_fidel;
    @FXML
    DatePicker data_comenzi;
    @FXML
    ListView<String> lst_comenzi_data;
    ObservableList<Comanda> obs_lst_com = FXCollections.observableArrayList();
    ObservableList<String> obs_lst_com_lst_data = FXCollections.observableArrayList();
    public void setTaximetristController(Service serv, Sofer sofer){
        this.serv = serv;
        this.sofer = sofer;
        setComenziTable();
        loadClientFidel();
        loadComenziListaData();
    }
    public void setComenziTable(){
        tblcol_locatie.setCellValueFactory(new PropertyValueFactory<Comanda,String>("locatie"));
        tblcol_nume.setCellValueFactory(new PropertyValueFactory<Comanda, String>("nume_persoana"));
        tbl_comenzi.setItems(obs_lst_com);
    }
    public void loadComenziTable(){
        List<Comanda> com = serv.findAllComenziLive();
        obs_lst_com.setAll(com);
        loadClientFidel();
    }
    public void loadComenziListaData(){
        List<Comanda> com = serv.findAllComenzi();
        LocalDate data = data_comenzi.getValue();
        obs_lst_com_lst_data.clear();
        lst_comenzi_data.setItems(obs_lst_com_lst_data);
        for(Comanda c : com){
            if (Objects.equals(c.getTaximetrist(), sofer) && Objects.equals(data, c.getData().toLocalDate())){
                obs_lst_com_lst_data.add(c.getPersoana().getNume());
            }
        }

    }
    public void handleOnoreazaComanda(){
        String timp = txtfld_timp.getText();
        //Comanda com_fara_sofer = tbl_comenzi.getSelectionModel().getSelectedItem();
        //trebuie try cu catch ca altfel da eroare urata daca e null Comanda
        try {
            Comanda com_fara_sofer = tbl_comenzi.getSelectionModel().getSelectedItem();

            if (com_fara_sofer != null) {
                serv.removeComandaLive(com_fara_sofer);
                com_fara_sofer.setSofer(sofer);//aici i-am setat sofer la comanda
                serv.addComandaLive(com_fara_sofer);
                serv.notifica_client(com_fara_sofer, timp);
            } else {
                MessageAlert.showErrorMessage(null,"Nu ai selectat nicio Comanda !");
            }

        } catch (NullPointerException e) {
            // Tratează excepția în cazul în care tbl_comenzi.getSelectionModel().getSelectedItem() returnează null
            System.out.println("Eroare: Elementul selectat este null.");
            e.printStackTrace();  // Aceasta afișează urmărirea stivei, pentru a ajuta la depanare
        }

    }
    public void loadClientFidel(){
        List <Comanda> comenzi = serv.findAllComenzi();
        // Utilizați un Map pentru a număra frecvența fiecărei persoane pentru un anumit șofer

        // Utilizați un Map pentru a număra frecvența fiecărei persoane pentru șoferul selectat
        Map<Persoana, Integer> frecventaPersoane = new HashMap<>();

        for (Comanda comanda : comenzi) {
            Sofer taximetrist = comanda.getTaximetrist();
            if (taximetrist != null && Objects.equals(comanda.getTaximetrist(), sofer)) {
                Persoana persoana = comanda.getPersoana();
                frecventaPersoane.put(persoana, frecventaPersoane.getOrDefault(persoana, 0) + 1);
            }
        }

        // Găsiți persoana cu cea mai mare frecvență pentru șoferul selectat
        Persoana persoanaMaxFidelitate = null;
        int frecventaMaxFidelitate = -1;

        for (Map.Entry<Persoana, Integer> entry : frecventaPersoane.entrySet()) {
            if (entry.getValue() > frecventaMaxFidelitate) {
                frecventaMaxFidelitate = entry.getValue();
                persoanaMaxFidelitate = entry.getKey();
            }
        }

        // Afișați rezultatul
        if (persoanaMaxFidelitate != null) {
            txtfld_client_fidel.setText(persoanaMaxFidelitate.getNume());
        } else {
            txtfld_client_fidel.setText("Nu există comenzi valide pentru a determina fidelitatea pentru acest sofer");
        }

    }

}
