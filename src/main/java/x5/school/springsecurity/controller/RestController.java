package x5.school.springsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @PreAuthorize("hasAuthority('READ')")
    public String read() {
        return "Метод доступен с разрешением READ";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String disableMethod() {
        return "Этот эндпоин";
    }
}
