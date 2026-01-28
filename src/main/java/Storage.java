import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class Storage {

    private final static Path FILE_PATH = Path.of("data/Jerry.txt");

    public static ArrayList<Task> initialise() throws JerryException {
       try {
           File taskFile =  new File(FILE_PATH.toString());
           File parentDirectory = new File(taskFile.getParent());

           if (!parentDirectory.exists()) {
               if (!parentDirectory.mkdirs()){
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
           throw new FileErrorException("There is an I/O error when creating Jerry.txt.\n"
                   + "Please make sure that the folder is writable.\n");
       } catch (SecurityException e) {
           throw new FileErrorException("There seems to be some issue with the permissions of the folder/file.\n"
                   + "Please make sure that the folder/file is writable.\n");
       }

    }

    public static void save(ArrayList<Task> taskList) throws JerryException {
        try {
            FileWriter taskFile = new FileWriter(FILE_PATH.toString());
            for (Task task : taskList) {
                taskFile.write(task.fileFormat() + System.lineSeparator());
            }
            taskFile.close();
        } catch (IOException e) {
            throw new FileErrorException("There seems to be an error when writing to Jerry.txt.\n"
                    + "Please make sure that Jerry.txt is writable.\n");
        }
    }
}