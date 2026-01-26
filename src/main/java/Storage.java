import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class Storage {

    private static Path FILE_PATH = Path.of("data/Jerry.txt");

    public static ArrayList<Task> initialise() throws IOException {
        File taskFile =  new File(FILE_PATH.toString());
        try {
            if (taskFile.createNewFile()) {
                return new ArrayList<Task>();
            }
            return Parser.fileParse(taskFile);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(e.getMessage());
        } catch (IOException e) {
            throw new IOException("IO error when creating file");
        }
    }
}