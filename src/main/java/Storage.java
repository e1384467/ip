import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class Storage {

    private final static Path FILE_PATH = Path.of("data/Jerry.txt");

    public static ArrayList<Task> initialise() throws IOException {
        File taskFile =  new File(FILE_PATH.toString());
        if (taskFile.createNewFile()) {
            return new ArrayList<Task>();
        }
        return Parser.fileParse(taskFile);
    }

    public static void save(ArrayList<Task> taskList) throws IOException {
        FileWriter taskFile = new FileWriter(FILE_PATH.toString());
        for (Task task : taskList) {
            taskFile.write(task.fileFormat() + System.lineSeparator());
        }
        taskFile.close();
    }
}