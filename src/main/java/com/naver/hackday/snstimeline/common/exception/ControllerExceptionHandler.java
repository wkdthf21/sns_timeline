package com.naver.hackday.snstimeline.common.exception;

import java.util.Iterator;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

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

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> notFoundException(NotFoundException exception) {
        ExceptionResponseDto responseDto = ExceptionResponseDto.builder()
                .field(exception.getField())
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponseDto> badRequestException(BadRequestException exception) {
        ExceptionResponseDto responseDto = ExceptionResponseDto.builder()
                .field(exception.getField())
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

	// PathVariable 유효성 검사 실패 -> 400 request 처리
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ExceptionResponseDto> constraintViolationException(ConstraintViolationException exception){
		final StringBuilder resultMessageBuilder = new StringBuilder();
		final Iterator<ConstraintViolation<?>> violationIterator = exception.getConstraintViolations().iterator();
		while(violationIterator.hasNext()){
			final ConstraintViolation<?> constraintViolation = violationIterator.next();
			resultMessageBuilder.append(constraintViolation.getPropertyPath().toString());

			if(violationIterator.hasNext())
				resultMessageBuilder.append(", ");
		}
		ExceptionResponseDto responseDto = ExceptionResponseDto.builder()
			.field(resultMessageBuilder.toString())
			.message(exception.getMessage())
			.build();

		return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
	}

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<ExceptionResponseDto> noContentException(NoContentException exception) {
        ExceptionResponseDto responseDto = ExceptionResponseDto.builder()
                .field(exception.getField())
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.NO_CONTENT);
    }

	@ExceptionHandler(FileUploadDownloadException.class)
	public ResponseEntity<ExceptionResponseDto> fileUploadDownloadException(FileUploadDownloadException exception) {
		ExceptionResponseDto responseDto = ExceptionResponseDto.builder()
			.field(exception.getField())
			.message(exception.getMessage())
			.build();

		return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
	}

}