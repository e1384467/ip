package jerry.ui;

import jerry.exceptions.EmptyInputException;
import jerry.exceptions.JerryException;
import jerry.exceptions.WrongArgumentException;
import jerry.task.Task;
import jerry.task.TaskList;

import java.util.Scanner;

/**
 * Handles all user interaction for Jerry, including reading user input and displaying messages to the user.
 */
public class Ui {

    /** Name of the chatbot shown in user-facing messages. */
    public static final String CHATBOT_NAME = "Jerry";

    /** Scanner used to read user input. */
    private final Scanner scan;

    /**
     * Constructs a {@code Ui} that reads input using the given scanner.
     *
     * @param scan The scanner to read user input from.
     */
    public Ui(Scanner scan) {
        this.scan = scan;
    }

    /**
     * Returns a trimmed line of user input read from the scanner.
     *
     * @return The user's input string.
     * @throws EmptyInputException If the user enters an empty input.
     */
    public String readUserInput() throws EmptyInputException {
        System.out.print("User input: ");
        String userInput = this.scan.nextLine().trim();
        if (userInput.isEmpty()) {
            throw new EmptyInputException();
        }
        return userInput;
    }

    /**
     * Displays the welcome message to the user.
     */
    public void showWelcome() {
        System.out.println("Hello! I'm " + CHATBOT_NAME);
        System.out.println("What can I do for you?\n");
    }

    /**
     * Displays the goodbye message to the user.
     */
    public void showBye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Displays the task list to the user.
     *
     * @param taskList The task list to display.
     * @throws JerryException If building the list output fails.
     */
    public void displayList(TaskList taskList) throws JerryException {
        if (taskList.isEmpty()) {
            System.out.println(CHATBOT_NAME
                    + ": your list is currently empty. Type to add more!\n");
        } else {
            System.out.println("Your list:");
            System.out.println(taskList.buildListOutput());
        }
    }

    /**
     * Displays a confirmation message that the specified task has been marked as done.
     *
     * @param targetTask The task that was marked as done.
     */
    public void showMark(Task targetTask) {
        System.out.println(CHATBOT_NAME
                + ": Nice! I've marked this task as done -> "
                + targetTask + "\n");
    }

    /**
     * Displays a confirmation message that the specified task has been unmarked as not done.
     *
     * @param targetTask The task that was unmarked.
     */
    public void showUnmark(Task targetTask) {
        System.out.println(CHATBOT_NAME
                + ": Okiee! I've unmarked this task as not done yet -> "
                + targetTask + "\n");
    }

    /**
     * Displays a confirmation message that the specified task has been deleted.
     *
     * @param targetTask The task that was deleted.
     * @param size The number of tasks remaining after deletion.
     */
    public void showDelete(Task targetTask, int size) {
        System.out.println(CHATBOT_NAME
                + "Got it! I've removed "
                + targetTask
                + ". You now have " + size + " task/s left\n");
    }

    /**
     * Displays a confirmation message that the specified task has been added.
     *
     * @param task The task that was added.
     * @param size The number of tasks in the list after adding.
     */
    public void showAdd(Task task, int size) {
        System.out.println(CHATBOT_NAME
                + ": I have added '" + task + "' to your list!");
        System.out.println("Now you have "
                + size + " tasks in the list!\n");
    }
    /**
     * Displays the specified error message to the user.
     *
     * @param errorMessage The error message to display.
     */
    public void showError(String errorMessage) {
        System.out.println(errorMessage);
    }

}
