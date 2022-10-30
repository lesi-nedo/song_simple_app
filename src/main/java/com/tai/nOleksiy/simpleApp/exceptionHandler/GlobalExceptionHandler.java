package com.tai.nOleksiy.simpleApp.exceptionHandler;

import com.tai.nOleksiy.simpleApp.songsValidator.songsReturnObj.SongErrorObj;
import com.tai.nOleksiy.simpleApp.songsValidator.songsReturnObj.SongFormatException;
import com.tai.nOleksiy.simpleApp.songsValidator.songsReturnObj.SongNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {
        ConstraintViolationException.class,
    })
    public ResponseEntity<SongErrorObj> handleConst(ConstraintViolationException e, HttpServletRequest request){
        StringBuilder strBuilder = new StringBuilder();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for(ConstraintViolation<?> violation : violations) {
            strBuilder.append(violation.getMessage()).append("\n");
        }

        return new ResponseEntity<>(helperCreate(strBuilder.toString(), HttpStatus.BAD_REQUEST, request.getRequestURI(), e.getClass().getSimpleName()), HttpStatus.BAD_REQUEST);
     }

    @ExceptionHandler(value = {
            Exception.class
    })
    public ResponseEntity<SongErrorObj> handleSong(Exception e, HttpServletRequest request){
        return new ResponseEntity<>(helperCreate(e.getMessage(), HttpStatus.BAD_REQUEST, request.getRequestURI(), e.getClass().getSimpleName()), HttpStatus.BAD_REQUEST);
    }
     private SongErrorObj helperCreate(String mess, HttpStatus stat, String path, String exc){
         SongErrorObj respObj = new SongErrorObj();
         respObj.setMessage(mess);
         respObj.setStatus(stat);
         respObj.setPath(path);
         respObj.setException(exc);
         return respObj;
     }
}

