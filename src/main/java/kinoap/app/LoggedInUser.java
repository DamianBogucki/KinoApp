package kinoap.app;

import kinoap.app.Entity.Client;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.swing.event.CaretListener;

public class LoggedInUser {
    public static Client getLoggedUnUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Client) authentication.getPrincipal();
    }
}
