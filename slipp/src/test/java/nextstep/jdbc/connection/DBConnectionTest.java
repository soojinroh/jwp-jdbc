package nextstep.jdbc.connection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class DBConnectionTest {
    private Properties properties;

    @BeforeEach
    void setUp() throws IOException {
        properties = new Properties();
        InputStream in = getClass().getClassLoader().getResourceAsStream("db.properties");
        properties.load(in);
    }

    @Test
    void get_properties_test() {
        assertThat(properties.getProperty("jdbc.url")).isEqualTo("jdbc:mysql://localhost:3306/jwp_jdbc?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC");
        assertThat(properties.getProperty("jdbc.driverClass")).isEqualTo("com.mysql.cj.jdbc.Driver");
        assertThat(properties.getProperty("jdbc.username")).isEqualTo("root");
        assertThat(properties.getProperty("jdbc.password")).isEqualTo("root");
        assertThat(properties.getProperty("jdbc.username")).isEqualTo(DBConnection.getMySQLConnection().getUserName());
    }

    @Test
    void db_connection_test() {
        assertThat(DBConnection.getMySQLConnection()).isNotNull();
    }
}