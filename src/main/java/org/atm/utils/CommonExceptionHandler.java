package org.atm.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<CommonExceptionMessage> handleCommonException(Exception e) {
        ResponseEntity<CommonExceptionMessage> responseEntity =
                new ResponseEntity<>(
                        new CommonExceptionMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        log.error("Throw exception ", e);
        return responseEntity;
    }
}
