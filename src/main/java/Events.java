public class Events extends Task {

    protected String from;
    protected String to;

    public Events(String descriptor, String from, String to) {
        super(descriptor);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() +
                " (from: " + this.from + " to: " + this.to + ")";
    }
}