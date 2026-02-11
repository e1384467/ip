package jerry.exceptions;

/**
 * Represents an exception thrown when a duplicate task is detected.
 */
public class DuplicatedTasksException extends JerryException {

    public DuplicatedTasksException(String duplicatedTasks) {
        super("Duplicated Tasks: " + duplicatedTasks);
    }
}
