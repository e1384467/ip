package jerry.exceptions;

/**
 * Represents an exception thrown when a user attempts to perform an action that has already been applied to a task.
 */
public class RepeatedActionsException extends JerryException {

    /**
     * Constructs a {@code RepeatedActionsException} with the specified error message describing the invalid repeated action.
     *
     * @param outputMessage The message detailing the cause of the repeated action error.
     */
    public RepeatedActionsException(String outputMessage) {
        super("Repeated Action >:( !!!!\n" + outputMessage);
    }
}