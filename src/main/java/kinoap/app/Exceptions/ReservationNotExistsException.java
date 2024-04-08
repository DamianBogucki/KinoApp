package kinoap.app.Exceptions;

public class ReservationNotExistsException extends RuntimeException{
    public ReservationNotExistsException(String message) {
        super(message);
    }

    public ReservationNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReservationNotExistsException(Throwable cause) {
        super(cause);
    }
}
