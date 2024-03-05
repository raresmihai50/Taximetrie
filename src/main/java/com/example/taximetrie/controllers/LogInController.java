package com.example.taximetrie.controllers;

import com.example.taximetrie.MainApplication;
import com.example.taximetrie.domain.Persoana;
import com.example.taximetrie.domain.Sofer;
import com.example.taximetrie.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LogInController {
    @FXML
    Button btn_login;
    @FXML
    TextField txtfld_login;
    Service serv;

    public void setService(Service serv) {
        this.serv = serv;
    }

    public void handleLogin(ActionEvent actionEvent) {
        String username = txtfld_login.getText();
        Sofer s = serv.findSoferByUsername(username);
        if (s != null) {
            showTaximetristApp(s);
        } else {
            Persoana p = serv.findPersoanaByUsername(username);
            if (p != null) {
                showClientApp(p);
            } else {
                MessageAlert.showErrorMessage(null, "Persoana cu acest username nu exista !");
            }
        }
    }

    private void showTaximetristApp(Sofer s) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("taximetrist-view.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setScene(scene);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Taximetrist: " + s.getNume());
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(scene);

            TaximetristController taximetristController = fxmlLoader.getController();
            taximetristController.setTaximetristController(serv,s);
            dialogStage.show();
            serv.addTaximetristController(taximetristController);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void showClientApp(Persoana p) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("client-view.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 500, 400);
            stage.setScene(scene);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Client: " + p.getNume());
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(scene);

            ClientController clientController = fxmlLoader.getController();
            clientController.setClientController(serv,p);
            dialogStage.show();
            serv.addClientController(clientController);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
