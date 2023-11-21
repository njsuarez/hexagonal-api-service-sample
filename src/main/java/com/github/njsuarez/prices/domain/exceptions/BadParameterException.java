package com.github.njsuarez.prices.domain.exceptions;

/**
 * Applied when the method parameters don't fit criteria
 */
public class BadParameterException extends RuntimeException {

    public BadParameterException(String message) {
        super(message);
    }

}
