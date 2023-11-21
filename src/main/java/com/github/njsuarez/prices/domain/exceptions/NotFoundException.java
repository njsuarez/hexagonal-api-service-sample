package com.github.njsuarez.prices.domain.exceptions;

/**
 * Applied when and entity is not found
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

}
