package x5.school.springsecurity.service.security.pojo;

import java.time.LocalDateTime;

public class Jwt {

    private final String token;
    private final LocalDateTime expiredIn;

    public Jwt(String token, LocalDateTime expiredIn) {
        this.token = token;
        this.expiredIn = expiredIn;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getExpiredIn() {
        return expiredIn;
    }
}
