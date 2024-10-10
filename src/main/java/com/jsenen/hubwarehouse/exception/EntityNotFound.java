package com.jsenen.hubwarehouse.exception;

public class EntityNotFound extends Exception {
    public EntityNotFound(){

        super("Entity not found");
    }
    public EntityNotFound(String message) {

        super(message);
    }
}
