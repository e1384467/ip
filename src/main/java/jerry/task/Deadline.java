package jerry.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    protected LocalDateTime by;

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    public Deadline(boolean isDone, String description, LocalDateTime by) {
        super(isDone, description);
        this.by = by;
    }

    @Override
    public String fileFormat() {
        return (super.isDone ? "1|D|" : "0|D|")
                + super.description
                + "|" + this.by.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm"));
    }

    @Override
    public String toString() {
        return "[D]" + super.toString()
                + " (by: " + this.by.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")) + ")";
    }
}
