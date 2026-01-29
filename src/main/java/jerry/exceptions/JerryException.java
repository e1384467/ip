package jerry.exceptions;

/**
 * Represents a general exception that can be thrown when an error occurs.
 * This exception serves as the base class for all custom exceptions.
 */
public class JerryException extends Exception {

    /**
     * Constructs a {@code JerryException} with the specified error message.
     *
     * @param outputMessage The error message describing the cause of the exception.
     */
    public JerryException(String outputMessage) {
        super(outputMessage);
    }
}