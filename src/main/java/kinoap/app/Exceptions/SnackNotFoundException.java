package kinoap.app.Exceptions;

public class SnackNotFoundException extends RuntimeException{
    public SnackNotFoundException(String message) {
        super(message);
    }

    public SnackNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SnackNotFoundException(Throwable cause) {
        super(cause);
    }
}
