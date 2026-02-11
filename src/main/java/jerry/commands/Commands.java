package jerry.commands;

import jerry.exceptions.InvalidCommandException;
import jerry.exceptions.JerryException;
import jerry.parser.Parser;
import jerry.storage.Storage;
import jerry.task.Task;
import jerry.task.TaskList;
import jerry.ui.Ui;

/**
 * Represents the set of valid command keywords supported by Jerry.
 * Each enum constant corresponds to a command a user can enter (e.g., {@code todo}, {@code list}).
 */
public enum Commands {
    BYE {
        @Override
        public String execute(TaskList taskList, Ui ui, String userInputArguments) throws JerryException {
            Storage.writeTasksToFile(taskList);
            return ui.showBye();
        }
    },
    LIST{
        @Override
        public String execute(TaskList taskList, Ui ui, String userInputArguments) throws JerryException {
            return ui.displayList(taskList);
        }
    },
    MARK{
        @Override
        public String execute(TaskList taskList, Ui ui, String userInputArguments) throws JerryException {
            int arrayIndex = Parser.getArrayIndex(userInputArguments);
            Task markTask = taskList.markTask(arrayIndex);

            Storage.writeTasksToFile(taskList);
            return ui.showMark(markTask);
        }
    },
    UNMARK{
        @Override
        public String execute(TaskList taskList, Ui ui, String userInputArguments) throws JerryException {
            int arrayIndex = Parser.getArrayIndex(userInputArguments);
            Task unmarkTask = taskList.unmarkTask(arrayIndex);

            Storage.writeTasksToFile(taskList);
            return ui.showUnmark(unmarkTask);
        }
    },
    TODO{
        @Override
        public String execute(TaskList taskList, Ui ui, String userInputArguments) throws JerryException {
            Task todoTask = Parser.parseTodo(userInputArguments);
            taskList.add(todoTask);

            Storage.writeTasksToFile(taskList);
            return ui.showAdd(todoTask, taskList.size());
        }
    },
    DEADLINE{
        @Override
        public String execute(TaskList taskList, Ui ui, String userInputArguments) throws JerryException {
            Task deadlineTask = Parser.parseDeadline(userInputArguments);
            taskList.add(deadlineTask);

            Storage.writeTasksToFile(taskList);
            return ui.showAdd(deadlineTask, taskList.size());
        }
    },
    EVENT{
        @Override
        public String execute(TaskList taskList, Ui ui, String userInputArguments) throws JerryException {
            Task eventTask = Parser.parseEvent(userInputArguments);
            taskList.add(eventTask);

            Storage.writeTasksToFile(taskList);
            return ui.showAdd(eventTask, taskList.size());
        }
    },
    DELETE{
        @Override
        public String execute(TaskList taskList, Ui ui, String userInputArguments) throws JerryException {
            int arrayIndex = Parser.getArrayIndex(userInputArguments);
            Task deletedTask = taskList.deleteTask(arrayIndex);

            Storage.writeTasksToFile(taskList);
            return ui.showDelete(deletedTask, taskList.size());
        }
    },
    FIND{
        @Override
        public String execute(TaskList taskList, Ui ui, String userInputArguments) throws JerryException {
            String searchQuery = Parser.getSearchQuery(userInputArguments);
            TaskList possibleResults = taskList.find(searchQuery);
            return ui.displayList(possibleResults);
        }
    };

    /**
     * Returns the {@code Commands} enum that matches the given user command input.
     * The matching is case-insensitive (e.g., {@code "todo"} and {@code "TODO"} both match {@link #TODO}).
     *
     * @param userCommandInput The raw command keyword entered by the user.
     * @return The corresponding {@code Commands} enum constant.
     * @throws InvalidCommandException If {@code userCommandInput} does not match any supported command keyword.
     */
    public static Commands getCommand(String userCommandInput) throws InvalidCommandException {
        try {
            return Commands.valueOf(userCommandInput.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandException();
        }
    }

    public abstract String execute(TaskList taskList, Ui ui, String userInputArguments) throws JerryException;
}
