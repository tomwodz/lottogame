package pl.tomwodz.lottogame.infrastructure.resultannouncer.controller.error;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.tomwodz.lottogame.domain.resultchecker.PlayerResultNotFoundException;
import pl.tomwodz.lottogame.infrastructure.resultannouncer.controller.ResultAnnouncerRestController;

@ControllerAdvice(assignableTypes = ResultAnnouncerRestController.class)
@Log4j2
public class ResultAnnouncerControllerErrorHandler {

    @ExceptionHandler(PlayerResultNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResultAnnouncerResponseDto handleException(PlayerResultNotFoundException e){
        log.error(e.getMessage());
        return new ErrorResultAnnouncerResponseDto(e.getMessage(), HttpStatus.NOT_FOUND);
    }

}
