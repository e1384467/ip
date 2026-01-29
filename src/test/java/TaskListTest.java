import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import jerry.exceptions.JerryException;
import jerry.exceptions.WrongArgumentException;
import jerry.task.Task;
import jerry.task.TaskList;
import jerry.task.ToDo;

public class TaskListTest {

    @Test
    public void constructor_emptyList_success() {
        TaskList taskList = new TaskList();
        assertTrue(taskList.isEmpty());
        assertEquals(0, taskList.size());
    }

    @Test
    public void constructor_populatedList_success() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo("Sleep"));
        TaskList list = new TaskList(tasks);
        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
    }

    @Test
    public void add_success() {
        TaskList taskList = new TaskList();
        taskList.add(new ToDo("Sleep"));
        assertEquals(1, taskList.size());
    }

    @Test
    public void get_success() throws WrongArgumentException {
        TaskList list = new TaskList();
        Task task = new ToDo("Sleep");
        list.add(task);
        Task result = list.get(0);
        assertEquals(task.toString(), result.toString());
    }

    @Test
    public void get_emptyList_wrongArgumentExceptionThrown() {
        TaskList list = new TaskList();
        try {
            assertEquals(new ToDo("Sleep").toString(), list.get(0).toString());
            fail();
        } catch (JerryException e) {
            assertEquals("Wrong Argument >:( !!!!\n"
                    + "Your task list is empty currently"
                    + "\nUse Command: list. To check your task list first :)\n", e.getMessage());
        }
    }

    @Test
    public void get_outOfRange_wrongArgumentExceptionThrown() {
        TaskList list = new TaskList();
        list.add(new ToDo("Sleep"));
        try {
            assertEquals("Sleep", list.get(1).toString());
            fail();
        } catch (JerryException e) {
            assertEquals("Wrong Argument >:( !!!!\n"
                    + "Please enter a number in the range of 1 to " + list.size()
                    + "\nUse Command: list. To check your task list first :)\n", e.getMessage());
        }
    }

    @Test
    public void deleteTask_success() throws JerryException {
        TaskList list = new TaskList();
        Task task = new ToDo("Sleep");
        list.add(task);
        Task deleted = list.deleteTask(0);
        assertEquals(task.toString(), deleted.toString());
    }

    @Test
    public void markTask_success() throws JerryException {
        TaskList list = new TaskList();
        Task task = new ToDo("Sleep");
        list.add(task);
        Task marked = list.markTask(0);
        assertTrue(marked.isDone());
    }

    @Test
    public void markTask_alreadyDone_repeatedActionsExceptionThrown() throws JerryException {
        TaskList list = new TaskList();
        Task task = new ToDo("Sleep");
        task.toggleIsDone();
        list.add(task);
        try {
            list.markTask(0);
            fail();
        } catch (JerryException e) {
            assertEquals("Repeated Action >:( !!!!\n" + "You've made a mistake, "
                    + task
                    + " is already marked as done\n", e.getMessage());
        }
    }

    @Test
    public void unmarkTask_success() throws JerryException {
        TaskList list = new TaskList();
        Task task = new ToDo(true, "Sleep");
        list.add(task);
        Task unmarkTask = list.unmarkTask(0);
        assertFalse(unmarkTask.isDone());
    }

    @Test
    public void unmarkTask_alreadyUndone_repeatedActionsExceptionThrown() throws JerryException {
        TaskList list = new TaskList();
        Task task = new ToDo("Sleep");
        list.add(task);
        try {
            list.unmarkTask(0);
            fail();
        } catch (JerryException e) {
            assertEquals("Repeated Action >:( !!!!\n" + "You've made a mistake, "
                    + task
                    + " is already unmarked as not done yet\n", e.getMessage());
        }
    }

    @Test
    public void buildListOutput_multipleTasks_success() throws Exception {
        TaskList list = new TaskList();
        list.add(new ToDo("Sleep"));
        list.add(new ToDo("Eat"));
        String output = list.buildListOutput();
        String expected = "1. [T][ ] Sleep" + System.lineSeparator()
                + "2. [T][ ] Eat" + System.lineSeparator();
        assertEquals(expected, output);
    }
}
