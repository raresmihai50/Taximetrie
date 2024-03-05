package com.example.taximetrie.repository;

import com.example.taximetrie.domain.Persoana;
import com.example.taximetrie.domain.Sofer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RepoSofer {
    private String url;
    private String username;
    private String password;
    private RepoPersoana repo_pers;

    public RepoSofer(String url,String username,String password, RepoPersoana repo_pers){
        this.url=url;
        this.username=username;
        this.password=password;
        this.repo_pers = repo_pers;

    }

    public List<Sofer> findAll() {

        List<Sofer> Soferi = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from Sofer");
             ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String indicativMasina = resultSet.getString("indicativMasina");
                Persoana pers = repo_pers.findOne(id);
                Sofer Sofer = new Sofer(pers.getUsername(), pers.getNume(), indicativMasina);
                Sofer.setId(id);
                Soferi.add(Sofer);

            }
            return Soferi;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Sofer findOne(Long id){
        List<Sofer> soferi = findAll();
        for (Sofer s : soferi){
            if (Objects.equals(s.getId(), id))
                return s;
        }
        return null;
    }
    public Sofer findOneByUsername(String username){
        List<Sofer> soferi = findAll();
        for (Sofer s : soferi){
            if (Objects.equals(s.getUsername(), username))
                return s;
        }
        return null;
    }
}
