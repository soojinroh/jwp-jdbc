package slipp.dao;

import nextstep.jdbc.JdbcTemplate;
import nextstep.jdbc.RowMapper;
import nextstep.jdbc.connection.DBConnection;
import nextstep.jdbc.exception.NotFoundObjectException;
import slipp.domain.User;

import java.util.List;

public class UserDao {
    private JdbcTemplate jdbcTemplate;

    public UserDao() {
        DBConnection dbConnection = DBConnection.getH2Connection("org.h2.Driver", "jdbc:h2:mem:jwp-framework", "sa", "");
        jdbcTemplate = new JdbcTemplate(dbConnection);
    }

    public void insert(User user) {
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        Object[] values = {user.getUserId(), user.getPassword(), user.getName(), user.getEmail()};
        jdbcTemplate.execute(sql, values);
    }

    public void update(User user) {
        String sql = "UPDATE users SET password = ?, name = ?, email = ? WHERE userId = ?";
        Object[] values = {user.getPassword(), user.getName(), user.getEmail(), user.getUserId()};
        jdbcTemplate.execute(sql, values);
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        RowMapper<User> rm = getUserRowMapper();

        return jdbcTemplate.query(sql, rm);
    }

    public User findByUserId(String userId) {
        String sql = "SELECT * FROM users WHERE userId = ?";
        Object[] values = {userId};
        RowMapper<User> rm = getUserRowMapper();

        return jdbcTemplate.queryForObject(sql, rm, values).orElseThrow(NotFoundObjectException::new);
    }

    private RowMapper<User> getUserRowMapper() {
        return rs -> new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"), rs.getString("email"));
    }
}
