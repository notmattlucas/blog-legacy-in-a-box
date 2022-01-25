package com.notmattlucas.box.part4.env;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class MySqlSvc extends Svc {

    private static Logger LOG = LoggerFactory.getLogger(MySqlSvc.class);

    private static final String USERNAME = "pc";

    private static final String PASSWORD = "petclinic";

    private String connectionUrl;

    public MySqlSvc(String host, int port) {
        super(host, port);
        String.format("jdbc:mysql://%s:%s/petclinic?serverTimezone=UTC", getHost(), getPort());
    }

    public void clear() {
        try (Connection conn = DriverManager.getConnection(connectionUrl, USERNAME, PASSWORD);
             PreparedStatement ps = conn.prepareStatement("SHOW TABLES;");
             ResultSet rs = ps.executeQuery()) {
            rs.getArray()
        } catch (SQLException e) {
            LOG.warn("Could not execute SQL task", e);
        }
    }

}
