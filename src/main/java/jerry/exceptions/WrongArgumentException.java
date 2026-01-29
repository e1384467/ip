package jerry.exceptions;

/**
 * Represents an exception thrown when a command argument is invalid.
 */
public class WrongArgumentException extends JerryException {

    /**
     * Constructs a {@code WrongArgumentException} with the specified error message describing the invalid argument.
     *
     * @param outputMessage The message detailing why the argument is invalid.
     */
    public WrongArgumentException(String outputMessage) {
        super("Wrong Argument >:( !!!!\n" + outputMessage);
    }
}
