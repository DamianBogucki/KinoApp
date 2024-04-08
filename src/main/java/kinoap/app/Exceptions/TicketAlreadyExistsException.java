package kinoap.app.Exceptions;

public class TicketAlreadyExistsException extends RuntimeException{
    public TicketAlreadyExistsException(String message) {
        super(message);
    }

    public TicketAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
