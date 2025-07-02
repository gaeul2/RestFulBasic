package kr.co.joenconsulting.myrestfulservice.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice//모든 컨트롤러 실행전 여기 탐
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)// 이 어노테이션으로 어떤 Exception 처리할지 지정가능
    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse =
                //request.getDescription 인데 상세정보를 client에 보여주지 않기위해 false부여
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)// 이 어노테이션으로 어떤 Exception 처리할지 지정가능
    public final ResponseEntity<Object> handleUserException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse =
                //request.getDescription 인데 상세정보를 client에 보여주지 않기위해 false부여
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ExceptionResponse exceptionResponse =
                //request.getDescription 인데 상세정보를 client에 보여주지 않기위해 false부여
//                new ExceptionResponse(new Date(), ex.getMessage(), ex.getBindingResult().toString());
                new ExceptionResponse(new Date(),  "validation Fail", ex.getBindingResult().toString());
//        return super.handleMethodArgumentNotValid(ex, headers, status, request);
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
