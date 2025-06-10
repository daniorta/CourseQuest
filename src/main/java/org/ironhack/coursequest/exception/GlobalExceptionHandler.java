package org.ironhack.coursequest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoudException(NotFoundException notFoundException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundException.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleNotFoundException(BadRequestException badRequestException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestException.getMessage());
    }

    //Error en las validaciones.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleNotFoundException(MethodArgumentNotValidException ex) {
        List<String> messages = new ArrayList<>();

        String message = "";
        for (FieldError error : ex.getFieldErrors()){
            System.out.println("FIELD " + error.getField());
            System.out.println("MESSAGE " + error.getDefaultMessage());

            messages.add(error.getField() + " : " + error.getDefaultMessage());
            message = message + error.getField() + " : " + error.getDefaultMessage() + " ";
        }

        System.out.println("------ String message:");
        System.out.println(message);

        String errorMessage = String.join(" ,", messages);

        return ResponseEntity.badRequest().body(errorMessage);
    }




}
