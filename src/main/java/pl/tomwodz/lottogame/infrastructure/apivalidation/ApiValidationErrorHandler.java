package pl.tomwodz.lottogame.infrastructure.apivalidation;


import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.tomwodz.lottogame.infrastructure.numberreceiver.controller.InputNumbersRestController;
import pl.tomwodz.lottogame.infrastructure.resultannouncer.controller.ResultAnnouncerRestController;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice(assignableTypes = {
        ResultAnnouncerRestController.class,
        InputNumbersRestController.class})
public class ApiValidationErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiValidationErrorResponseDto handlerValidationException(MethodArgumentNotValidException exception){
       List<String> message = getErrorsFromException(exception);
        return new ApiValidationErrorResponseDto(message, HttpStatus.BAD_REQUEST);
    }

    private List<String> getErrorsFromException(MethodArgumentNotValidException exception){
        return exception.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    }
}
