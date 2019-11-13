package nextstep.jdbc.connection;

public class PropertiesLoadException extends RuntimeException {
    public PropertiesLoadException(Exception e) {
        super(e.getMessage(), e.getCause());
    }
}
