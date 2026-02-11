package jerry.ui;

import jerry.exceptions.JerryException;
import jerry.task.Task;
import jerry.task.TaskList;

/**
 * Handles all user interaction for Jerry, including reading user input and displaying messages to the user.
 */
public class Ui {

    /** Name of the chatbot shown in user-facing messages. */
    public static final String CHATBOT_NAME = "Jerry";

    /**
     * Returns the welcome message shown to the user when the application starts.
     *
     * @return Greeting text displayed at the beginning of the session.
     */
    public static String welcomeText() {
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
    public String displayList(TaskList taskList) throws JerryException {
        if (taskList.isEmpty()) {
            return CHATBOT_NAME
                    + ": your list is currently empty. Type to add more!\n";
        } else {
            return "Your list:" + "\n"
                    + taskList.buildListOutput();
        }
    }

    /**
     * Displays a confirmation message that the specified task has been marked as done.
     *
     * @param targetTask The task that was marked as done.
     * @return The marked task to be displayed.
     */
    public String showMark(Task targetTask) {
        return CHATBOT_NAME
                + ": Nice! I've marked this task as done -> "
                + targetTask + "\n";
    }

    /**
     * Displays a confirmation message that the specified task has been unmarked as not done.
     *
     * @param targetTask The task that was unmarked.
     * @return The unmarked task to be displayed.
     */
    public String showUnmark(Task targetTask) {
        return CHATBOT_NAME
                + ": Okiee! I've unmarked this task as not done yet -> "
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
        return CHATBOT_NAME
                + "Got it! I've removed "
                + targetTask
                + ". You now have " + size + " task/s left\n";
    }

    /**
     * Displays a confirmation message that the specified task has been added.
     *
     * @param task The task that was added.
     * @param size The number of tasks in the list after adding.
     * @return The task added and the size of the list to be displayed.
     */
    public String showAdd(Task task, int size) {
        return CHATBOT_NAME
                + ": I have added '" + task + "' to your list!\n"
                + "Now you have "
                + size + " tasks in the list!";
    }
}
