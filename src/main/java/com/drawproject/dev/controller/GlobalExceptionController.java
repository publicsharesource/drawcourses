package com.drawproject.dev.controller;

import com.drawproject.dev.dto.ResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionController {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class ResponseError {
        private HttpStatus status;
        private String message;
        private String throwable;

    }

    /*
    @ExceptionHandler will register the given method for a given
    exception type, so that ControllerAdvice can invoke this method
    logic if a given exception type is thrown inside the web application.
    * */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ResponseError> exceptionHandler(Exception exception){

        ResponseError rep = new ResponseError(HttpStatus.BAD_REQUEST, exception.getMessage(), exception.toString());

        return new ResponseEntity<ResponseError>(rep, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.BAD_REQUEST, "Invalid some fields", errorMap);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<ResponseError> handleNotFoundException(NoSuchElementException noSuchElementException){

        ResponseError rep = new ResponseError(HttpStatus.NOT_FOUND, "Your item is not existed!", noSuchElementException.toString());

        return new ResponseEntity<ResponseError>(rep, HttpStatus.NOT_FOUND);
    }

}