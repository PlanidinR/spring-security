package x5.school.springsecurity.service.security;

import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import x5.school.springsecurity.repository.UserRepository;
import x5.school.springsecurity.service.security.pojo.Jwt;


@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public AuthenticationService(AuthenticationManager authenticationManager, UserRepository userRepository, JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @Transactional
    public Jwt authenticate(String login, String password) {
        var authentication = getAuthentication(login, password);
        var userDetails = (UserDetails) authentication.getPrincipal();
        var user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        var jwtWithExpireDate = jwtProvider.createToken(user.getId(), user.getUsername(), user.getRole().getName());

        return new Jwt(
                jwtWithExpireDate.getToken(),
                jwtWithExpireDate.getExpiredIn()
        );
    }

    private Authentication getAuthentication(String login, String password) {
        Authentication authentication;

        try {
            authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Логин или пароль указаны неверно", ex);
        } catch (AuthenticationException ex) {
            throw new AuthorizationServiceException("Ошибка аутентификации", ex);
        }
        return authentication;
    }
}

