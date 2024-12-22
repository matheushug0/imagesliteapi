package br.com.mh.imageliteapi.domain.exception;

public class DuplicatedTupleException extends RuntimeException {
    public DuplicatedTupleException(String message) {
        super(message);
    }
}
