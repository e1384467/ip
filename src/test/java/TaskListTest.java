import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import jerry.exceptions.DuplicatedTasksException;
import jerry.exceptions.JerryException;
import jerry.exceptions.RepeatedActionsException;
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
    public void add_success() throws JerryException {
        TaskList taskList = new TaskList();
        taskList.add(new ToDo("Sleep"));
        assertEquals(1, taskList.size());
    }

    @Test
    public void add_multipleTasks_success() throws JerryException {
        TaskList taskList = new TaskList();
        taskList.add(new ToDo("Sleep"));
        taskList.add(new ToDo("Eat"));
        taskList.add(new ToDo("Study"));
        assertEquals(3, taskList.size());
    }

    @Test
    public void add_duplicateTask_duplicatedTasksExceptionThrown() throws JerryException {
        TaskList taskList = new TaskList();
        taskList.add(new ToDo("Sleep"));
        assertThrows(DuplicatedTasksException.class, () -> taskList.add(new ToDo("Sleep")));
    }

    @Test
    public void get_success() throws JerryException {
        TaskList list = new TaskList();
        Task task = new ToDo("Sleep");
        list.add(task);
        Task result = list.get(0);
        assertEquals(task.toString(), result.toString());
    }

    @Test
    public void get_multipleTasksLastElement_success() throws JerryException {
        TaskList list = new TaskList();
        list.add(new ToDo("Sleep"));
        list.add(new ToDo("Eat"));
        Task lastTask = new ToDo("Study");
        list.add(lastTask);
        assertEquals(lastTask.toString(), list.get(2).toString());
    }

    @Test
    public void get_emptyList_wrongArgumentExceptionThrown() {
        TaskList list = new TaskList();
        assertThrows(WrongArgumentException.class, () -> list.get(0));
    }

    @Test
    public void get_outOfRange_wrongArgumentExceptionThrown() throws JerryException {
        TaskList list = new TaskList();
        list.add(new ToDo("Sleep"));
        assertThrows(WrongArgumentException.class, () -> list.get(1));
    }

    @Test
    public void get_negativeIndex_wrongArgumentExceptionThrown() throws JerryException {
        TaskList list = new TaskList();
        list.add(new ToDo("Sleep"));
        assertThrows(WrongArgumentException.class, () -> list.get(-1));
    }

    @Test
    public void deleteTask_success() throws JerryException {
        TaskList list = new TaskList();
        Task task = new ToDo("Sleep");
        list.add(task);
        Task deleted = list.deleteTask(0);
        assertEquals(task.toString(), deleted.toString());
        assertEquals(0, list.size());
    }

    @Test
    public void deleteTask_fromMultipleTasks_success() throws JerryException {
        TaskList list = new TaskList();
        Task task1 = new ToDo("Sleep");
        Task task2 = new ToDo("Eat");
        list.add(task1);
        list.add(task2);
        list.deleteTask(0);
        assertEquals(1, list.size());
        assertEquals(task2.toString(), list.get(0).toString());
    }

    @Test
    public void deleteTask_outOfRange_wrongArgumentExceptionThrown() throws JerryException {
        TaskList list = new TaskList();
        list.add(new ToDo("Sleep"));
        assertThrows(WrongArgumentException.class, () -> list.deleteTask(5));
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
        assertThrows(RepeatedActionsException.class, () -> list.markTask(0));
    }

    @Test
    public void markTask_multipleTasksMarkOneTask_success() throws JerryException {
        TaskList list = new TaskList();
        list.add(new ToDo("Sleep"));
        list.add(new ToDo("Eat"));
        list.add(new ToDo("Study"));
        list.markTask(1);
        assertTrue(list.get(1).isDone());
        assertFalse(list.get(0).isDone());
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
        assertThrows(RepeatedActionsException.class, () -> list.unmarkTask(0));
    }

    @Test
    public void unmarkTask_multipleTasksUnmarkOneTask_success() throws JerryException {
        TaskList list = new TaskList();
        list.add(new ToDo(true, "Sleep"));
        list.add(new ToDo(true, "Eat"));
        list.unmarkTask(0);
        assertFalse(list.get(0).isDone());
        assertTrue(list.get(1).isDone());
    }

    @Test
    public void buildListOutput_singleTask_success() throws JerryException {
        TaskList list = new TaskList();
        list.add(new ToDo("Sleep"));
        String output = list.buildListOutput();
        String expected = "1. [T][ ] Sleep" + System.lineSeparator();
        assertEquals(expected, output);
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

    @Test
    public void buildListOutput_emptyList_success() {
        TaskList list = new TaskList();
        String output = list.buildListOutput();
        assertEquals("", output);
    }

    @Test
    public void buildListOutput_markedTasks_success() throws JerryException {
        TaskList list = new TaskList();
        list.add(new ToDo(true, "Sleep"));
        list.add(new ToDo("Eat"));
        String output = list.buildListOutput();
        String expected = "1. [T][X] Sleep" + System.lineSeparator()
                + "2. [T][ ] Eat" + System.lineSeparator();
        assertEquals(expected, output);
    }

    @Test
    public void find_singleMatch_success() throws JerryException {
        TaskList list = new TaskList();
        list.add(new ToDo("Sleep"));
        list.add(new ToDo("Eat"));
        TaskList result = list.find("Sleep");
        assertEquals(1, result.size());
    }

    @Test
    public void find_multipleMatches_success() throws JerryException {
        TaskList list = new TaskList();
        list.add(new ToDo("Sleep well"));
        list.add(new ToDo("Eat"));
        list.add(new ToDo("Sleep early"));
        TaskList result = list.find("Sleep");
        assertEquals(2, result.size());
    }

    @Test
    public void find_noMatches_success() throws JerryException {
        TaskList list = new TaskList();
        list.add(new ToDo("Sleep"));
        list.add(new ToDo("Eat"));
        TaskList result = list.find("Study");
        assertEquals(0, result.size());
        assertTrue(result.isEmpty());
    }

    @Test
    public void find_emptyList_success() {
        TaskList list = new TaskList();
        TaskList result = list.find("Sleep");
        assertEquals(0, result.size());
    }

    @Test
    public void size_emptyList_success() {
        TaskList list = new TaskList();
        assertEquals(0, list.size());
    }

    @Test
    public void isEmpty_emptyList_success() {
        TaskList list = new TaskList();
        assertTrue(list.isEmpty());
    }

    @Test
    public void isEmpty_populatedList_success() throws JerryException {
        TaskList list = new TaskList();
        list.add(new ToDo("Sleep"));
        assertFalse(list.isEmpty());
    }
}
