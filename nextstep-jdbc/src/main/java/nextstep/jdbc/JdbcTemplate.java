package nextstep.jdbc;

import nextstep.jdbc.connection.ConnectionManager;
import nextstep.jdbc.connection.DBConnection;
import nextstep.jdbc.exception.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcTemplate {
    private static final Logger log = LoggerFactory.getLogger(JdbcTemplate.class);

    private ConnectionManager connectionManager;

    public JdbcTemplate() {
        connectionManager = new ConnectionManager(DBConnection.getInstance());
    }

    public void execute(String sql, Object... objects) {
        try (Connection con = connectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            setValues(pstmt, objects);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.error("executeUpdate Exception!");
            throw new DataAccessException(e);
        }
    }

    public <T> List<T> query(String sql, RowMapper<T> rm) {
        try (Connection con = connectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            List<T> values = new ArrayList<>();
            while (rs.next()) {
                values.add(rm.mapRow(rs));
            }
            return values;
        } catch (SQLException e) {
            log.error("executeQuery Exception!");
            throw new DataAccessException(e);
        }
    }

    public <T> Optional<T> queryForObject(String sql, RowMapper<T> rm, Object... objects) {
        try (Connection con = connectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = executeValueSetQuery(pstmt, objects)) {

            if (rs.next()) {
                return Optional.ofNullable(rm.mapRow(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            log.error("queryForObject Exception!");
            throw new DataAccessException(e);
        }
    }

    private ResultSet executeValueSetQuery(PreparedStatement pstmt, Object[] objects) throws SQLException {
        setValues(pstmt, objects);
        return pstmt.executeQuery();
    }

    private void setValues(PreparedStatement pstmt, Object[] objects) throws SQLException {
        for (int i = 0; i < objects.length; i++) {
            pstmt.setObject(i + 1, objects[i]);
        }
    }
}
