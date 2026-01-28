import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Parser {

    public static ArrayList<Task> fileParse (File taskFile) throws FileNotFoundException {
        ArrayList<Task> taskList = new ArrayList<Task>();
        Scanner fileScan = new Scanner(taskFile);
        while (fileScan.hasNextLine()) {
            String line = fileScan.nextLine();
            String[] lineSplit = line.split("\\s",3);
            boolean isDoneFlag = Integer.parseInt(lineSplit[0]) == 1;
            String taskTypeFlag = lineSplit[1];
            String taskDetails = lineSplit[2];
            Task task = Parser.commandParse(isDoneFlag, taskTypeFlag, taskDetails);
            taskList.add(task);
        }
        return taskList;
    }

    public static Task commandParse(boolean isDoneFlag, String taskTypeFlag, String taskDetails) {
        if (taskTypeFlag.equalsIgnoreCase("T")) {
            return new ToDo(isDoneFlag, taskDetails);
        }

        if (taskTypeFlag.equalsIgnoreCase("D")) {
            String[] details = taskDetails.split("(?i)\\s*/by\\s*", 2);
            String taskDescription = details[0];
            String by = details[1];
            return new Deadline(isDoneFlag, taskDescription, by);
        }

        String[] details = taskDetails.split("(?i)\\s*/from\\s*",2);
        String taskDescription = details[0];
        String[] taskDuration = details[1].split("(?i)\\s*/to\\s*",2);
        String from = taskDuration[0];
        String to = taskDuration[1];

        return new Event(isDoneFlag, taskDescription, from, to);
    }

}
