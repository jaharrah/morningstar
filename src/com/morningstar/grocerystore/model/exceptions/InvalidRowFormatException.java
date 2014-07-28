package com.morningstar.grocerystore.model.exceptions;

/**
 * This exception is thrown when the row in a text file does not fit the expected formatting specified
 * by the program requirements.
 * User: Jon Aharrah
 * Date: 7/17/14
 * Time: 2:25 PM
 */
public class InvalidRowFormatException extends Exception {
    public InvalidRowFormatException() {
    }

    public InvalidRowFormatException(String message) {
        super(message);
    }
}
