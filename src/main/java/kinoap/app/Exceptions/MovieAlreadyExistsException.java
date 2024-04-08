package kinoap.app.Exceptions;

public class MovieAlreadyExistsException extends RuntimeException{
    public MovieAlreadyExistsException() {
    }

    public MovieAlreadyExistsException(String message) {
        super(message);
    }

    public MovieAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
