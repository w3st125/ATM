package org.atm.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

    @Data
    @AllArgsConstructor
    private static class CommonException {
        private String message;
    }

    @ExceptionHandler(CurrencyException.class)
    protected ResponseEntity<CommonException> handleCurrencyException() {
        return new ResponseEntity<>(
                new CommonException("Переводы на счёт с другой валютой запрещены!"),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientFundException.class)
    protected ResponseEntity<CommonException> handleInsufficientFundException() {
        return new ResponseEntity<>(
                new CommonException("На Вашем счёте недостаточно средств!"),
                HttpStatus.BAD_REQUEST);
    }
}
