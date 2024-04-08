package kinoap.app.securityConfig;

import kinoap.app.dao.ClientDao;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.naming.ldap.PagedResultsControl;

@Service
@AllArgsConstructor
public class ClientDetailsService implements UserDetailsService {
    private ClientDao clientDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var u = clientDao.findClientByEmail(username);
        return u.orElseThrow(() -> new UsernameNotFoundException("username not found"));
    }
}
