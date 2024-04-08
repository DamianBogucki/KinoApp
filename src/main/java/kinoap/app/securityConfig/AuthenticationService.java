package kinoap.app.securityConfig;

import jakarta.transaction.Transactional;
import kinoap.app.Entity.Authority;
import kinoap.app.Entity.Client;
import kinoap.app.dao.AuthorityDao;
import kinoap.app.dao.ClientDao;
import kinoap.app.dtoObjects.RequestDto.AuthenticationRequst;
import kinoap.app.dtoObjects.RequestDto.RegisterRequest;
import kinoap.app.dtoObjects.ResponseDto.AuthenticationResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@Service
public class AuthenticationService {
    private final ClientDao clientDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AuthorityDao authorityDao;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request){
        var client = new Client();
        client.setFirstName(request.getFirstName());
        client.setLastName(request.getLastName());
        client.setEmail(request.getEmail());
        client.setPassword(passwordEncoder.encode(request.getPassword()));
        client.setPhoneNumber(request.getPhoneNumber());
        client.setAuthorities(Set.of(authorityDao.findAuthorityByName("USER"),authorityDao.findAuthorityByName("ADMIN")));
        clientDao.save(client);

        Map<String,Object> mapp = new HashMap<>();
        mapp.put("aud","USER");

        var jwtToken = jwtService.generateToken(mapp,client);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken(jwtToken);
        return authenticationResponse;

    }


    public AuthenticationResponse authenticate(AuthenticationRequst requst){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requst.getEmail(),
                        requst.getPassword()
                )
        );

        var user = clientDao.findClientByEmail(requst.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken(jwtToken);
        return authenticationResponse;
    }
}
