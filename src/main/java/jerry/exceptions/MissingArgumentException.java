package jerry.exceptions;

/**
 * Represents an exception thrown when a required argument for a command is missing from the user input.
 */
public class MissingArgumentException extends JerryException {

    /**
     * Constructs a {@code MissingArgumentException} with a message indicating
     * that a required argument is missing, followed by the correct usage format.
     *
     * @param correctUsage The correct usage format of the command.
     */
    public MissingArgumentException(String correctUsage) {
        super("Missing Argument >:( !!!! Please try:\n" + correctUsage);
    }
}
