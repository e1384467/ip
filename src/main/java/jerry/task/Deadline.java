package jerry.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that must be completed by a specific date and time.
 * A {@code Deadline} task extends {@code Task} by including a due date.
 */
public class Deadline extends Task {

    /** The date and time by which the task must be completed. */
    protected LocalDateTime by;

    /**
     * Constructs a {@code Deadline} task with the specified description
     * and due date.
     *
     * @param description The description of the deadline task.
     * @param by The date and time by which the task must be completed.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Constructs a {@code Deadline} task with the specified completion status,
     * description, and due date.
     *
     * @param isDone Whether the task is marked as completed.
     * @param description The description of the deadline task.
     * @param by The date and time by which the task must be completed.
     */
    public Deadline(boolean isDone, String description, LocalDateTime by) {
        super(isDone, description);
        this.by = by;
    }

    /**
     * Returns the file representation of this deadline task in the expected save file format.
     *
     * @return A formatted string describing this deadline task in file storage format.
     */
    @Override
    public String fileFormat() {
        return (super.isDone ? "1|D|" : "0|D|")
                + super.description
                + "|" + this.by.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm"));
    }

    /**
     * Returns a string representation of this deadline task.
     *
     * @return A formatted string describing this deadline task.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() +
                " (by: " + this.by.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")) + ")";
    }
}