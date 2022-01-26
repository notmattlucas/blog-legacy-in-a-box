package com.notmattlucas.box.part4.env;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.function.Consumer;

public class MySql extends Svc {

    private static Logger LOG = LoggerFactory.getLogger(MySql.class);

    private static final String USERNAME = "pc";

    private static final String PASSWORD = "petclinic";

    private static final String DB = "petclinic";

    private final String connectionUrl;

    public MySql(String host, int port) {
        super(host, port);
        connectionUrl = String.format("jdbc:mysql://%s:%s/%s?serverTimezone=UTC", getHost(), getPort(), DB);
    }

    public void clear() {
        execute((conn) -> {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            try {
                ctx.execute("SET FOREIGN_KEY_CHECKS=0");
                ctx.meta()
                    .filterSchemas(s -> s.getName().equals(DB))
                    .getTables()
                    .forEach(table -> ctx.truncate(table.getName()).execute());
            } finally {
                ctx.execute("SET FOREIGN_KEY_CHECKS=1");
            }
        });
    }

    private void execute(Consumer<Connection> task) {
        try (Connection conn = DriverManager.getConnection(connectionUrl, USERNAME, PASSWORD)) {
            task.accept(conn);
        } catch (SQLException e) {
            LOG.warn("Could not execute SQL task", e);
        }
    }

}
