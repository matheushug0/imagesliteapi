package br.com.mh.imageliteapi.application.jwt;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException(String message) {
        super(message);
    }
}