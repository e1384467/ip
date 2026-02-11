package jerry.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

import jerry.exceptions.CorruptedSavedFileException;
import jerry.exceptions.JerryException;
import jerry.exceptions.MissingArgumentException;
import jerry.exceptions.MissingFileException;
import jerry.exceptions.WrongArgumentException;
import jerry.task.Deadline;
import jerry.task.Event;
import jerry.task.Task;
import jerry.task.ToDo;

/**
 * Parses user input and saved file data into commands and task objects.
 */
public class Parser {

    private static final DateTimeFormatter FILE_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm");
    private static final DateTimeFormatter USER_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("ddMMyyyy HHmm");

    /**
     * Loads tasks from the specified saved file and adds them to the given task list.
     * Each line in the file is parsed into a corresponding {@code Task} based on the expected save file format.
     *
     * @param taskFile The file containing the saved task data.
     * @param taskList The list to which the parsed tasks will be added.
     * @return The updated task list containing all loaded tasks.
     * @throws JerryException If the file is missing, inaccessible, contains corrupted or invalid task data.
     */
    public static ArrayList<Task> loadTasksFromFile(File taskFile, ArrayList<Task> taskList) throws JerryException {
        try {
            assert taskFile != null : "taskFile should not be null";
            assert taskList != null : "taskList should not be null";
            Scanner fileScan = new Scanner(taskFile);
            while (fileScan.hasNextLine()) {
                String line = fileScan.nextLine();
                Task task = parseTaskFromLine(line);
                taskList.add(task);
            }
            return taskList;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new CorruptedSavedFileException("The jerry.Jerry.txt file seems to be corrupted :(\n"
                    + "There could be invalid entries in it.\n");
        } catch (FileNotFoundException e) {
            throw new MissingFileException("Oh noo!!! jerry.Jerry.txt file is missing or inaccessible.\n"
                    + "Please make sure that jerry.Jerry.txt is in the data/ directory and that it is writable.\n");
        } catch (DateTimeParseException e) {
            throw new CorruptedSavedFileException(
                    "The data time format of one of the entries in jerry.Jerry.txt seems to be corrupted\n"
                    + "Please ensure that it is in <yyyy-mm-dd>T<hh-mm> format (24-hour clock).\n"
                    + "E.g. 2022-12-06T18-00\n");
        }
    }

    private static Task parseTaskFromLine(String line) throws CorruptedSavedFileException {
        String[] split = line.split("\\|");
        boolean isDone = split[0].equals("1");

        return switch (split[1].toUpperCase()) {
        case "T" -> new ToDo(isDone, split[2]);
        case "D" -> new Deadline(isDone, split[2], parseDateTime(split[3], FILE_DATE_TIME_FORMAT));
        case "E" -> new Event(isDone, split[2],
                parseDateTime(split[3], FILE_DATE_TIME_FORMAT),
                parseDateTime(split[4], FILE_DATE_TIME_FORMAT));
        default -> throw new CorruptedSavedFileException("There is no such task type.\n"
                    + "The jerry.Jerry.txt file could be corrupted\n");
        };
    }

    private static LocalDateTime parseDateTime(String dateTimeString, DateTimeFormatter dateTimeFormat) {
        return LocalDateTime.parse(dateTimeString, dateTimeFormat);
    }

    private static void validateNoPipeCharacter(String userInputArgument) throws WrongArgumentException {
        if (userInputArgument.contains("|")) {
            throw new WrongArgumentException("Character '|' is not allowed in your input.\n");
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
        validateNoPipeCharacter(taskDescription);
        return new ToDo(taskDescription);
    }

    /**
     * Returns a {@code Deadline} task parsed from the given user input.
     * The input is validated to ensure it contains a task description
     * and a valid deadline specified using the {@code /by} delimiter.
     *
     * @param userInputArgument The raw user input containing the deadline task description and due date.
     * @return A {@code Deadline} task created from the parsed input.
     * @throws JerryException If required arguments are missing,
     *              invalid characters are present or the date-time format is incorrect.
     */
    public static Task parseDeadline(String userInputArgument) throws JerryException {
        validateNoPipeCharacter(userInputArgument);
        if (!userInputArgument.toLowerCase().contains(" /by ")) {
            throw new MissingArgumentException(
                    "deadline <your task goes here> /by <ddmmyyyy hhmm (24-hour clock)>\n");
        }

        String[] split = userInputArgument.split("(?i)\\s+/by\\s+", 2);
        String taskDescription = split[0].trim();
        String byString = split[1].trim();
        try {
            LocalDateTime by = parseDateTime(byString, USER_DATE_TIME_FORMAT);
            return new Deadline(taskDescription, by);
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
     * @param userInputArgument The raw user input containing the event description, start time, and end time.
     * @return An {@code Event} task created from the parsed input.
     * @throws JerryException If there are missing arguments,
     *              invalid characters, incorrect date-time format or the time range is wrong.
     */
    public static Task parseEvent(String userInputArgument) throws JerryException {
        validateNoPipeCharacter(userInputArgument);
        if (!userInputArgument.toLowerCase().contains(" /from ")) {
            throw new MissingArgumentException(
                    "event <your task goes here> "
                            + "/from <ddmmyyyy hhmm (24-hour clock)> /to <ddmmyyyy hhmm (24-hour clock)>\n");
        }
        String[] firstSplit = userInputArgument.split("(?i)\\s+/from\\s+", 2);
        String taskDescription = firstSplit[0].trim();

        if (!firstSplit[1].toLowerCase().contains(" /to ")) {
            throw new MissingArgumentException(
                    "event <your task goes here> "
                            + "/from <ddmmyyyy hhmm (24-hour clock)> /to <ddmmyyyy hhmm (24-hour clock)>\n");
        }
        String[] secondSplit = firstSplit[1].split("(?i)\\s+/to\\s+", 2);
        String fromString = secondSplit[0].trim();
        String toString = secondSplit[1].trim();
        try {
            LocalDateTime from = parseDateTime(fromString, USER_DATE_TIME_FORMAT);
            LocalDateTime to = parseDateTime(toString, USER_DATE_TIME_FORMAT);
            if (from.isAfter(to)) {
                throw new WrongArgumentException("Invalid Time Range\n"
                        + "Your start time must be before end time\n");
            }
            return new Event(taskDescription, from, to);
        } catch (DateTimeParseException e) {
            throw new WrongArgumentException("There is issue with your date and time format.\n"
                    + "Try: ddmmyyyy hhmm (24-hour clock)\n"
                    + "E.g. 06062002 0530\n");
        }
    }

    /**
     * Returns the zero-based array index parsed from the given user input array.
     *
     * @param userInputArgument The string containing the task's index.
     * @return The zero-based index corresponding to the user-provided task index.
     * @throws JerryException If the index is missing or is not a valid number.
     */
    public static int getArrayIndex(String userInputArgument) throws JerryException {
        if (userInputArgument.isEmpty()) {
            throw new MissingArgumentException("Mark <your task index from list>\n"
                    + "or\n"
                    + "Unmark <your task index from list>\n"
                    + "or\n"
                    + "Delete <your task index from list>\n");
        }
        try {
            int index = Integer.parseInt(userInputArgument);
            return index - 1;
        } catch (NumberFormatException e) {
            throw new WrongArgumentException("THIS IS NOT A NUMBER\n");
        }
    }

    /**
     * Returns the validated search query extracted from user input.
     * The query must be non-empty to be considered valid.
     *
     * @param searchQuery The raw search query provided by the user.
     * @return The validated search query.
     * @throws JerryException If the search query is empty.
     */
    public static String getSearchQuery(String searchQuery) throws JerryException {
        if (searchQuery.isEmpty()) {
            throw new MissingArgumentException("Please enter a search query.\n"
                    + "Command: Find <your search query>\n"
                    + "E.g. find book\n");
        }
        return searchQuery;
    }

    public static String getUserInputArguments(String userInput) {
        String[] userInputParts = getUserInputParts(userInput);
        return (userInputParts.length < 2 ? "" : userInputParts[1].trim());
    }

    public static String getUserInputCommand(String userInput) {
        String[] userInputParts = getUserInputParts(userInput);
        return userInputParts[0];
    }

    private static String[] getUserInputParts(String userInput) {
        String trimmedUserInput = userInput.trim();
        return trimmedUserInput.split("\\s+", 2);
    }
}
