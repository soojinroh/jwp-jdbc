package nextstep.jdbc.exception;

public class DataAccessException extends RuntimeException {
    public DataAccessException(Exception e) {
        super(e.getMessage(), e.getCause());
    }
}
