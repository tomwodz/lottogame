package pl.tomwodz.lottogame.infrastructure.resultannouncer.controller.error;

import org.springframework.http.HttpStatus;

public record ErrorResultAnnouncerResponseDto(
        String message,
        HttpStatus httpStatus) {
}
