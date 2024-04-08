package kinoap.app.controllers;


import jakarta.validation.Valid;
import kinoap.app.dtoObjects.RequestDto.AuthenticationRequst;
import kinoap.app.dtoObjects.RequestDto.RegisterRequest;
import kinoap.app.dtoObjects.ResponseDto.AuthenticationResponse;
import kinoap.app.securityConfig.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest requst){
        return ResponseEntity.ok(authenticationService.register(requst));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid AuthenticationRequst requst){
        return ResponseEntity.ok(authenticationService.authenticate(requst));
    }
}
