import java.util.ArrayList;

public class TaskList {

    private final ArrayList<Task> taskList;

    public TaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    public Task get(int index) throws WrongArgumentException {
        if (index < 0 || index >= this.taskList.size()) {
            throw new WrongArgumentException((this.taskList.isEmpty()
                            ? ("Your task list is empty currently")
                            : ("Please enter a number in the range of 1 to " + this.taskList.size()))
                            + "\nUse Command: list. To check your task list first :)\n");
        }
        return this.taskList.get(index);
    }

    public Task deleteTask(int targetIndex) throws JerryException {;
        Task targetTask = get(targetIndex);
        this.taskList.remove(targetTask);
        return targetTask;
    }

    public Task markTask(int targetIndex) throws JerryException{
        Task targetTask = get(targetIndex);
        if (targetTask.isDone()) {
            throw new RepeatedActionsException( "You've made a mistake, "
                    + targetTask
                    + " is already marked as done\n");
        }
        targetTask.toggleIsDone();
        return targetTask;
    }

    public Task unmarkTask(int targetIndex) throws JerryException {
        Task targetTask = get(targetIndex);
        if (!targetTask.isDone()) {
            throw new RepeatedActionsException("You've made a mistake, "
                    + targetTask
                    + " is already unmarked as not done yet\n");
        }
        targetTask.toggleIsDone();
        return targetTask;
    }

    public void add(Task task) {
        this.taskList.add(task);
    }

    public int size() {
        return this.taskList.size();
    }

    public boolean isEmpty() {
        return this.taskList.isEmpty();
    }

    public String buildListOutput() throws WrongArgumentException {
        String listOutput = "";
        for (int index = 0; index < taskList.size(); index += 1) {
            int displayIndex = index + 1;
            listOutput = listOutput.concat(Integer.toString(displayIndex) + ". " + get(index).toString() + System.lineSeparator());
        }
        return listOutput;
    }

}
