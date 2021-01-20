package io.github.curso.exception;

public class NotFoundApplicationException extends RuntimeException{

    public NotFoundApplicationException(String message){
        super(message);
    }

}
