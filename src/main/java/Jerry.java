import java.util.Scanner;
import java.util.ArrayList;

public class Jerry {

    public static final String CHATBOT_NAME = "Jerry";
    private final ArrayList<Task> taskList;

    public Jerry() throws JerryException {
        this.taskList = Storage.initialise();
    }

    public void printList() {
        System.out.println("Your list:");
        for (int index = 0; index < this.taskList.size(); index += 1) {
            System.out.println(index + 1 + "." + this.taskList.get(index));
        }
        System.out.println();
    }

    public void addTaskAndPrint(Task task) {
        taskList.add(task);
        System.out.println(CHATBOT_NAME
                + ": I have added '" + task + "' to your list!");
        System.out.println("Now you have "
                + taskList.size() + " tasks in the list!\n");
    }

    public void deleteTask(int targetIndex) throws JerryException {
        Task targetTask = taskList.get(targetIndex);
        taskList.remove(targetTask);
        System.out.println(CHATBOT_NAME
                + "Got it! I've removed "
                + targetTask
                + ". You now have " + taskList.size() + " task/s left\n");
    }

    public void markTask(int targetIndex) throws JerryException  {
        Task targetTask = taskList.get(targetIndex);
        if (targetTask.isDone) {
            throw new RepeatedActionsException( "You've made a mistake, "
                    + targetTask
                    + " is already marked as done\n");
        }
        targetTask.toggleIsDone();
        System.out.println(CHATBOT_NAME
                + ": Nice! I've marked this task as done -> "
                + targetTask + "\n");

    }

    public void unmarkTask(int targetIndex) throws JerryException {
        Task targetTask = taskList.get(targetIndex);
        if (!targetTask.isDone) {
            throw new RepeatedActionsException("You've made a mistake, "
                    + targetTask
                    + " is already unmarked as not done yet\n");
        }
        targetTask.toggleIsDone();
        System.out.println(CHATBOT_NAME
                + ": Okiee! I've unmarked this task as not done yet -> "
                + targetTask + "\n");

    }

    public void addTodoTask(String taskDescription) throws JerryException {
        addTaskAndPrint(Parser.parseTodo(taskDescription));
    }

    public void addDeadlineTask(String userInput) throws JerryException {
        addTaskAndPrint(Parser.parseDeadline(userInput));
    }

    public void addEventTask(String userInput) throws JerryException {
        addTaskAndPrint(Parser.parseEvent(userInput));
    }

    public void readUserInput() {
        Scanner scan = new Scanner(System.in);
        String userInput;
        while (true) {
            try {
                System.out.print("User input: ");
                userInput = scan.nextLine().trim();
                if (userInput.isEmpty()) {
                    throw new EmptyInputException();
                }
                String[] userInputArray = userInput.split("\\s+");
                Commands userCommand = Commands.getCommand(userInputArray[0]);

                switch (userCommand) {
                    case BYE:
                        Storage.save(taskList);
                        return;

                    case LIST:
                        if (this.taskList.isEmpty()) {
                            System.out.println(CHATBOT_NAME
                                    + ": your list is currently empty. Type to add more!\n");
                        } else {
                            printList();
                        }
                        break;

                    case MARK:
                        markTask(Parser.getArrayIndex(userInputArray, this.taskList.size()));
                        Storage.save(taskList);
                        break;

                    case UNMARK:
                        unmarkTask(Parser.getArrayIndex(userInputArray, this.taskList.size()));
                        Storage.save(taskList);
                        break;

                    case TODO:
                        addTodoTask(userInput.substring(Commands.TODO.toString().length()).trim());
                        Storage.save(taskList);
                        break;

                    case DEADLINE:
                        addDeadlineTask(userInput.substring(Commands.DEADLINE.toString().length()).trim());
                        Storage.save(taskList);
                        break;

                    case EVENT:
                        addEventTask(userInput.substring(Commands.EVENT.toString().length()).trim());
                        Storage.save(taskList);
                        break;

                    case DELETE:
                        deleteTask(Parser.getArrayIndex(userInputArray, this.taskList.size()));
                        Storage.save(taskList);
                        break;
                }
            } catch (JerryException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args)  {
        try {
            Jerry jerry = new Jerry();
            System.out.println("Hello! I'm " + CHATBOT_NAME);
            System.out.println("What can I do for you?\n");
            jerry.readUserInput();
            System.out.println("Bye. Hope to see you again soon!");
        } catch (JerryException e) {
            System.out.println(e.getMessage());
        }

    }
}
