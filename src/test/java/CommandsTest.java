import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import jerry.commands.Commands;
import jerry.exceptions.InvalidCommandException;
import jerry.exceptions.JerryException;

public class CommandsTest {

    @Test
    public void getCommand_success() throws InvalidCommandException {
        assertEquals(Commands.BYE, Commands.getCommand("bye"));
        assertEquals(Commands.LIST, Commands.getCommand("list"));
        assertEquals(Commands.MARK, Commands.getCommand("mark"));
        assertEquals(Commands.UNMARK, Commands.getCommand("unmark"));
        assertEquals(Commands.TODO, Commands.getCommand("todo"));
        assertEquals(Commands.DEADLINE, Commands.getCommand("deadline"));
        assertEquals(Commands.EVENT, Commands.getCommand("event"));
        assertEquals(Commands.DELETE, Commands.getCommand("delete"));
    }

    @Test
    public void getCommand_notACommand_invalidCommandExceptionThrown() {
        try {
            assertEquals(Commands.BYE, Commands.getCommand("notACommand"));
            fail();
        } catch (JerryException e) {
            assertEquals("Invalid Command >:c\n"
                    + "Command: list (to view your tasks in a list)\n"
                    + "e.g. list\n"
                    + "Command: Todo <add your task here>\n"
                    + "e.g. todo clean house\n"
                    + "Command: jerry.task.Deadline <add your task here> /by <ddmmyyyy hhmm (24-hour clock)>\n"
                    + "e.g. deadline submit report /by 06062002 0530\n"
                    + "Command: jerry.task.Event <add your task here> "
                    + "/from <ddmmyyyy hhmm (24-hour clock)> /to <ddmmyyyy hhmm (24-hour clock)>\n"
                    + "e.g. event project meeting /from 06062002 0530 /to 07062002 0500 \n"
                    + "Command: Mark <your task index from list>\n"
                    + "e.g. mark 1\n"
                    + "Command: Unmark <your task index from list>\n"
                    + "e.g. unmark 2\n"
                    + "Command: Delete <your task index from list>\n"
                    + "e.g. delete 3\n"
                    + "Command: Find <your search query>"
                    + "e.g. find book"
                    + "Command: Bye\n"
                    + "e.g. bye\n", e.getMessage());
        }
    }
}
