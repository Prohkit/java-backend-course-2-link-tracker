package edu.java.scrapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MigrationTest extends IntegrationTest {

    @Test
    void doTablesExist() throws SQLException {
        String chat = "chat";
        String link = "link";
        String chatLink = "chat_link";
        Connection connection =
            DriverManager.getConnection(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());
        assertThat(connection.getMetaData().getTables(null, null, chat, null).next()).isTrue();
        assertThat(connection.getMetaData().getTables(null, null, link, null).next()).isTrue();
        assertThat(connection.getMetaData().getTables(null, null, chatLink, null).next()).isTrue();
    }
}
