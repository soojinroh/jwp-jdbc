package nextstep.jdbc.exception;

public class NotFoundObjectException extends RuntimeException{
    public NotFoundObjectException() {
        super("Requested Object not found!");
    }
}
