package com.example.taximetrie.repository;

import com.example.taximetrie.domain.Persoana;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RepoPersoana {
    private String url;
    private String username;
    private String password;

    public RepoPersoana(String url,String username,String password){
        this.url=url;
        this.username=username;
        this.password=password;

    }

    public List<Persoana> findAll() {

        List<Persoana> Persoane = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from Persoana");
             ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String username= resultSet.getString("username");
                String name= resultSet.getString("name");
                Persoana Persoana = new Persoana(username,name);
                Persoana.setId(id);
                Persoane.add(Persoana);

            }
            return Persoane;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Persoana findOne(Long id){
        List<Persoana> persoane = findAll();
        for (Persoana p : persoane){
            if (Objects.equals(p.getId(), id))
                return p;
        }
        return null;
    }
    public Persoana findOneByUsername(String username){
        List<Persoana> persoane = findAll();
        for (Persoana p : persoane){
            if (Objects.equals(p.getUsername(), username))
                return p;
        }
        return null;
    }
}
