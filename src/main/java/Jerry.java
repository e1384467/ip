import java.util.Scanner;
import java.util.ArrayList;

public class Jerry {

    public static final String CHATBOT_NAME = "Jerry";
    public static final String EXIT = "bye";
    public static final String SHOW_LIST = "list";
    public static final String MARK_TASK = "mark";
    public static final String UNMARK_TASK = "unmark";
    public static final String TODO_TASK = "todo";

    private final ArrayList<Task> taskList;

    public Jerry() {
        this.taskList = new ArrayList<>();
    }

    public void printList() {
        System.out.println("Your list:");
        for (int index = 0; index < this.taskList.size(); index += 1) {
            System.out.println(index + 1 + "." +
                    this.taskList.get(index));
        }
        System.out.println();
    }

    public void addTaskPrint(Task task) {
        System.out.println(CHATBOT_NAME +
                ": I have added '" +
                task +
                "' to your list!");
        System.out.println("Now you have " +
                taskList.size() +
                " tasks in the list!\n");
    }

    public Task getTask(String taskNumber) {
        int index = Integer.parseInt(taskNumber) - 1;
        return this.taskList.get(index);
    }

    public void markTask(Task targetTask) {
        if (targetTask.isDone)
        {
            System.out.println("This task is already Marked and Done\n");
        } else {
            targetTask.toggleIsDone();
            System.out.println(CHATBOT_NAME +
                    ": Nice! I've marked this task as done -> " +
                    targetTask + "\n");
        }
    }

    public void unmarkTask(Task targetTask) {
        if (!targetTask.isDone)
        {
            System.out.println("This task is already Unmarked\n");
        } else {
            targetTask.toggleIsDone();
            System.out.println(CHATBOT_NAME +
                    ": Okiee! I've marked this task as not done yet -> " +
                    targetTask + "\n");
        }
    }

    public void addToDoTask(String userInput) {
        String task_description = userInput.substring(TODO_TASK.length()).trim();
        Task task = new ToDo(task_description);
        taskList.add(task);
        addTaskPrint(task);
    }

    public void readUserInput() {
        Scanner scan = new Scanner(System.in);
        String userInput;
        while (true) {
            System.out.print("User input: ");
            userInput = scan.nextLine();
            String[] userInputByWord = userInput.split("\\s+");
            String userCommand = userInputByWord[0].toLowerCase();

            switch (userCommand) {
                case EXIT:
                    return;

                case SHOW_LIST:
                    if (this.taskList.isEmpty()) {
                        System.out.println(CHATBOT_NAME +
                                ": your list is currently empty. Type to add more!\n");
                    } else {
                        printList();
                    }
                    break;

                case MARK_TASK:
                    markTask(getTask(userInputByWord[1]));
                    break;
                case UNMARK_TASK:
                    unmarkTask(getTask(userInputByWord[1]));
                    break;

                case TODO_TASK:
                    addToDoTask(userInput);
                    break;
                default:
                    System.out.println("Invalid Command");

            }
        }
    }

    public static void main(String[] args) {
        Jerry jerry = new Jerry();
        System.out.println("Hello! I'm " + CHATBOT_NAME);
        System.out.println("What can I do for you?\n");
        jerry.readUserInput();
        System.out.println("Bye. Hope to see you again soon!");
    }
}
