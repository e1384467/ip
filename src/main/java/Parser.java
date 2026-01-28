import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Parser {

    public static ArrayList<Task> loadTasksFromFile (File taskFile, ArrayList<Task> taskList) throws FileNotFoundException {
        Scanner fileScan = new Scanner(taskFile);
        while (fileScan.hasNextLine()) {
            String line = fileScan.nextLine();
            String[] lineSplit = line.split("\\s",3);
            boolean isDoneFlag = Integer.parseInt(lineSplit[0]) == 1;
            String taskTypeFlag = lineSplit[1];
            String taskDetails = lineSplit[2];
            Task task = Parser.parseTaskFromFile(isDoneFlag, taskTypeFlag, taskDetails);
            taskList.add(task);
        }
        return taskList;
    }

    public static Task parseTaskFromFile(boolean isDoneFlag, String taskTypeFlag, String taskDetails) {
        if (taskTypeFlag.equalsIgnoreCase("T")) {
            return new ToDo(isDoneFlag, taskDetails);
        }

        if (taskTypeFlag.equalsIgnoreCase("D")) {
            String[] split = taskDetails.split("(?i)\\s*/by\\s*", 2);
            String taskDescription = split[0];
            String by = split[1];
            return new Deadline(isDoneFlag, taskDescription, by);
        }

        String[] details = taskDetails.split("(?i)\\s*/from\\s*",2);
        String taskDescription = details[0];
        String[] taskDuration = details[1].split("(?i)\\s*/to\\s*",2);
        String from = taskDuration[0];
        String to = taskDuration[1];

        return new Event(isDoneFlag, taskDescription, from, to);
    }

    public static Task parseTodo(String taskDescription) throws JerryException {
        if (taskDescription.isEmpty()) {
            throw new MissingArgumentException("todo <your task goes here>\n");
        }
        return new ToDo(taskDescription);
    }

    public static Task parseDeadline(String userInput) throws JerryException {
        try {
            String[] split = userInput.split("(?i)\\s*/by\\s*",2);
            String taskDescription = split[0];
            String by = split[1];
            if (taskDescription.isEmpty() || by.isEmpty()) {
                throw new MissingArgumentException("deadline <your task goes here> /by <add your date/time here>\n");
            }
            return new Deadline(taskDescription, by);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new MissingArgumentException("deadline <your task goes here> /by <add your date/time here>\n");
        }
    }

    public static Task parseEvent(String userInput) throws JerryException {
        try {
            String[] firstSplit = userInput.split("(?i)\\s*/from\\s*", 2);
            String taskDescription = firstSplit[0];
            String[] secondSplit = firstSplit[1].split("(?i)\\s*/to\\s*", 2);
            String from = secondSplit[0];
            String to = secondSplit[1];
            if (taskDescription.isEmpty() || from.isEmpty() || to.isEmpty()) {
                throw new MissingArgumentException("Event <your task goes here> /from <add your start date/time here> /to <add your end date/time here>\n");
            }
            return new Event(taskDescription, from, to);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new MissingArgumentException("Event <your task goes here> /from <add your start date/time here> /to <add your end date/time here>\n");
        }
    }

    public static int getArrayIndex(String[] userInputArray, int listSize) throws JerryException {
        try {
            int index = Integer.parseInt(userInputArray[1]);
            return index - 1;
        } catch (NumberFormatException e) {
            throw new WrongArgumentException("THIS IS NOT A NUMBER\n");
        } catch (IndexOutOfBoundsException e) {
            if (userInputArray.length < 2) {
                throw new MissingArgumentException("Mark <your task index from list>\n"
                        + "or\n"
                        + "Unmark <your task index from list>\n"
                        + "or\n"
                        + "Delete <your task index from list>\n");
            }
            throw new WrongArgumentException(
                    (listSize == 0
                    ? ("Your task list is empty currently")
                    : ("Please enter a number in the range of 1 to " + listSize))
                    + "\nUse Command: list. To check your task list first :)\n");
        }
    }

}
