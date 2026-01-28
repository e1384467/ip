import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class Storage {

    private final static Path FILE_PATH = Path.of("data/Jerry.txt");

    public static ArrayList<Task> initialise() throws JerryException {
       try {
           File taskFile =  new File(FILE_PATH.toString());
           ArrayList<Task> taskList = new ArrayList<Task>();
           if (taskFile.createNewFile()) {
               return taskList;
           }
           return Parser.loadTasksFromFile(taskFile, taskList);
       } catch (FileNotFoundException e) {
           throw new MissingFileException("Oh noo!!! Jerry.txt file is missing or stored in the wrong directory.\n"
                   + "Make sure to use data/ directory or restart me.\n");
       } catch (IOException e) {
           throw new FileErrorException("There seems to be an error when creating Jerry.txt.\n");
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
            throw new FileErrorException("There seems to be an error when writing to Jerry.txt.\n");
        }
    }
}