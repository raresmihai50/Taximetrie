package com.example.taximetrie.repository;

import com.example.taximetrie.domain.Comanda;
import com.example.taximetrie.domain.Persoana;
import com.example.taximetrie.domain.Sofer;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class RepoComanda {
    private String url;
    private String username;
    private String password;
    private RepoPersoana repo_pers;
    private RepoSofer repo_sofer;
    private List<Comanda> comenzi_live = new ArrayList<>();

    public RepoComanda(String url, String username, String password, RepoPersoana repo_pers, RepoSofer repo_sofer) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.repo_pers = repo_pers;
        this.repo_sofer = repo_sofer;

    }

    public List<Comanda> findAll() {

        List<Comanda> Comenzi = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from Comanda");
             ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long id_sofer = resultSet.getLong("id_sofer");
                Long id_persoana = resultSet.getLong("id_persoana");
                String dataS = resultSet.getString("data");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime data = LocalDateTime.parse(dataS, formatter);
                Sofer s = repo_sofer.findOne(id_sofer);
                Persoana p = repo_pers.findOne(id_persoana);
                Comanda comanda = new Comanda(p, s, data, null, null);
                comanda.setId(id);
                Comenzi.add(comanda);


            }
            return Comenzi;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Comanda findOne(Long id) {
        List<Comanda> comenzi = findAll();
        for (Comanda c : comenzi) {
            if (Objects.equals(c.getId(), id))
                return c;
        }
        return null;
    }
    public void addComandaLive(Comanda com){
        comenzi_live.add(com);
    }
    public void removeComandaLive(Comanda com){
        comenzi_live.remove(com);
    }
    public List<Comanda> findAllComenziLive(){
        return comenzi_live;
    }
    public Comanda findOneComandaLive(Long id){
        for (Comanda c : comenzi_live){
            if (Objects.equals(c.getId(), id)){
                return c;
            }
        }
        return null;
    }

    public Optional<Comanda> save(Comanda entity) {
        if (entity == null)
            throw new IllegalArgumentException("(SAVE)Entity cannot be null!");
        Random rand = new Random();

        String insertSQL = "insert into comanda (id, id_persoana, id_sofer, data) values (?, ?, ?, ?)";
        try (var connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(insertSQL)) {

            Long id = rand.nextLong(100000);
            statement.setLong(1, id);
            statement.setLong(2, entity.getPersoana().getId());
            statement.setLong(3, entity.getTaximetrist().getId());
            statement.setTimestamp(4, Timestamp.valueOf(entity.getData().truncatedTo(ChronoUnit.SECONDS)));
            int response = statement.executeUpdate();
            return response == 0 ? Optional.of(entity) : Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
