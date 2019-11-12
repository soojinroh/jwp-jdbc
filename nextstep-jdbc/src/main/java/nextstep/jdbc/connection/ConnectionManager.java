package nextstep.jdbc.connection;

import nextstep.jdbc.exception.GetConnectionException;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {
    private static final Logger log = LoggerFactory.getLogger(ConnectionManager.class);

    private BasicDataSource basicDataSource;

    public ConnectionManager(DBConnection dbConnection) {
        initBasicDataSource(dbConnection);
    }

    private void initBasicDataSource(DBConnection dbConnection) {
        this.basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(dbConnection.getDriver());
        basicDataSource.setUrl(dbConnection.getUrl());
        basicDataSource.setUsername(dbConnection.getUserName());
        basicDataSource.setPassword(dbConnection.getPassword());
    }

    public Connection getConnection() {
        try {
            return getDataSource().getConnection();
        } catch (SQLException e) {
            log.error("Cannot get connection from ConnectionManager!");
            throw new GetConnectionException(e);
        }
    }

    private DataSource getDataSource() {
        return basicDataSource;
    }

}
