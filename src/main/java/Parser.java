import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Parser {

    public static ArrayList<Task> loadTasksFromFile (File taskFile, ArrayList<Task> taskList) throws JerryException {
        try {
            Scanner fileScan = new Scanner(taskFile);
            while (fileScan.hasNextLine()) {
                String line = fileScan.nextLine();
                String[] split = line.split("\\|");
                boolean isDone = split[0].equals("1");
                switch (split[1].toUpperCase()) {
                    case "T":
                        taskList.add(new ToDo(isDone, split[2]));
                        break;
                    case "D":
                        taskList.add(new Deadline(isDone, split[2], split[3]));
                        break;
                    case "E":
                        taskList.add(new Event(isDone, split[2], split[3], split[4]));
                        break;
                    default:
                        throw new CorruptedSavedFileException("There is no such task type.\n"
                                + "The Jerry.txt file could be corrupted\n");
                }
            }
            return taskList;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new CorruptedSavedFileException("The Jerry.txt file seems to be corrupted :(\n"
                    + "There could be invalid or empty entries in it.\n");
        } catch (FileNotFoundException e) {
            throw new MissingFileException("Oh noo!!! Jerry.txt file is missing or inaccessible.\n"
                    + "Please make sure that Jerry.txt is in the data/ directory and that it is writable.\n");
        }
    }

    public static Task parseTodo(String taskDescription) throws JerryException {
        if (taskDescription.isEmpty()) {
            throw new MissingArgumentException("todo <your task goes here>\n");
        }
        if (taskDescription.contains("|")) {
            throw new WrongArgumentException("Character '|' is not allowed in your task description.\n");
        }
        return new ToDo(taskDescription);
    }

    public static Task parseDeadline(String userInput) throws JerryException {
        try {
            if (userInput.contains("|")) {
                throw new WrongArgumentException("Character '|' is not allowed in your input.\n");
            }
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
            if (userInput.contains("|")) {
                throw new WrongArgumentException("Character '|' is not allowed in your input.\n");
            }
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
