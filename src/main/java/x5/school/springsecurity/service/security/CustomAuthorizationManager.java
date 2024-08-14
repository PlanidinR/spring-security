package x5.school.springsecurity.service.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        var auth = authentication.get();
        var request = context.getRequest();

        if (auth.isAuthenticated() && isUserInAllowedLocation(request)) {
            return new AuthorizationDecision(true);
        }

        return new AuthorizationDecision(false);
    }
    private boolean isUserInAllowedLocation(HttpServletRequest request) {
        String userIp = request.getRemoteAddr();

        return checkIpAgainstAllowedLocations(userIp);
    }
    private boolean checkIpAgainstAllowedLocations(String ip) {
        return true;
    }
}