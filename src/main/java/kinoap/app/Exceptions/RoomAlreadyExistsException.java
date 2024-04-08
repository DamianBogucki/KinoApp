package kinoap.app.Exceptions;

public class RoomAlreadyExistsException extends RuntimeException{
    public RoomAlreadyExistsException(String message) {
        super(message);
    }

    public RoomAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoomAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
