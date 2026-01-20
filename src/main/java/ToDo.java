public class ToDo extends Task {

    public ToDo(String descriptor) {
        super(descriptor);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}