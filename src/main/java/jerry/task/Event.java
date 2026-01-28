package jerry.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {

    protected LocalDateTime from;
    protected LocalDateTime to;

    public Event(String descriptor, LocalDateTime from, LocalDateTime to) {
        super(descriptor);
        this.from = from;
        this.to = to;
    }

    public Event(boolean isDone, String descriptor, LocalDateTime from, LocalDateTime to) {
        super(isDone, descriptor);
        this.from = from;
        this.to = to;
    }

    @Override
    public String fileFormat() {
        return (super.isDone ? "1|E|" : "0|E|")
                + super.description
                + "|" + this.from.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm"))
                + "|" + this.to.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm"));
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() +
                " (from: " + this.from.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"))
                + " to: " + this.to.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")) + ")";
    }
}