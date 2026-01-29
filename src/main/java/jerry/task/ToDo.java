package jerry.task;

public class ToDo extends Task {

    public ToDo(String description) {
        super(description);
    }

    public ToDo(boolean isDone, String description) {
        super(isDone, description);
    }

    @Override
    public String fileFormat() {
        return (super.isDone ? "1|T|" : "0|T|") + super.description;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
