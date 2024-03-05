package com.example.taximetrie;

import com.example.taximetrie.controllers.LogInController;
import com.example.taximetrie.domain.Comanda;
import com.example.taximetrie.repository.RepoComanda;
import com.example.taximetrie.repository.RepoPersoana;
import com.example.taximetrie.repository.RepoSofer;
import com.example.taximetrie.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        String url = "jdbc:postgresql://localhost:5432/taximetrie";
        String user = "postgres";
        String pass = "raresmihai1";
        RepoPersoana repo_pers = new RepoPersoana(url,user,pass);
        RepoSofer repo_sofer = new RepoSofer(url,user,pass,repo_pers);
        RepoComanda repo_com = new RepoComanda(url,user,pass,repo_pers,repo_sofer);
        Service serv = new Service(repo_pers,repo_sofer,repo_com);


        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 345);
        LogInController ctr = fxmlLoader.getController();
        ctr.setService(serv);
        stage.setTitle("Log In");
        stage.setScene(scene);
        stage.show();
    }
}
