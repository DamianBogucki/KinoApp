package kinoap.app.Exceptions;

public class FilmShowNotExistsEx extends RuntimeException{
    public FilmShowNotExistsEx(String message) {
        super(message);
    }

    public FilmShowNotExistsEx(String message, Throwable cause) {
        super(message, cause);
    }

    public FilmShowNotExistsEx(Throwable cause) {
        super(cause);
    }
}
