package com.paycraftsystems.exceptions;


public class InvalidRequestException extends RuntimeException implements java.io.Serializable {

    public InvalidRequestException(String message) {
        super(message);
    }
}
