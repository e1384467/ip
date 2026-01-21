import java.util.Scanner;
import java.util.ArrayList;

public class Jerry {

    public static final String CHATBOT_NAME = "Jerry";
    public static final String EXIT = "bye";
    public static final String SHOW_LIST = "list";
    public static final String MARK_TASK = "mark";
    public static final String UNMARK_TASK = "unmark";
    public static final String TODO_TASK = "todo";
    public static final String DEADLINE_TASK = "deadline";
    public static final String EVENT_TASK= "event";

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
        taskList.add(task);
        System.out.println(CHATBOT_NAME +
                ": I have added '" +
                task +
                "' to your list!");
        System.out.println("Now you have " +
                taskList.size() +
                " tasks in the list!\n");
    }

    public Task getTask(String[] userInputByWord) throws MissingArgumentException, WrongArgumentException {
        try {
            int index = Integer.parseInt(userInputByWord[1]);
            return this.taskList.get(index - 1);
        } catch (NumberFormatException e) {
            throw new WrongArgumentException("Wrong Argument >:( !!!! THIS IS NOT A NUMBER\n");
        } catch (IndexOutOfBoundsException e) {
            if (userInputByWord.length < 2) {
                throw new MissingArgumentException("Missing Argument >:( !!!! Please try:\n" +
                        "Mark <your task index from list>\n" +
                        "or\n" +
                        "Unmark <your task index from list>\n");
            }
            throw new WrongArgumentException((taskList.isEmpty() ?
                    ("Wrong Argument >:( !!!! Your task list is empty currently") :
                    ("Wrong Argument >:( !!!! Please enter a number in the range of 1 to " +
                            taskList.size())) +
                    "\nUse Command: list. To check your task list first :)\n");
        }


    }

    public void markTask(Task targetTask) throws RepeatedActionsException  {
        if (targetTask.isDone) {
            throw new RepeatedActionsException("Repeated Action >:( !!!!\n" +
                    "You've made a mistake, " +
                    targetTask +
                    " is already marked as done\n");
        }
        targetTask.toggleIsDone();
        System.out.println(CHATBOT_NAME +
                ": Nice! I've marked this task as done -> " +
                targetTask + "\n");

    }

    public void unmarkTask(Task targetTask) throws RepeatedActionsException{
        if (!targetTask.isDone) {
            throw new RepeatedActionsException("Repeated Action >:( !!!!\n" +
                    "You've made a mistake, " +
                    targetTask +
                    " is already unmarked as not done yet\n");
        }
        targetTask.toggleIsDone();
        System.out.println(CHATBOT_NAME +
                ": Okiee! I've marked this task as not done yet -> " +
                targetTask + "\n");

    }

    public void toDoTask(String userInput) throws MissingArgumentException {
        String taskDescription = userInput.substring(TODO_TASK.length()).trim();
        if (taskDescription.isEmpty()) {
            throw new MissingArgumentException("Missing Argument >:( !!!! Please try:\n" +
                    "Todo <your task goes here>\n");
        }
        Task task = new ToDo(taskDescription);
        addTaskPrint(task);
    }

    public void deadlineTask(String userInput) throws MissingArgumentException, WrongArgumentException{
        try {
            String information = userInput.substring(DEADLINE_TASK.length()).trim();
            String[] informationSeparate = information.split("(?i)\\s*/by\\s*", 2);
            String taskDescription = informationSeparate[0];
            String deadline = informationSeparate[1];
            if (taskDescription.isEmpty()|| deadline.isEmpty()) {
                throw new MissingArgumentException("Missing Argument >:( !!!! Please try:\n" +
                        "deadline <your task goes here> /by <add your date/time here\n");
            }
            Task task = new Deadline(taskDescription, deadline);
            addTaskPrint(task);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new MissingArgumentException("Missing Argument >:( !!!! Please try:\n" +
                    "deadline <your task goes here>\n");
        }
    }

    public void eventTask(String userInput) {
        String information = userInput.substring(EVENT_TASK.length()).trim();
        String[] informationSeparate = information.split("\\s*/from\\s*",2);
        String taskDescription = informationSeparate[0];
        String[] furtherSplit = informationSeparate[1].split("\\s*/to\\s*",2);
        String from = furtherSplit[0];
        String to = furtherSplit[1];
        Task task = new Event(taskDescription, from, to);
        addTaskPrint(task);
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
                        markTask(getTask(userInputByWord));
                        break;
                    case UNMARK_TASK:
                        unmarkTask(getTask(userInputByWord));
                        break;

                    case TODO_TASK:
                        toDoTask(userInput);
                        break;

                    case DEADLINE_TASK:
                        deadlineTask(userInput);
                        break;

                    case EVENT_TASK:
                        eventTask(userInput);
                        break;

                    default:
                        throw new InvalidCommandException();
                }
            } catch (DukeException e) {
                System.out.println(e.getMessage());
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
