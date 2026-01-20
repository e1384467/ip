public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public void toggleIsDone() {
        this.isDone = !this.isDone;
    }

    public String printTask() {
        return "[" +
                this.getStatusIcon() +
                "] " +
                this.description;
    }
}