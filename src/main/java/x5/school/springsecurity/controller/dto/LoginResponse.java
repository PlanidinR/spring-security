package x5.school.springsecurity.controller.dto;


import java.time.LocalDateTime;

public record LoginResponse(String accessToken, LocalDateTime expiredIn) {
}
