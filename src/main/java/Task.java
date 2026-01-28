public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public Task(boolean isDone,String description) {
        this.description = description;
        this.isDone = isDone;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public void toggleIsDone() {
        this.isDone = !this.isDone;
    }

    public abstract String fileFormat();

    @Override
    public String toString() {
        return "[" +
                this.getStatusIcon() +
                "] " +
                this.description;
    }
}