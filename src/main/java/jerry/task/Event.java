package jerry.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that occurs over a specific period of time.
 * An {@code Event} task extends {@code Task} by including a start time
 * and an end time.
 */
public class Event extends Task {

    /** The start date and time of the event. */
    protected LocalDateTime from;

    /** The end date and time of the event. */
    protected LocalDateTime to;

    /**
     * Constructs an {@code Event} task with the specified description,
     * start time, and end time.
     *
     * @param description The description of the event task.
     * @param from The start date and time of the event.
     * @param to The end date and time of the event.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Constructs an {@code Event} task with the specified completion status,
     * description, start time, and end time.
     *
     * @param isDone Whether the task is marked as completed.
     * @param description The description of the event task.
     * @param from The start date and time of the event.
     * @param to The end date and time of the event.
     */
    public Event(boolean isDone, String description, LocalDateTime from, LocalDateTime to) {
        super(isDone, description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the file representation of this event task in the expected save file format.
     *
     * @return A formatted string describing this event task in file storage format.
     */
    @Override
    public String fileFormat() {
        return (super.isDone ? "1|E|" : "0|E|")
                + super.description
                + "|" + this.from.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm"))
                + "|" + this.to.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm"));
    }

    /**
     * Returns a string representation of this event task.
     *
     * @return A formatted string describing this event task.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + this.from.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"))
                + " to: " + this.to.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")) + ")";
    }
}
