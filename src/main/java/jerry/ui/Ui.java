package jerry.ui;

import jerry.exceptions.JerryException;
import jerry.task.Task;
import jerry.task.TaskList;

/**
 * Handles all user interaction for Jerry, including reading user input and displaying messages to the user.
 */
public class Ui {

    /**
     * Returns the welcome message shown to the user when the application starts.
     *
     * @return Greeting text displayed at the beginning of the session.
     */
    public static String showWelcomeText() {
        return "Hello! What can I do for you?\n";
    }

    /**
     * Displays the goodbye message to the user.
     *
     * @return The goodbye message to be displayed.
     */
    public String showBye() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Displays the task list to the user.
     *
     * @param taskList The task list to display.
     * @return The list to be displayed.
     * @throws JerryException If building the list output fails.
     */
    public String showList(TaskList taskList) throws JerryException {
        if (taskList.isEmpty()) {
            return "Your list is currently empty. Type to add more!\n";
        }
        return "Your list:" + "\n"
                + taskList.buildListOutput();

    }

    /**
     * Displays a confirmation message that the specified task has been marked as done.
     *
     * @param targetTask The task that was marked as done.
     * @return The marked task to be displayed.
     */
    public String showMark(Task targetTask) {
        return "Nice! I've marked this task as done -> "
                + targetTask + "\n";
    }

    /**
     * Displays a confirmation message that the specified task has been unmarked as not done.
     *
     * @param targetTask The task that was unmarked.
     * @return The unmarked task to be displayed.
     */
    public String showUnmark(Task targetTask) {
        return "Okiee! I've unmarked this task as not done yet -> "
                + targetTask + "\n";
    }

    /**
     * Displays a confirmation message that the specified task has been deleted.
     *
     * @param targetTask The task that was deleted.
     * @param size The number of tasks remaining after deletion.
     * @return The delete task and the resulting list to be displayed.
     */
    public String showDelete(Task targetTask, int size) {
        return "Got it! I've removed "
                + targetTask
                + ". You now have " + size + " task(s) left\n";
    }

    /**
     * Displays a confirmation message that the specified task has been added.
     *
     * @param task The task that was added.
     * @param size The number of tasks in the list after adding.
     * @return The task added and the size of the list to be displayed.
     */
    public String showAdd(Task task, int size) {
        return "I have added '" + task + "' to your list!\n"
                + "Now you have "
                + size + " tasks in the list!";
    }

    /**
     * Displays the results of a find command, showing the tasks that match the search query.
     *
     * @param possibleResults The list of tasks that match the search query.
     * @return A message showing the matching tasks, or a message indicating that no matches were found.
     */
    public String showFindResult(TaskList possibleResults) {
        if (possibleResults.isEmpty()) {
            return "No matching tasks found for your search query :(\n";
        }
        return "Here are the matching tasks in your list:" + "\n"
                + possibleResults.buildListOutput();
    }
}
