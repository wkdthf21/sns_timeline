package com.naver.hackday.snstimeline.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseDto> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();

        FieldError fieldError = bindingResult.getFieldErrors().get(0);
        ExceptionResponseDto responseDto = ExceptionResponseDto.builder()
                .field(fieldError.getField())
                .message(fieldError.getDefaultMessage())
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponseDto> notExistException(CustomException exception) {
        ExceptionResponseDto responseDto = ExceptionResponseDto.builder()
                .field(exception.getField())
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }
}
