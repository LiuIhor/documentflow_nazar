package com.company.docflow.exception;

import com.company.docflow.dto.ErrorDto;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDto> handleEntityNotFoundException(EntityNotFoundException ex) {
        ErrorDto errorDto = new ErrorDto(
                LocalDateTime.now(),
                "Entity not found",
                ex.getMessage()
        );

        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDto> handleConstraintViolationException(ConstraintViolationException ex) {

        ErrorDto errorDto = new ErrorDto(
                LocalDateTime.now(),
                "Constraint Violation Exception",
                ex.getMessage()
        );
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDto> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        ErrorDto errorDto = new ErrorDto(
                LocalDateTime.now(),
                "Database constraint violation",
                ex.getMessage()
        );
        return new ResponseEntity<>(errorDto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDto> handleGlobalException(RuntimeException ex) {

        ErrorDto errorDto = new ErrorDto(
                LocalDateTime.now(),
                "An error occurred",
                ex.getMessage()
        );
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}