package jerry.exceptions;

/**
 * Represents an exception thrown when the user enters an invalid or unrecognized command.
 */
public class InvalidCommandException extends JerryException {

    /** List of valid Commands and their example usages */
    private static final String COMMAND_LIST =
            "Command: list (to view your tasks in a list)\n"
                    + "e.g. list\n"
                    + "Command: Todo <add your task here>\n"
                    + "e.g. todo clean house\n"
                    + "Command: jerry.task.Deadline <add your task here> /by <ddmmyyyy hhmm (24-hour clock)>\n"
                    + "e.g. deadline submit report /by 06062002 0530\n"
                    + "Command: jerry.task.Event <add your task here> "
                    + "/from <ddmmyyyy hhmm (24-hour clock)> /to <ddmmyyyy hhmm (24-hour clock)>\n"
                    + "e.g. event project meeting /from 06062002 0530 /to 07062002 0500 \n"
                    + "Command: Mark <your task index from list>\n"
                    + "e.g. mark 1\n"
                    + "Command: Unmark <your task index from list>\n"
                    + "e.g. unmark 2\n"
                    + "Command: Delete <your task index from list>\n"
                    + "e.g. delete 3\n"
                    + "Command: Find <your search query>"
                    + "e.g. find book"
                    + "Command: Bye\n"
                    + "e.g. bye\n";

    /**
     * Constructs an {@code InvalidCommandException} with a default error message
     * followed by a list of valid commands and example usages.
     */
    public InvalidCommandException() {
        super("Invalid Command >:c\n" + COMMAND_LIST);
    }

    /**
     * Constructs an {@code InvalidCommandException} with the specified error message
     * followed by a list of valid commands and example usages.
     *
     * @param outputMessage The error message describing why the command is invalid.
     */
    public InvalidCommandException(String outputMessage) {
        super(outputMessage + COMMAND_LIST);
    }
}
