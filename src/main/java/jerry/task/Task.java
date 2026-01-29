package jerry.task;

/**
 * Represents an abstract task with a description and completion status.
 * This class serves as the base type for all specific task implementations.
 */
public abstract class Task {

    /** The description of the task. */
    protected String description;

    /** Indicates whether the task has been marked as completed. */
    protected boolean isDone;

    /**
     * Constructs a {@code Task} with the specified description.
     * The task is initially marked as not completed.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Constructs a {@code Task} with the specified completion status
     * and description.
     *
     * @param isDone Whether the task is marked as completed.
     * @param description The description of the task.
     */
    public Task(boolean isDone, String description) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Returns the status icon representing whether this task is completed.
     *
     * @return {@code "X"} if the task is completed, or a blank space otherwise.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Toggles the completion status of this task.
     */
    public void toggleIsDone() {
        this.isDone = !this.isDone;
    }

    /**
     * Returns whether this task is marked as completed.
     *
     * @return {@code true} if the task is completed, or {@code false} otherwise.
     */
    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Returns the file representation of this task in the expected save file format.
     *
     * @return A string representing this task in file storage format.
     */
    public abstract String fileFormat();

    /**
     * Returns a string representation of this task.
     *
     * @return A formatted string describing this task.
     */
    @Override
    public String toString() {
        return "["
                + this.getStatusIcon()
                + "] "
                + this.description;
    }

    public boolean matchesSearchQuery(String searchQuery) {
        return this.description.contains(searchQuery);
    }
}

