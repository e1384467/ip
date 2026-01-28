public class Deadline extends Task {

    protected String by;

    public Deadline(String descriptor, String by) {
        super(descriptor);
        this.by = by;
    }

    public Deadline(boolean isDone, String descriptor, String by) {
        super(isDone, descriptor);
        this.by = by;
    }

    @Override
    public String fileFormat() {
        return (super.isDone ? "1|D|" : "0|D|")
                + super.description
                + "|" + this.by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() +
                " (by: " + by + ")";
    }
}