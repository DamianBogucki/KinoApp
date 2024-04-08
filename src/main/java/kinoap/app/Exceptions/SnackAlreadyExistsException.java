package kinoap.app.Exceptions;

public class SnackAlreadyExistsException extends RuntimeException{
    public SnackAlreadyExistsException(String message) {
        super(message);
    }

    public SnackAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public SnackAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
