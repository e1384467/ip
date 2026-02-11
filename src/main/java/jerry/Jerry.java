package jerry;

import jerry.commands.Commands;
import jerry.exceptions.JerryException;
import jerry.parser.Parser;
import jerry.storage.Storage;
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
            String userInputCommand = Parser.getUserInputCommand(userInput);
            Commands userCommand = Commands.getCommand(userInputCommand);

            String userInputArguments = Parser.getUserInputArguments(userInput);

            return userCommand.execute(taskList, ui, userInputArguments);

        } catch (JerryException e) {
            return e.getMessage();
        }
    }

}
