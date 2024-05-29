package x5.school.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @GetMapping("/about")
    public String hello() {
        return "Привет, это урок о пользователях и ролях!";
    }

    @GetMapping("/admin")
    public String disableMethod() {
        return "Этот эндпоин";
    }
}
