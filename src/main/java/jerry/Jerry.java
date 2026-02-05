package jerry;

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

    protected boolean isExit;
    protected String startUpError;

    /** Handles user interaction such as reading input and displaying messages or error messages. */
    private final Ui ui;

    /** Stores and manages the user's tasks. */
    private TaskList taskList;

    /**
     * Constructs a {@code Jerry} instance and initializes storage and task data.
     * If loading from storage fails, an empty task list is used.
     */
    public Jerry() {
        this.isExit = false;
        this.ui = new Ui();
        this.startUpError = "";
        try {
            this.taskList = new TaskList(Storage.initialise());
        } catch (JerryException e) {
            this.startUpError = e.getMessage();
            this.taskList = new TaskList();
        }
    }

    /**
     * Returns the welcome message shown to the user when the application starts.
     *
     * @return Greeting text displayed at the beginning of the session.
     */
    public String welcomeText() {
        return "Hello! What can I do for you?\n";
    }

    /**
     * Executes the command from the user input and returns the message to display.
     *
     * @param userInput user’s input command.
     * @return Message to be display.
     */
    public String getResponse(String userInput) {
        try {
            String[] userInputArray = userInput.trim().split("\\s+");
            Commands userCommand = Commands.getCommand(userInputArray[0]);

            switch (userCommand) {
            case BYE:
                isExit = true;
                Storage.writeTasksToFile(taskList);
                return ui.showBye();

            case LIST:
                return ui.displayList(taskList);

            case MARK:
                Task markTask = this.taskList.markTask(Parser.getArrayIndex(userInputArray));
                Storage.writeTasksToFile(taskList);
                return ui.showMark(markTask);

            case UNMARK:
                Task unmarkTask = this.taskList.unmarkTask(Parser.getArrayIndex(userInputArray));
                Storage.writeTasksToFile(taskList);
                return ui.showUnmark(unmarkTask);

            case TODO:
                Task todoTask = Parser.parseTodo(userInput.substring(Commands.TODO.toString().length()).trim());
                this.taskList.add(todoTask);
                Storage.writeTasksToFile(taskList);
                return ui.showAdd(todoTask, taskList.size());

            case DEADLINE:
                Task deadlineTask = Parser
                        .parseDeadline(userInput
                                .substring(Commands.DEADLINE.toString().length())
                                .trim());
                this.taskList.add(deadlineTask);
                Storage.writeTasksToFile(taskList);
                return ui.showAdd(deadlineTask, taskList.size());

            case EVENT:
                Task eventTask = Parser.parseEvent(userInput.substring(Commands.EVENT.toString().length()).trim());
                this.taskList.add(eventTask);
                Storage.writeTasksToFile(taskList);
                return ui.showAdd(eventTask, taskList.size());

            case DELETE:
                Task deletedTask = taskList.deleteTask(Parser.getArrayIndex(userInputArray));
                Storage.writeTasksToFile(taskList);
                return ui.showDelete(deletedTask, taskList.size());

            case FIND:
                String searchQuery = Parser.getSearchQuery(userInput
                        .substring(Commands.FIND.toString().length())
                        .trim());
                TaskList possibleResults = taskList.find(searchQuery);
                return ui.displayList(possibleResults);

            default:
                return "You have managed something not even god can do.";
            }
        } catch (JerryException e) {
            return e.getMessage();
        }
    }

}
