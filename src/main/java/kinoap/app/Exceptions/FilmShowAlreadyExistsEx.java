package kinoap.app.Exceptions;

public class FilmShowAlreadyExistsEx extends RuntimeException{
    public FilmShowAlreadyExistsEx(String message) {
        super(message);
    }

    public FilmShowAlreadyExistsEx(String message, Throwable cause) {
        super(message, cause);
    }

    public FilmShowAlreadyExistsEx(Throwable cause) {
        super(cause);
    }
}
