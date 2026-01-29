package jerry.exceptions;

/**
 * Represents an exception thrown when the user provides an empty input.
 */
public class EmptyInputException extends InvalidCommandException {

    /**
     * Constructs an {@code EmptyInputException} with a default error message prompting the user to enter a command.
     */
    public EmptyInputException() {
        super("Please enter something :b\n");
    }
}