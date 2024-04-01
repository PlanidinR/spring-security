package x5.school.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/open")
    public String hello() {
        return "Привет, Spring Security!";
    }

    @GetMapping("/close")
    public String disableMethod() {
        return "Этот эндпоинт закрыт";
    }
}
