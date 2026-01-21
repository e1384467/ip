public class InvalidCommandException extends DukeException {

    private static final String COMMAND_LIST =
            "Command: list (to view your tasks in a list)\n" +
            "e.g. list\n" +
            "Command: Todo <add your task here>\n" +
            "e.g. todo clean house\n" +
            "Command: Deadline <add your task here> /by <add your date/time here>\n" +
            "e.g. deadline submit report /by Monday 11/10/2019 5 pm\n" +
            "Command: Event <add your task here> /from <add your start date/time here> /to <add your end date/time here>\n" +
            "e.g. event project meeting /from Tuesday 12/10/2019 1pm /to Tuesday 12/10/2019 2pm\n" +
            "Command: Mark <your task index from list>\n" +
            "e.g. mark 1\n" +
            "Command: Unmark <your task index from list> \n" +
            "e.g. unmark 2\n" +
            "Command: Bye\n" +
            "e.g. bye\n" ;

    InvalidCommandException() {
        super("Invalid Command >:c\n" + COMMAND_LIST);
    }
    InvalidCommandException(String outputMessage) {
        super(outputMessage + COMMAND_LIST);
    }
}