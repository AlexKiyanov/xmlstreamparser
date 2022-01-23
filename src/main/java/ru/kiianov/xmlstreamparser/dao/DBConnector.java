package ru.kiianov.xmlstreamparser.dao;

import ru.kiianov.xmlstreamparser.dao.exception.DataBaseRuntimeException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBConnector {
    private final String url;
    private final String user;
    private final String password;

    public DBConnector(String fileConfigName) {
        ResourceBundle resource = ResourceBundle.getBundle(fileConfigName);
        this.url = resource.getString("db.url");
        this.user = resource.getString("db.user");
        this.password = resource.getString("db.password");
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new DataBaseRuntimeException("Connection wasn't created", e);
        }
    }
}
