package jerry.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import jerry.exceptions.FileErrorException;
import jerry.exceptions.JerryException;
import jerry.parser.Parser;
import jerry.task.Task;
import jerry.task.TaskList;

/**
 * Handles loading and saving of task data to storage.
 */
public class Storage {

    /** Path to the file used for storing task data. */
    private static final Path FILE_PATH = Path.of("data/Jerry.txt");

    /**
     * Returns a list of tasks loaded from the saved data file.
     * If the data file does not exist, it is created and an empty list is returned.
     *
     * @return A list of tasks loaded from storage.
     * @throws JerryException If the file cannot be created, accessed, or contains corrupted data.
     */
    public static ArrayList<Task> initialise() throws JerryException {
        try {
            File taskFile = new File(FILE_PATH.toString());
            File parentDirectory = new File(taskFile.getParent());
            if (!parentDirectory.exists()) {
                if (!parentDirectory.mkdirs()) {
                    throw new FileErrorException("Failed trying to create data/ directory.\n"
                            + "Please ensure that the folder is writable.");
                }
            }

            ArrayList<Task> taskList = new ArrayList<Task>();
            if (taskFile.createNewFile()) {
                return taskList;
            }
            return Parser.loadTasksFromFile(taskFile, taskList);
        } catch (IOException e) {
            throw new FileErrorException("There is an I/O error when creating jerry.Jerry.txt.\n"
                    + "Please make sure that the folder is writable.\n");
        } catch (SecurityException e) {
            throw new FileErrorException("There seems to be some issue with the permissions of the folder/file.\n"
                    + "Please make sure that the folder/file is writable.\n");
        }

    }

    /**
     * Saves the given task list to the data file in the expected file format.
     * Existing file contents will be overwritten.
     *
     * @param taskList The task list to be saved to storage.
     * @throws JerryException If an error occurs while writing to the data file or creating the parent directory
     */
    public static void save(TaskList taskList) throws JerryException {
        try {
            File taskFile = new File(FILE_PATH.toString());
            File parentDirectory = new File(taskFile.getParent());
            if (!parentDirectory.exists()) {
                if (!parentDirectory.mkdirs()) {
                    throw new FileErrorException("Failed trying to create data/ directory.\n"
                            + "Please ensure that the folder is writable.");
                }
            }

            FileWriter writeTaskFile = new FileWriter(FILE_PATH.toString());
            for (int index = 0; index < taskList.size(); index += 1) {
                writeTaskFile.write(taskList.get(index).fileFormat() + System.lineSeparator());
            }
            writeTaskFile.close();
        } catch (IOException e) {
            throw new FileErrorException("There seems to be an error when writing to jerry.Jerry.txt.\n"
                    + "Please make sure that data/jerry.Jerry.txt exist and is writable.\n");
        } catch (SecurityException e) {
            throw new FileErrorException("There seems to be some issue with the permissions of the folder/file.\n"
                    + "Please make sure that the folder/file is writable.\n");
        }
    }
}
