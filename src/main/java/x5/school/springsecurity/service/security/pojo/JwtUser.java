package x5.school.springsecurity.service.security.pojo;

import java.security.Principal;
import java.util.UUID;

public class JwtUser implements Principal {
    private final UUID id;
    private final String name;

    public JwtUser(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID id() {
        return id;
    }

    public String getName() {
        return name;
    }
}