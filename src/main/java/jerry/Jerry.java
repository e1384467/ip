package jerry;

import java.util.Scanner;

import jerry.commands.Commands;
import jerry.exceptions.JerryException;
import jerry.parser.Parser;
import jerry.storage.Storage;
import jerry.task.Task;
import jerry.task.TaskList;
import jerry.ui.Ui;

/**
 * Drives the execution of the Jerry task management application.
 */
public class Jerry {

    /** Handles user interaction such as reading input and displaying messages or error messages. */
    private Ui ui;

    /** Stores and manages the user's tasks. */
    private TaskList taskList;

    /**
     * Constructs a {@code Jerry} instance and initializes storage and task data.
     * If loading from storage fails, an empty task list is used.
     */
    public Jerry() {
        this.ui = new Ui(new Scanner(System.in));
        try {
            this.taskList = new TaskList(Storage.initialise());
        } catch (JerryException e) {
            this.ui.showError(e.getMessage());
            this.taskList = new TaskList();
        }
    }

    /**
     * Runs the main program loop to process user commands until the user exits.
     */
    public void run() {
        ui.showWelcome();
        while (true) {
            try {
                String userInput = this.ui.readUserInput();
                String[] userInputArray = userInput.split("\\s+");
                Commands userCommand = Commands.getCommand(userInputArray[0]);

                switch (userCommand) {
                case BYE:
                    Storage.save(taskList);
                    ui.showBye();
                    return;

                case LIST:
                    ui.displayList(taskList);
                    break;

                case MARK:
                    Task markTask = this.taskList.markTask(Parser.getArrayIndex(userInputArray));
                    ui.showMark(markTask);
                    Storage.save(taskList);
                    break;

                case UNMARK:
                    Task unmarkTask = this.taskList.unmarkTask(Parser.getArrayIndex(userInputArray));
                    ui.showUnmark(unmarkTask);
                    Storage.save(taskList);
                    break;

                case TODO:
                    Task todoTask = Parser.parseTodo(userInput.substring(Commands.TODO.toString().length()).trim());
                    this.taskList.add(todoTask);
                    ui.showAdd(todoTask, taskList.size());
                    Storage.save(taskList);
                    break;

                case DEADLINE:
                    Task deadlineTask = Parser
                            .parseDeadline(userInput
                                    .substring(Commands.DEADLINE.toString().length())
                                    .trim());
                    this.taskList.add(deadlineTask);
                    ui.showAdd(deadlineTask, taskList.size());
                    Storage.save(taskList);
                    break;

                case EVENT:
                    Task eventTask = Parser.parseEvent(userInput.substring(Commands.EVENT.toString().length()).trim());
                    this.taskList.add(eventTask);
                    ui.showAdd(eventTask, taskList.size());
                    Storage.save(taskList);
                    break;

                case DELETE:
                    Task deletedTask = taskList.deleteTask(Parser.getArrayIndex(userInputArray));
                    ui.showDelete(deletedTask, taskList.size());
                    Storage.save(taskList);
                    break;

                case FIND:
                    String searchQuery = Parser.getSearchQuery(userInput.substring(Commands.FIND.toString().length()).trim());
                    TaskList possibleResults = taskList.find(searchQuery);
                    ui.displayList(possibleResults);

                default:
                    break;
                }
            } catch (JerryException e) {
                this.ui.showError(e.getMessage());
            }
        }
    }

    /**
     * Starts the Jerry application.
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {
        Jerry jerry = new Jerry();
        jerry.run();
    }
}
