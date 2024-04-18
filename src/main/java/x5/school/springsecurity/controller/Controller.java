package x5.school.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/about")
    public String hello() {
        return "Привет, это урок о пользователях и ролях!";
    }

    @GetMapping("/admin")
    public String disableMethod() {
        return "Этот эндпоин";
    }
}
