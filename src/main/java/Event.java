public class Event extends Task {

    protected String from;
    protected String to;

    public Event(String descriptor, String from, String to) {
        super(descriptor);
        this.from = from;
        this.to = to;
    }

    public Event(boolean isDone, String descriptor, String from, String to) {
        super(isDone, descriptor);
        this.from = from;
        this.to = to;
    }

    @Override
    public String fileFormat() {
        return (super.isDone ? "1 E " : "0 E ")
                + super.description
                + " /from " + this.from
                + " /to " + this.to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() +
                " (from: " + this.from + " to: " + this.to + ")";
    }
}