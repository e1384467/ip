package jerry.task;

/**
 * Represents a task with only a description and no associated date or time.
 * A {@code ToDo} task extends {@code Task} without additional attributes.
 */
public class ToDo extends Task {

    /**
     * Constructs a {@code ToDo} task with the specified description.
     * The task is initially marked as not completed.
     *
     * @param description The description of the to-do task.
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Constructs a {@code ToDo} task with the specified completion status
     * and description.
     *
     * @param isDone Whether the task is marked as completed.
     * @param description The description of the to-do task.
     */
    public ToDo(boolean isDone, String description) {
        super(isDone, description);
    }

    /**
     * Returns the file representation of this todo task in the expected save file format.
     *
     * @return A string representing this todo task in file storage format.
     */
    @Override
    public String fileFormat() {
        return (super.isDone ? "1|T|" : "0|T|") + super.description;
    }

    /**
     * Returns a string representation of this todo task.
     *
     * @return A formatted string describing this todo task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
