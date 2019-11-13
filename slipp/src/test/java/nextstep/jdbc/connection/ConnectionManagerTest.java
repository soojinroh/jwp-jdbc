package nextstep.jdbc.connection;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionManagerTest {

    @Test
    void test() {
        DBConnection dbConnection = DBConnection.getMySQLConnection();
        ConnectionManager connectionManager = new ConnectionManager(dbConnection);

        assertNotNull(connectionManager.getConnection());
    }
}