package ru.senina.itmo.lab6;

/**
 * Exception to show that some arguments weren't valid
 */
public class InvalidArgumentsException extends RuntimeException{
    public InvalidArgumentsException(String message) {
        super(message);
    }
}
