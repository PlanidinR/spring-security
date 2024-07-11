package x5.school.springsecurity.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import x5.school.springsecurity.controller.dto.LoginRequest;
import x5.school.springsecurity.controller.dto.LoginResponse;
import x5.school.springsecurity.service.security.AuthenticationService;

@RestController
public class AuthController {
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        var jwt = authenticationService.authenticate(loginRequest.login(), loginRequest.password());

        return new LoginResponse(
                jwt.getToken(),
                jwt.getExpiredIn());
    }
}
