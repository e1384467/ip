package jerry.parser;

import jerry.exceptions.*;
import jerry.task.Deadline;
import jerry.task.Event;
import jerry.task.Task;
import jerry.task.ToDo;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Parses user input and saved file data into commands and task objects.
 */
public class Parser {

    /**
     * Loads tasks from the specified saved file and adds them to the given task list.
     * Each line in the file is parsed into a corresponding {@code Task} based on the expected save file format.
     *
     * @param taskFile The file containing the saved task data.
     * @param taskList The list to which the parsed tasks will be added.
     * @return The updated task list containing all loaded tasks.
     * @throws JerryException If the file is missing, inaccessible, contains corrupted or invalid task data.
     */
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
                        taskList.add(new Deadline(isDone
                                , split[2]
                                , LocalDateTime.parse(split[3], DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm"))));
                        break;
                    case "E":
                        taskList.add(new Event(isDone
                                , split[2]
                                , LocalDateTime.parse(split[3], DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm"))
                                , LocalDateTime.parse(split[4], DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm"))));
                        break;
                    default:
                        throw new CorruptedSavedFileException("There is no such task type.\n"
                                + "The jerry.Jerry.txt file could be corrupted\n");
                }
            }
            return taskList;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new CorruptedSavedFileException("The jerry.Jerry.txt file seems to be corrupted :(\n"
                    + "There could be invalid entries in it.\n");
        } catch (FileNotFoundException e) {
            throw new MissingFileException("Oh noo!!! jerry.Jerry.txt file is missing or inaccessible.\n"
                    + "Please make sure that jerry.Jerry.txt is in the data/ directory and that it is writable.\n");
        } catch (DateTimeParseException e) {
            throw new CorruptedSavedFileException("The data time format of one of the entries in jerry.Jerry.txt seems to be corrupted\n"
                    + "Please ensure that it is in <yyyy-mm-dd>T<hh-mm> format (24-hour clock).\n"
                    + "E.g. 2022-12-06T18-00\n");
        }
    }

    /**
     * Returns a {@code ToDo} task parsed from the given task description.
     * The description is validated to ensure it is non-empty and does not
     * contain invalid characters.
     *
     * @param taskDescription The description of the to-do task.
     * @return A {@code ToDo} task created from the given description.
     * @throws JerryException If the description is empty or contains invalid characters.
     */
    public static Task parseTodo(String taskDescription) throws JerryException {
        if (taskDescription.isEmpty()) {
            throw new MissingArgumentException("todo <your task goes here>\n");
        }
        if (taskDescription.contains("|")) {
            throw new WrongArgumentException("Character '|' is not allowed in your task description.\n");
        }
        return new ToDo(taskDescription);
    }

    /**
     * Returns a {@code Deadline} task parsed from the given user input.
     * The input is validated to ensure it contains a task description and a valid deadline specified using the {@code /by} delimiter.
     *
     * @param userInput The raw user input containing the deadline task description and due date.
     * @return A {@code Deadline} task created from the parsed input.
     * @throws JerryException If required arguments are missing, invalid characters are present or the date-time format is incorrect.
     */
    public static Task parseDeadline(String userInput) throws JerryException {
        try {
            if (userInput.contains("|")) {
                throw new WrongArgumentException("Character '|' is not allowed in your input.\n");
            }
            String[] split = userInput.split("(?i)\\s*/by\\s*", 2);
            String taskDescription = split[0];
            String by = split[1];
            if (taskDescription.isEmpty() || by.isEmpty()) {
                throw new MissingArgumentException("deadline <your task goes here> /by <ddmmyyyy hhmm (24-hour clock)>\n");
            }
            return new Deadline(taskDescription, LocalDateTime.parse(by, DateTimeFormatter.ofPattern("ddMMyyyy HHmm")));
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new MissingArgumentException("deadline <your task goes here> /by <ddmmyyyy hhmm (24-hour clock)>\n");
        } catch (DateTimeParseException e) {
            throw new WrongArgumentException("There is issue with your date and time format.\n"
                    + "Try: ddmmyyyy hhmm (24-hour clock)\n"
                    + "E.g. 06062002 0530\n");
        }
    }

    /**
     * Returns an {@code Event} task parsed from the given user input.
     * The input is validated to ensure it contains a task description, a start time
     * specified using {@code /from}, and an end time specified using {@code /to},
     * with the start time occurring before the end time.
     *
     * @param userInput The raw user input containing the event description, start time, and end time.
     * @return An {@code Event} task created from the parsed input.
     * @throws JerryException If there are missing arguments, invalid characters, incorrect date-time format or the time range is wrong
     */
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
                throw new MissingArgumentException("event <your task goes here> /from <ddmmyyyy hhmm (24-hour clock)> /to <ddmmyyyy hhmm (24-hour clock)>\n");
            }
            LocalDateTime formattedFrom = LocalDateTime.parse(from, DateTimeFormatter.ofPattern("ddMMyyyy HHmm"));
            LocalDateTime formattedTo = LocalDateTime.parse(to, DateTimeFormatter.ofPattern("ddMMyyyy HHmm"));

            if (formattedFrom.isAfter(formattedTo)) {
                throw new WrongArgumentException("Invalid Time Range\n"
                        + "Your start time must be before end time\n");
            }
            return new Event(taskDescription, formattedFrom, formattedTo);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new MissingArgumentException("event <your task goes here> /from <ddmmyyyy hhmm (24-hour clock)> /to <ddmmyyyy hhmm (24-hour clock)>\n");
        } catch (DateTimeParseException e) {
            throw new WrongArgumentException("There is issue with your date and time format.\n"
                    + "Try: ddmmyyyy hhmm (24-hour clock)\n"
                    + "E.g. 06062002 0530\n");
        }
    }

    /**
     * Returns the zero-based array index parsed from the given user input array.
     *
     * @param userInputArray The array containing the command and its arguments.
     * @return The zero-based index corresponding to the user-provided task index.
     * @throws JerryException If the index is missing or is not a valid number.
     */
    public static int getArrayIndex(String[] userInputArray) throws JerryException {
        try {
            int index = Integer.parseInt(userInputArray[1]);
            return index - 1;
        } catch (NumberFormatException e) {
            throw new WrongArgumentException("THIS IS NOT A NUMBER\n");
        } catch (IndexOutOfBoundsException e) {
            throw new MissingArgumentException("Mark <your task index from list>\n"
                    + "or\n"
                    + "Unmark <your task index from list>\n"
                    + "or\n"
                    + "Delete <your task index from list>\n");
        }
    }
}
