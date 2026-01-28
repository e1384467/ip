package jerry.ui;

import jerry.exceptions.EmptyInputException;
import jerry.exceptions.WrongArgumentException;
import jerry.task.Task;
import jerry.task.TaskList;

import java.util.Scanner;

public class Ui {

    public static final String CHATBOT_NAME = "Jerry";
    private final Scanner scan;

    public Ui(Scanner scan) {
        this.scan = scan;
    }

    public String readUserInput() throws EmptyInputException {
        System.out.print("User input: ");
        String userInput = this.scan.nextLine().trim();
        if (userInput.isEmpty()) {
            throw new EmptyInputException();
        }
        return userInput;
    }

    public void showWelcome() {
        System.out.println("Hello! I'm " + CHATBOT_NAME);
        System.out.println("What can I do for you?\n");
    }

    public void showBye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public void displayList(TaskList taskList ) throws WrongArgumentException {
        if (taskList.isEmpty()) {
            System.out.println(CHATBOT_NAME
                    + ": your list is currently empty. Type to add more!\n");
        } else {
            System.out.println("Your list:");
            System.out.println(taskList.buildListOutput());
        }
    }
    public void showMark(Task targetTask) {
        System.out.println(CHATBOT_NAME
                + ": Nice! I've marked this task as done -> "
                + targetTask + "\n");
    }

    public void showUnmark(Task targetTask) {
        System.out.println(CHATBOT_NAME
                + ": Okiee! I've unmarked this task as not done yet -> "
                + targetTask + "\n");
    }

    public void showDelete(Task targetTask, int size) {
        System.out.println(CHATBOT_NAME
                + "Got it! I've removed "
                + targetTask
                + ". You now have " + size + " task/s left\n");
    }

    public void showAdd(Task task, int size) {
        System.out.println(CHATBOT_NAME
                + ": I have added '" + task + "' to your list!");
        System.out.println("Now you have "
                + size + " tasks in the list!\n");
    }

    public void showError(String errorMessage) {
        System.out.println(errorMessage);
    }

}
