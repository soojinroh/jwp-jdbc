package nextstep.jdbc.exception;

public class GetConnectionException extends RuntimeException {
    public GetConnectionException(Exception e) {
        super(e.getMessage(), e.getCause());
    }
}
