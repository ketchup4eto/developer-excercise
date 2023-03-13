package com.exercise.cloudruid.utils.exceptions;

public class ItemNotInCartException extends RuntimeException {
    public ItemNotInCartException(String message) {
        super(message);
    }
}
