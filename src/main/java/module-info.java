module com.example.taximetrie {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.taximetrie to javafx.fxml;
    exports com.example.taximetrie;

    opens com.example.taximetrie.service;
    exports com.example.taximetrie.service;

    opens com.example.taximetrie.domain;
    exports com.example.taximetrie.domain;

    opens com.example.taximetrie.repository;
    exports com.example.taximetrie.repository;

    opens com.example.taximetrie.controllers;
    exports com.example.taximetrie.controllers;
}