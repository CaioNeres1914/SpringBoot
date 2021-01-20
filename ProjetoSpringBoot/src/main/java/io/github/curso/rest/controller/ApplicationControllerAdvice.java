package io.github.curso.rest.controller;

import io.github.curso.exception.ApiErrors;
import io.github.curso.exception.NotFoundApplicationException;
import io.github.curso.exception.RegraNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handlerRegraNegocioExcepetion(RegraNegocioException ex){
        String mensagemErro = ex.getMessage();
        return new ApiErrors(mensagemErro);

    }

    @ExceptionHandler(NotFoundApplicationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handlerNotFoundException(NotFoundApplicationException ex){
        String mensagemErro = ex.getMessage();
        return new ApiErrors(mensagemErro);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handlerMethodNotValidExeception(MethodArgumentNotValidException ex){

        List<String> errors = ex.getBindingResult().getAllErrors()
                                                   .stream()
                                                   .map( erro -> erro.getDefaultMessage())
                                                   .collect(Collectors.toList());

        return new ApiErrors(errors);
    }

}
