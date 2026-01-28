package jerry.task;

public class ToDo extends Task {

    public ToDo(String descriptor) {
        super(descriptor);
    }

    public ToDo(boolean isDone, String descriptor) {
        super(isDone, descriptor);
    }

    @Override
    public String fileFormat() {
        return (super.isDone ? "1|T|" : "0|T|")  + super.description;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}