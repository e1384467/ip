package jerry.task;

import java.util.ArrayList;

import jerry.exceptions.JerryException;
import jerry.exceptions.RepeatedActionsException;
import jerry.exceptions.WrongArgumentException;

/**
 * Represents a list of tasks and provides operations to add, retrieve, update, and remove tasks.
 */
public class TaskList {

    /** Stores all tasks managed by this task list. */
    private final ArrayList<Task> taskList;

    /**
     * Constructs an empty {@code TaskList}.
     */
    public TaskList() {
        this.taskList = new ArrayList<Task>();
    }

    /**
     * Constructs a {@code TaskList} backed by the given list of tasks.
     *
     * @param taskList The list of tasks to be managed by this task list.
     */
    public TaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    /**
     * Returns the task at the specified zero-based index.
     *
     * @param index The zero-based index of the task in the list.
     * @return The task at the specified index.
     * @throws JerryException If the index is out of range.
     */
    public Task get(int index) throws JerryException {
        if (index < 0 || index >= this.taskList.size()) {
            throw new WrongArgumentException((this.taskList.isEmpty()
                            ? ("Your task list is empty currently")
                            : ("Please enter a number in the range of 1 to " + this.taskList.size()))
                            + "\nUse Command: list. To check your task list first :)\n");
        }
        return this.taskList.get(index);
    }

    /**
     * Deletes the task at the specified zero-based index.
     *
     * @param targetIndex The zero-based index of the task to delete.
     * @return The deleted task.
     * @throws JerryException If the index is out of range.
     */
    public Task deleteTask(int targetIndex) throws JerryException {
        Task targetTask = get(targetIndex);
        this.taskList.remove(targetTask);
        return targetTask;
    }


    /**
     * Marks the task at the specified zero-based index as done.
     *
     * @param targetIndex The zero-based index of the task to mark.
     * @return The marked task.
     * @throws JerryException If the index is out of range or the task is already marked as done.
     */
    public Task markTask(int targetIndex) throws JerryException {
        Task targetTask = get(targetIndex);
        if (targetTask.isDone()) {
            throw new RepeatedActionsException("You've made a mistake, "
                    + targetTask
                    + " is already marked as done\n");
        }
        targetTask.toggleIsDone();
        return targetTask;
    }

    /**
     * Unmarks the task at the specified zero-based index as not done.
     *
     * @param targetIndex The zero-based index of the task to unmark.
     * @return The unmarked task.
     * @throws JerryException If the index is out of range or the task is already unmarked.
     */
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

    /**
     * Adds the specified task to this task list.
     *
     * @param task The task to add.
     */
    public void add(Task task) {
        this.taskList.add(task);
    }

    /**
     * Returns the number of tasks in this task list.
     *
     * @return The number of tasks in the list.
     */
    public int size() {
        return this.taskList.size();
    }

    /**
     * Returns whether this task list contains no tasks.
     *
     * @return {@code true} if the list is empty, or {@code false} otherwise.
     */
    public boolean isEmpty() {
        return this.taskList.isEmpty();
    }

    /**
     * Returns a formatted list of all tasks with one-based indices for display.
     *
     * @return A formatted string containing all tasks in the list.
     * @throws JerryException If a task retrieval fails due to an invalid index.
     */
    public String buildListOutput() throws JerryException {
        String listOutput = "";
        for (int index = 0; index < taskList.size(); index += 1) {
            int displayIndex = index + 1;
            listOutput = listOutput.concat(Integer.toString(displayIndex) + ". "
                    + get(index).toString()
                    + System.lineSeparator());
        }
        return listOutput;
    }
}
